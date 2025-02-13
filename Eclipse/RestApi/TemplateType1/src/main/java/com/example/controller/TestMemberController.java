package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.TestMemberServiceImpl;
import com.example.business.interf.UserCRUDInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberHistory;
import com.example.dto.response.TestMemberResponse;
import com.example.dto.response.rest.CustomResponseCode;
import com.example.dto.response.rest.RestResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/members/my")
public class TestMemberController {
	
	private final HttpServletRequest httpRequest;
	private final UserCRUDInterface<
		TestMemberServiceImpl, TestMemberResponse, TestMemberRequest
	> memberService;
	
	/**
	 * 현재 인증된 사용자 정보 반환.
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Object> getCurrentUser() {
		
		TestMemberResponse memberResponse = memberService.getCurrentUser();
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(memberResponse)
			.build()
			.toResponse();
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<Object> searchUsername(
		@PathVariable("username") String username
	) {
		
		boolean result = memberService.exists(username);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@PostMapping
	public ResponseEntity<Object> register(
		@Valid @RequestBody TestMemberRequest memberRequest
	) {
		
		TestMemberResponse memberResponse 
			= memberService.register(memberRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.MEMBER_CREATED)
			.uri(httpRequest.getRequestURI())
			.data(memberResponse)
			.build()
			.toResponse();
	}
	
	@PutMapping
	public ResponseEntity<Object> updateMyInfo(
		@Valid @RequestBody TestMemberRequest memberRequest
	) {
		
		TestMemberHistory memberHistory
			= (TestMemberHistory) memberService.update(memberRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.MEMBER_UPDATED)
			.uri(httpRequest.getRequestURI())
			.data(memberHistory)
			.build()
			.toResponse();
	}
	
	@DeleteMapping
	public ResponseEntity<Object> unregister() {
		
		TestMemberResponse memberResponse = memberService.unregister();
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.MEMBER_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(memberResponse)
			.build()
			.toResponse();
	}

}
