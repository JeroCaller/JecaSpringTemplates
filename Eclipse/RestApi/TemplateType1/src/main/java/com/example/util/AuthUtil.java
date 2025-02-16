package com.example.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.common.UserRole;
import com.example.exception.classes.NotAuthenticatedUserException;

/**
 * 인증 관련 유틸리티 클래스.
 */
public class AuthUtil {
	
	public static Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * 현재 로그인(인증)된 사용자의 정보 반환.
	 * 
	 * @return 회원 응답 DTO 타입.
	 */
	public static UserDetails getCurrentUserInfo() 
		throws NotAuthenticatedUserException 
	{
		
		Authentication auth = SecurityContextHolder.getContext()
			.getAuthentication();
		
		if (auth == null || !auth.isAuthenticated() || isAnonymous(auth)) {
			throw new NotAuthenticatedUserException();
		}
		
		return (UserDetails) auth.getPrincipal();
	}
	
	/**
	 * 현재 사용자가 인증되지 않은 익명의 사용자인지 판별.
	 * 
	 * @param auth
	 * @return
	 */
	public static boolean isAnonymous(Authentication auth) {
		
		for (GrantedAuthority authz : auth.getAuthorities()) {
			if (authz.getAuthority().equals(UserRole.ANONYMOUS.getRole())) {
				return true;  // 익명의 사용자로 판단
			}
		}
		return false; // 인증된 사용자
		
	}
	
	/**
	 * 현재 사용자가 인증되지 않은 익명의 사용자인지 판별.
	 * 
	 * @return
	 */
	public static boolean isAnonymous() {
		
		Authentication auth = SecurityContextHolder.getContext()
			.getAuthentication();
		
		for (GrantedAuthority authz : auth.getAuthorities()) {
			if (authz.getAuthority().equals(UserRole.ANONYMOUS.getRole())) {
				return true;  // 익명의 사용자로 판단
			}
		}
		
		return false; // 인증된 사용자
	}
	
}
