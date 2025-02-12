package com.example.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.data.entity.TestProduct;

public interface TestProductRepository 
	extends JpaRepository<TestProduct, Integer> {
	
	Optional<TestProduct> findByName(String name);
	
	List<TestProduct> findAllByCategory(String category);
	
	Page<TestProduct> findAllByCategory(String category, Pageable pageRequest);
	
	boolean existsByName(String name);
	
	void deleteByName(String name);
	
	void deleteAllByCategory(String category);
	
}
