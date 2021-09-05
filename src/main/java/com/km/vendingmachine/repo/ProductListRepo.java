package com.km.vendingmachine.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.km.vendingmachine.model.ProductItems;

@Repository
public interface ProductListRepo extends JpaRepository<ProductItems, Integer>{

	Optional<ProductItems> findProductItemById(int id);
	
	void deleteItemById(int id);
}
