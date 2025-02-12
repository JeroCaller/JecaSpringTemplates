package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 역할 상수 모음.
 */
@Getter
@AllArgsConstructor
public enum UserRole {
	
	ANONYMOUS("ROLE_ANONYMOUS"),
	USER("ROLE_USER");
	
	private String role;
	
}
