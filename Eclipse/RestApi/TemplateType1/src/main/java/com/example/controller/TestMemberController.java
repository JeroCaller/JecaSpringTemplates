package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.interf.UserCRUDInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.dto.response.rest.CustomResponseCode;
import com.example.dto.response.rest.RestResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/members")
public class TestMemberController {
	
	private final HttpServletRequest httpRequest;
	private final UserCRUDInterface<
		TestMemberResponse, TestMemberRequest
	> memberService;
	
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
	
}
