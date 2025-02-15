package com.example.dto.response;

import com.example.data.entity.TestProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestProductResponse {
	
	private Integer id;
	private String name;
	private String category;
	private String description;
	private int amount;
	private int price;
	
	public static TestProductResponse toDto(TestProduct entity) {
		
		return TestProductResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.category(entity.getCategory())
			.description(entity.getDescription())
			.amount(entity.getAmount())
			.price(entity.getPrice())
			.build();
	}
	
}
