package com.example.exception.classes;

import com.example.exception.BaseInternalServerException;

/**
 * IOException 발생 시 이를 IORuntimeException으로 바꾼다. 
 * 
 * 서비스 계층에서 발생 시 IOException을 그대로 컨트롤러 계층으로 전파시키기 
 * 어려울 때, 또는 개발자가 예측 가능한 예외로 관리하고자 할 때 사용.
 */
public class IORuntimeException extends BaseInternalServerException {
	
	public IORuntimeException(String message) {
		super(message);
	}
	
}
