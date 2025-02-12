package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.AuthServiceImpl;
import com.example.business.interf.AuthInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.dto.response.rest.RestResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final HttpServletRequest httpRequest;
	private final HttpServletResponse httpResponse;
	
	private final AuthInterface<
		AuthServiceImpl, TestMemberResponse, TestMemberRequest
	> authService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(
		@Valid @RequestBody TestMemberRequest memberRequest
	) {
		
		TestMemberResponse memberResponse = authService.login(
			memberRequest, 
			httpRequest, 
			httpResponse
		);
		
		return RestResponse.builder()
			.uri(httpRequest.getRequestURI())
			.data(memberResponse)
			.build()
			.toResponse();
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Object> logout() {
		
		return RestResponse.builder()
			.uri(httpRequest.getRequestURI())
			.data("로그아웃 성공")
			.build()
			.toResponse();
	}

}
