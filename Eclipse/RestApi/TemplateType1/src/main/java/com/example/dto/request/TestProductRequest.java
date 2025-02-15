package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TestProductRequest {
	
	private Integer id;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String name;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String category;
	
	@Size(min = 0, max = 1000)
	private String description;
	
	@PositiveOrZero
	private int amount;
	
	@PositiveOrZero
	private int price;
	
}
