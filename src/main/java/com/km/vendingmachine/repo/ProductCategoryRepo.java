package com.km.vendingmachine.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.km.vendingmachine.model.ConfigList;
import com.km.vendingmachine.model.ProductModel;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductModel, Integer>{
	
	Optional<ProductModel> findProductModelByConfig(ConfigList id);
	
	Optional<ProductModel> findProductModelById(int id);
	
	void deleteProductById(int id);
}
