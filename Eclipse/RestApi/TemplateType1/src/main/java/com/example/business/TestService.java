package com.example.business;

import org.springframework.stereotype.Service;

import com.example.advice.annotation.TestForAOPInMethod;
import com.example.dto.request.TestProductRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 테스트용 서비스 클래스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
	
	@TestForAOPInMethod(DtoType = TestProductRequest.class)
	public void aopTest(TestProductRequest request, String good) {
		log.info("aopTest");
		log.info(request.toString());
	}
	
}
