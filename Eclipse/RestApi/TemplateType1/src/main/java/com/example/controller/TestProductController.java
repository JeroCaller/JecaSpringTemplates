package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.TestProductServiceImpl;
import com.example.business.interf.CRUDInterface;
import com.example.dto.request.TestProductRequest;
import com.example.dto.response.TestProductResponse;
import com.example.dto.response.rest.CustomResponseCode;
import com.example.dto.response.rest.RestResponse;
import com.example.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/products")
public class TestProductController {
	
	private final HttpServletRequest httpRequest;
	private final CRUDInterface<
		TestProductServiceImpl, 
		TestProductResponse, 
		TestProductRequest, 
		String
	> productService;
	
	@GetMapping("/{name}")
	public ResponseEntity<Object> getOne(@PathVariable("name") String name) {
		
		TestProductResponse productResponse = productService.getOne(name);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(productResponse)
			.build()
			.toResponse();
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllInList() {
		
		List<TestProductResponse> products = productService.getAllInList();
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(products)
			.build()
			.toResponse();
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllInPage(
		@RequestParam("startPage") int startPage, 
		@RequestParam("size") int size
	) {
		
		Pageable pageRequest = PageUtil.toOneBasedPageable(startPage, size);
		
		Page<TestProductResponse> products = productService
			.getAllInPage(pageRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(products)
			.build()
			.toResponse();
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<Object> getSomeInListByCategory(
		@PathVariable("category") String category
	) {
		
		List<TestProductResponse> products = productService
			.getSomeInListBy(category);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(products)
			.build()
			.toResponse();
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<Object> getSomeInPageByCategory(
		@PathVariable("category") String category,
		@RequestParam("startPage") int startPage,
		@RequestParam("size") int size
	) {
		
		Pageable pageRequest = PageUtil.toOneBasedPageable(startPage, size);
		
		Page<TestProductResponse> products = productService
			.getSomeInPageBy(category, pageRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(products)
			.build()
			.toResponse();
	}
	
	@GetMapping("/exist/{name}")
	public ResponseEntity<Object> exists(@PathVariable("name") String name) {
		
		boolean result = productService.exists(name);
		
		return RestResponse.builder()
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@PostMapping
	public ResponseEntity<Object> registerProduct(
		@Valid @RequestBody TestProductRequest productRequest
	) {
		
		TestProductResponse productResponse 
			= (TestProductResponse) productService.create(productRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_CREATED)
			.uri(httpRequest.getRequestURI())
			.data(productResponse)
			.build()
			.toResponse();
	}
	
	@PutMapping
	public ResponseEntity<Object> updateProduct(
		@Valid @RequestBody TestProductRequest productRequest
	) {
		
		TestProductResponse productResponse 
			= (TestProductResponse) productService.update(productRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_UPDATED)
			.uri(httpRequest.getRequestURI())
			.data(productResponse)
			.build()
			.toResponse();
	}
	
	@DeleteMapping("/{name}")
	public ResponseEntity<Object> deleteOneByName(
		@PathVariable("name") String name
	) {
		
		Object result = productService.deleteOneBy(name);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@DeleteMapping("/category/{category}")
	public ResponseEntity<Object> deleteSomeByCategory(
		@PathVariable("category") String category
	) {
		
		Object result = productService.deleteSomeBy(category);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteAll() {
		
		Object result = productService.deleteAll();
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
}
