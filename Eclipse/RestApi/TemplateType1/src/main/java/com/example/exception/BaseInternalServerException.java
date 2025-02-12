package com.example.exception;

import com.example.dto.response.rest.CustomResponseCode;

import lombok.Getter;

/**
 * 서버 내에서 예외 발생 시 사용할 최상위 커스텀 예외 클래스. 
 * 서버 내 예외 메시지는 클라이언트에 노출되면 안되기에 이렇게 따로 제작함. 
 */
@Getter
public class BaseInternalServerException extends RuntimeException {
	
	protected CustomResponseCode responseCode;
	
	public BaseInternalServerException() {
		super();
		this.responseCode = CustomResponseCode.INTERNAL_SERVER_ERROR;
	}
	
	public BaseInternalServerException(CustomResponseCode responseCode) {
		super();
		this.responseCode = responseCode;
	}
	
	public BaseInternalServerException(String message) {
		super(message);
		this.responseCode = CustomResponseCode.INTERNAL_SERVER_ERROR;
	}
	
	public BaseInternalServerException(String message, CustomResponseCode responseCode) {
		super(message);
		this.responseCode = responseCode;
	}
	
}
