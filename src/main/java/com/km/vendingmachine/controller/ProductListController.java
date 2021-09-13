package com.km.vendingmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.km.vendingmachine.client.DeleteOrderClient;
import com.km.vendingmachine.exception.NotFoundException;
import com.km.vendingmachine.model.ConfigList;
import com.km.vendingmachine.model.ProductItems;
import com.km.vendingmachine.model.ProductModel;
import com.km.vendingmachine.service.ProductItemService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ProductListController {
	
	ProductItemService productItemService;
	DeleteOrderClient deleteOrderClient;
	
	@Autowired
	public ProductListController(ProductItemService productItemService, DeleteOrderClient deleteOrderClient) {
		this.productItemService = productItemService;
		this.deleteOrderClient = deleteOrderClient;
	}
	
	@GetMapping(value="/all") 
	@ApiOperation(value="Show All List of Products",
		notes="This part gets all the list of Products - getProductItemList",
		response = ProductModel.class)
	public ResponseEntity<List<ProductModel>> getProductItemList(){
		List<ProductModel> productItemList = productItemService.getAllProductList();
		return new ResponseEntity<>(productItemList, HttpStatus.OK);
	}
	
	@GetMapping(value="/config")
	@ApiOperation(value="Show All List of Configs",
		notes="This part gets all the list of Configs - getConfigList",
		response = ConfigList.class)
	public ResponseEntity<List<ConfigList>> getConfigList(){
		List<ConfigList> configs = productItemService.getConfigList();
		return new ResponseEntity<>(configs, HttpStatus.OK);
	}
	
	@GetMapping(value="/items") 
	@ApiOperation(value="Show All List of Items",
		notes="This part gets all the list of Items - getProductItems",
		response = ProductItems.class)
	public ResponseEntity<List<ProductItems>> getProductItems(){
		List<ProductItems> productItems = productItemService.getProductItems();
		return new ResponseEntity<>(productItems, HttpStatus.OK);
	}
	
	@GetMapping(value="/all/{id}")
	@ApiOperation(value="Show only single Category",
		notes="This part get a single Category - getCategory",
		response = ProductModel.class)
	public ResponseEntity<ProductModel> getCategory(@PathVariable("id") int id) throws NotFoundException{
		ProductModel config = productItemService.getCategory(id);
		return new ResponseEntity<>(config, HttpStatus.OK);
	}
	
	@GetMapping(value="/config/{id}")
	@ApiOperation(value="Show only single Config",
		notes="This part get a single Config - getConfig",
		response = ConfigList.class)
	public ResponseEntity<ConfigList> getConfig(@PathVariable("id") int id) throws NotFoundException{
		ConfigList config = productItemService.getConfig(id);
		return new ResponseEntity<>(config, HttpStatus.OK);
	}
	
	@GetMapping(value="/items/{id}") 
	@ApiOperation(value="Show only single Item",
		notes="This part get a single Item - gettem",
		response = ProductItems.class)
	public ResponseEntity<ProductItems> gettem(@PathVariable("id") int id) throws NotFoundException{
		ProductItems productitem = productItemService.getItem(id);
		return new ResponseEntity<>(productitem, HttpStatus.OK);
	}
	
	@PostMapping(value="/add") 
	@ApiOperation(value="Add a Category with config and list of items",
		notes="This part adds up a Category with config and list of items - addProductList",
		response = ProductModel.class)
	public ResponseEntity<ProductModel> addProductList(@RequestBody ProductModel productListItems){
		ConfigList confList = productListItems.getConfig();
		int rowLetter = confList.getRows();
		if(!productItemService.findRows(rowLetter)) {
			ProductModel productItems = productItemService.addProductItemList(productListItems);
			return new ResponseEntity<>(productItems, HttpStatus.CREATED);
		} else {
			ConfigList confListData = productItemService.findColumn(rowLetter);
			int currentNewColumn = confListData.getColumns() + confList.getColumns() + 1;
			if(currentNewColumn > 9) {
				return new ResponseEntity<>(productListItems, HttpStatus.INSUFFICIENT_STORAGE);
			} else {
				int id = productItemService.findProductCategoryId(confListData).getId();
				List<ProductItems> productItemList = productListItems.getItems();
				for (ProductItems productItem : productItemList) {
					productItem.setProduct_id(id);
					productItemService.addProductItem(productItem);
				}
				confListData.setColumns(currentNewColumn);
				productItemService.updateConfigListColumn(confListData);
				return new ResponseEntity<>(productListItems, HttpStatus.CREATED);
			}
		}
	}
	
	@PutMapping(value="/update") 
	@ApiOperation(value="Update a Category name purpose method",
		notes="This part update a Category name - updateProductList",
		response = ProductModel.class)
	public ResponseEntity<ProductModel> updateProductList(@RequestBody ProductModel productListItems){
		ProductModel productItems = productItemService.addProductItemList(productListItems);
		return new ResponseEntity<>(productItems, HttpStatus.OK);
	}
	
	@PutMapping(value="/update/item")
	@ApiOperation(value="Update Item by its name, amount and price",
		notes="This part update Item by its name, amount and price - updateItem",
		response = ProductItems.class)
	public ResponseEntity<ProductItems> updateItem(@RequestBody ProductItems productItem){
		ProductItems pItem = productItemService.addProductItem(productItem);
		return new ResponseEntity<>(pItem, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/forceDelete/{id}")
	@ApiOperation(value="Force Delete a single Category which includes other data connected on Config and Item List",
		notes="This part Force Delete a single Category which includes other data connected on Config and Item List - deleteCategory")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) throws EmptyResultDataAccessException{
		productItemService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/forceDeleteItem/{id}")
	@ApiOperation(value="Force Delete a single Item and it also updatep amount on config",
		notes="This part Force Delete a single Item and it also update amount on config - deleteItem")
	public ResponseEntity<?> deleteItem(@PathVariable("id") int id) throws EmptyResultDataAccessException{
		ProductItems productitem = productItemService.getItem(id);
		ProductModel config = productItemService.getCategory(productitem.getProduct_id());
		config.getConfig().setColumns(config.getConfig().getColumns()-1);
		ProductModel productItems = productItemService.addProductItemList(config);
		productItemService.deleteItem(id); 
		deleteOrderClient.deleteCustomerOrder(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
