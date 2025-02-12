package com.example.exception.aop.classes;

import com.example.exception.BaseAOPException;

public class TestTypeCastingException extends BaseAOPException {
	
	public TestTypeCastingException() {
		super();
	}
	
	public TestTypeCastingException(String message) {
		super("타입 캐스팅 중 예외 발생 : " + message);
	}
	
}
