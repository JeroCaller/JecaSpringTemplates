package com.example.exception;

import com.example.dto.response.rest.CustomResponseCode;

import lombok.Getter;

/**
 * 모든 커스텀 예외 클래스의 최상위 클래스. 
 * 커스텀 예외 클래스 생성 시 이 클래스를 상속받도록 한다.
 */
@Getter
public class BaseCustomException extends RuntimeException {
	
	protected CustomResponseCode responseCode;
	
	public BaseCustomException() {
		super();
		this.responseCode = CustomResponseCode.UNKNOWN_ERROR;
	}
	
	public BaseCustomException(CustomResponseCode responseCode) {
		super();
		this.responseCode = responseCode;
	}
	
	public BaseCustomException(String message) {
		super(message);
		this.responseCode = CustomResponseCode.UNKNOWN_ERROR;
	}
	
	public BaseCustomException(String message, CustomResponseCode responseCode) {
		super(message);
		this.responseCode = responseCode;
	}
	
}
