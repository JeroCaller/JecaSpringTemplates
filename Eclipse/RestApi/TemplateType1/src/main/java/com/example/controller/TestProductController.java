package com.example.controller;

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
		TestProductResponse, 
		TestProductRequest, 
		Integer, 
		String
	> productService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") int id) {
		
		TestProductResponse productResponse = productService.getOne(id);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(productResponse)
			.build()
			.toResponse();
	}
	
	@GetMapping
	public ResponseEntity<Object> getAll(
		@RequestParam(name = "startPage", required = false) Integer startPage, 
		@RequestParam(name = "size", required = false) Integer size
	) {
		
		Object result = null;
		if (startPage == null || size == null) {
			result = productService.getAllInList();
		} else {
			Pageable pageRequest = PageUtil.toOneBasedPageable(startPage, size);
			result = productService.getAllInPage(pageRequest);
		}
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<Object> getAllByCategory(
		@PathVariable("category") String category, 
		@RequestParam(name = "startPage", required = false) Integer startPage, 
		@RequestParam(name = "size", required = false) Integer size
	) {
		
		Object result = null;
		if (startPage == null || size == null) {
			result = productService.getSomeInListBy(category);
		} else {
			Pageable pageRequest = PageUtil.toOneBasedPageable(startPage, size);
			result = productService.getSomeInPageBy(category, pageRequest);
		}
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOneProduct(@PathVariable("id") int id) {
		
		Object result = productService.deleteOneBy(id);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.PRODUCT_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@DeleteMapping("/category/{category}")
	public ResponseEntity<Object> deleteAllByCategory(
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
