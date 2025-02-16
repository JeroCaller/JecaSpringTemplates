package com.example.business.interf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 인증(로그인) 서비스 추상 인터페이스
 * 
 * @param <RES> - 응답 DTO 클래스 타입
 * @param <REQ> - 요청 DTO 클래스 타입
 */
public interface AuthInterface<RES, REQ> {
	
	/**
	 * 로그인 기능
	 * 
	 * @return
	 */
	RES login(
		REQ loginRequest, 
		HttpServletRequest httpRequest, 
		HttpServletResponse httpResponse
	);
	
	/**
	 * 현재 세션에 저장된 인증 정보 무효화.
	 * 회원 탈퇴 기능 구현 시 사용.
	 * 
	 * @return
	 */
	default void logout(
		HttpServletRequest httpRequest, 
		HttpServletResponse httpResponse
	) {}
	
}
