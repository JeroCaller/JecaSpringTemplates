package com.example.exception;

import com.example.dto.response.rest.CustomResponseCode;

import lombok.Getter;

/**
 * AOP에서 문제 발생 시 사용할 최상위 AOP 커스텀 예외 클래스.
 */
@Getter
public class BaseAOPException extends RuntimeException {
	
	protected CustomResponseCode responseCode;
	
	public BaseAOPException() {
		super();
		this.responseCode = CustomResponseCode.AOP_ERROR;
	}
	
	public BaseAOPException(CustomResponseCode responseCode) {
		super();
		this.responseCode = responseCode;
	}
	
	public BaseAOPException(String message) {
		super(message);
		this.responseCode = CustomResponseCode.AOP_ERROR;
	}
	
	public BaseAOPException(String message, CustomResponseCode responseCode) {
		super(message);
		this.responseCode = responseCode;
	}
	
}
