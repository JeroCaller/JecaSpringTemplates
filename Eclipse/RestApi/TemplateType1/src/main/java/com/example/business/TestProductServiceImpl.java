package com.example.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
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
@Primary
public class TestProductServiceImpl implements CRUDInterface< 
	TestProductResponse, 
	TestProductRequest, 
	Integer,
	String
> {
	
	private final TestProductRepository testProductRepository;
	
	@Override
	public TestProductResponse getOne(Integer field) {
		
		TestProduct product = testProductRepository.findById(field)
			.orElseThrow(() -> new TestProductNotFoundException());
		
		return TestProductResponse.toDto(product);
	}

	@Override
	public List<TestProductResponse> getAllInList() {
		
		List<TestProduct> products = testProductRepository.findAll();
		
		if (ListUtil.isEmpty(products)) {
			throw new TestProductNotFoundException();
		}
		
		return products.stream()
			.map(TestProductResponse :: toDto)
			.collect(Collectors.toList());
	}

	@Override
	public Page<TestProductResponse> getAllInPage(Pageable pageRequest) {
		
		Page<TestProduct> products = testProductRepository.findAll(pageRequest);
		
		if (PageUtil.isEmtpy(products)) {
			throw new TestProductNotFoundException();
		}
		
		return products.map(TestProductResponse :: toDto);
	}

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
	 * 세 재품 등록. 
	 * 이미 똑같은 이름의 제품이 있으면 예외 발생하면 제품 등록 진행 중단됨. 
	 */
	@Override
	public TestProductResponse create(TestProductRequest request) {
		
		Optional<TestProduct> checkProduct = testProductRepository
			.findByName(request.getName());
		if (checkProduct.isPresent()) {
			throw new TestProductAlreadyExistsException();
		}
		
		TestProduct targetProduct = TestProduct.toEntity(request);
		TestProduct savedProduct = testProductRepository.save(targetProduct);
		
		return TestProductResponse.toDto(savedProduct);
	}
	
	/**
	 * 제품 정보 한 건 수정. 
	 * 
	 * 기존 DB 내 없는 데이터에 대해선 예외를 발생시키면서 종료됨. 
	 * 새 데이터 생성을 원하면 create() 메서드를 대신 사용.
	 */
	@Override
	public TestProductResponse update(TestProductRequest request) {
		
		Optional<TestProduct> checkProduct = testProductRepository
			.findById(request.getId());
		if (checkProduct.isEmpty()) {
			throw new TestProductNotFoundException();
		}
				
		TestProduct targetProduct = TestProduct.toUpdateEntity(request);
		TestProduct updatedProduct = testProductRepository.save(targetProduct);
		
		return TestProductResponse.toDto(updatedProduct);
	}

	@Override
	public Object deleteOneBy(Integer field) {
		
		testProductRepository.deleteById(field);

		// 반환값 없음.
		return null;
	}

	@Override
	public Object deleteSomeBy(String field) {
		
		testProductRepository.deleteAllByCategory(field);
		
		// 반환값 없음.
		return null;
	}

	@Override
	public Object deleteAll() {
		
		testProductRepository.deleteAll();
		
		// 반환값 없음.
		return null;
	}

}
