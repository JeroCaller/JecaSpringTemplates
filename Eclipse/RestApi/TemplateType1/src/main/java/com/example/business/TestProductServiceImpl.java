package com.example.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.business.interf.CRUDInterface;
import com.example.data.entity.TestProduct;
import com.example.data.repository.TestProductRepository;
import com.example.dto.request.TestProductRequest;
import com.example.dto.response.TestProductResponse;
import com.example.exception.classes.TestProductAlreadyExistsException;
import com.example.exception.classes.TestProductNotFoundException;
import com.example.util.ListUtil;
import com.example.util.PageUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestProductServiceImpl implements CRUDInterface<
	TestProductServiceImpl, 
	TestProductResponse, 
	TestProductRequest, 
	String
> {
	
	private final TestProductRepository testProductRepository;
	
	@Override
	public TestProductResponse getOne(String field) {
		
		TestProduct product = testProductRepository.findByName(field)
			.orElseThrow(() -> new TestProductNotFoundException());
		
		return TestProductResponse.toDto(product);
	}

	@Override
	public List<TestProductResponse> getAllInList() {
		
		List<TestProduct> allProducts = testProductRepository.findAll();
		
		if (ListUtil.isEmpty(allProducts)) {
			throw new TestProductNotFoundException();
		}
		
		return allProducts.stream()
			.map(TestProductResponse :: toDto)
			.collect(Collectors.toList());
	}

	@Override
	public Page<TestProductResponse> getAllInPage(Pageable pageRequest) {
		
		Page<TestProduct> pagedProducts = testProductRepository.findAll(pageRequest);
		
		if (PageUtil.isEmtpy(pagedProducts)) {
			throw new TestProductNotFoundException();
		}
		
		return pagedProducts.map(TestProductResponse :: toDto);
	}
	
	/**
	 * 주어진 카테고리에 해당하는 모든 데이터 조회.
	 * 
	 * @param field - 카테고리명
	 */
	@Override
	public List<TestProductResponse> getSomeInListBy(String field) {
		
		List<TestProduct> products = testProductRepository.findAllByCategory(field);
		
		if (ListUtil.isEmpty(products)) {
			throw new TestProductNotFoundException();
		}
		
		return products.stream()
			.map(TestProductResponse :: toDto)
			.collect(Collectors.toList());
	}
	
	/**
	 * 주어진 카테고리명에 해당하는 모든 데이터를 페이징하여 조회. 
	 * 
	 * @param - 조회 기준이 되는 카테고리명.
	 */
	@Override
	public Page<TestProductResponse> getSomeInPageBy(
		String field, Pageable pageRequest
	) {
		
		Page<TestProduct> products = testProductRepository
			.findAllByCategory(field, pageRequest);
		
		if (PageUtil.isEmtpy(products)) {
			throw new TestProductNotFoundException();
		}
		
		return products.map(TestProductResponse :: toDto);
	}

	@Override
	public boolean exists(String field) {
		return testProductRepository.existsByName(field);
	}

	@Override
	public TestProductResponse create(TestProductRequest request) {
		
		if (exists(request.getName())) {
			throw new TestProductAlreadyExistsException();
		}
		
		TestProduct newProduct = TestProduct.toEntity(request);
		TestProduct savedProduct = testProductRepository.save(newProduct);
		
		return TestProductResponse.toDto(savedProduct);
	}

	@Override
	public TestProductResponse update(TestProductRequest request) {
		
		TestProduct product = testProductRepository.findByName(request.getName())
			.orElseThrow(() -> new TestProductNotFoundException());
		
		TestProduct willBeUpdatedProduct = TestProduct
			.toUpdateEntity(request, product.getId());
		
		TestProduct updatedProduct = testProductRepository
			.save(willBeUpdatedProduct);
		
		return TestProductResponse.toDto(updatedProduct);
	}

	@Override
	public Object deleteOneBy(String field) {
		
		testProductRepository.deleteByName(field);
		
		// 아무것도 반환하지 않는다. 
		return null;
	}
	
	/**
	 * 특정 카테고리에 부합하는 모든 데이터 삭제.
	 * 
	 * @param field - 삭제하고자 하는 카테고리명.
	 */
	@Override
	public Object deleteSomeBy(String field) {
		
		testProductRepository.deleteAllByCategory(field);
		
		// 아무것도 반환하지 않는다. 
		return null;
	}

	@Override
	public Object deleteAll() {
		
		testProductRepository.deleteAll();
		
		// 아무것도 반환하지 않는다. 
		return null;
	}

}
