package com.km.vendingmachine.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "productModel")
@ApiModel(description="Details about Product Category Model")
public class ProductModel implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "prod_seq")
    @Column(nullable = false, updatable = false,name = "prodId")
	@ApiModelProperty(position = 0, notes = "Unique id and auto generate with hybernate of Config List")
	private int id;
	@ApiModelProperty(position = 1,notes = "Category Name which is optional")
	private String categoryName;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@ApiModelProperty(position = 2,notes = "Config Model")
	private ConfigList config;	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="product_id",referencedColumnName = "prodId") 
	@ApiModelProperty(position = 3,notes = "Item List Model")
	private List<ProductItems> items;	
	
	public ProductModel() {}

	public ProductModel(int id, String categoryName, ConfigList config,
			List<ProductItems> items) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.config = config;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ConfigList getConfig() {
		return config;
	}

	public void setConfig(ConfigList config) {
		this.config = config;
	}

	public List<ProductItems> getItems() {
		return items;
	}

	public void setItems(List<ProductItems> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", categoryName=" + categoryName + ", utilDate=" +  ", config=" + config + ", items=" + items + "]";
	}
}
