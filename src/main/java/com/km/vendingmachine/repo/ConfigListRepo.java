package com.km.vendingmachine.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.km.vendingmachine.model.ConfigList;

@Repository
public interface ConfigListRepo extends JpaRepository<ConfigList, Integer>{

	Optional<ConfigList>  findConfigListByRows(int rows);
	
	Optional<ConfigList>  findConfigListById(int id);
}
