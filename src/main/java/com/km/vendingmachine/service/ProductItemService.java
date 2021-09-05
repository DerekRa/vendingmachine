package com.km.vendingmachine.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.km.vendingmachine.exception.NotFoundException;
import com.km.vendingmachine.model.ConfigList;
import com.km.vendingmachine.model.ProductItems;
import com.km.vendingmachine.model.ProductModel;
import com.km.vendingmachine.repo.ConfigListRepo;
import com.km.vendingmachine.repo.ProductCategoryRepo;
import com.km.vendingmachine.repo.ProductListRepo;


@Service
@Transactional
public class ProductItemService {
	private static final String WNF = " was not found"; 
	
	ProductListRepo productListrepo;
	ConfigListRepo configListrepo;
	ProductCategoryRepo productModelrepo;

	public ProductItemService(ProductListRepo productListrepo, ConfigListRepo configListrepo,
			ProductCategoryRepo productModelrepo) {
		super();
		this.productListrepo = productListrepo;
		this.configListrepo = configListrepo;
		this.productModelrepo = productModelrepo;
	}

	public List<ProductModel> getAllProductList(){
		return productModelrepo.findAll();
	}
	
	public List<ConfigList> getConfigList(){
		return configListrepo.findAll();
	}
	
	public List<ProductItems> getProductItems(){
		return productListrepo.findAll();
	}
	
	public ProductModel getCategory(int id){
		return productModelrepo.findProductModelById(id)
				.orElseThrow(() -> new NotFoundException("Category id " + id + WNF));
	}
	
	public ConfigList getConfig(int id){
		return configListrepo.findConfigListById(id)
				.orElseThrow(() -> new NotFoundException("Config id " + id + WNF));
	}
	
	public ProductItems getItem(int id) {
		return productListrepo.findProductItemById(id)
				.orElseThrow(() -> new NotFoundException("Product item id " + id + WNF));
	}
	
	public boolean findRows(int rowLetter) {
		Optional<ConfigList> findRow = configListrepo.findConfigListByRows(rowLetter);
		return findRow.isPresent();
	}
	
	public ConfigList findColumn(int rowLetter) {
		return configListrepo.findConfigListByRows(rowLetter)
				.orElseThrow(() -> new NotFoundException("Row " + rowLetter + WNF));
	}
	
	public ProductModel findProductCategoryId(ConfigList configId) {
		return productModelrepo.findProductModelByConfig(configId)
				.orElseThrow(() -> new NotFoundException("Config id " + configId + WNF));
	}
	
	public ProductModel addProductItemList(ProductModel productitems) {
		return productModelrepo.save(productitems);
	}

	public ProductItems addProductItem(ProductItems productitems) {
		return productListrepo.save(productitems);
	}
	
	public ConfigList updateConfigListColumn(ConfigList configList) {
		return configListrepo.save(configList);
	}
	
	public void deleteCategory(int id) {
		productModelrepo.deleteById(id);
	}
	
	public void deleteItem(int id) {
		productListrepo.deleteItemById(id);
	}
}
