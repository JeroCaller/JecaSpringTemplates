package com.example.data.entity;

import com.example.dto.request.TestProductRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, length = 11, updatable = false)
	private int id;
	
	@Column(nullable = false, length = 30, unique = true)
	private String name;
	
	@Column(nullable = false, length = 30)
	private String category;
	
	@Column(length = 1000)
	private String description;
	
	@Column(length = 11)
	private int amount;
	
	@Column(length = 11)
	private int price;
	
	public static TestProduct toEntity(TestProductRequest request) {
		
		return TestProduct.builder()
			.name(request.getName())
			.category(request.getCategory())
			.description(request.getDescription())
			.amount(request.getAmount())
			.price(request.getPrice())
			.build();
	}
	
	public static TestProduct toUpdateEntity(TestProductRequest request, int id) {
		
		return TestProduct.builder()
			.id(id)
			.name(request.getName())
			.category(request.getCategory())
			.description(request.getDescription())
			.amount(request.getAmount())
			.price(request.getPrice())
			.build();
	}
	
}
