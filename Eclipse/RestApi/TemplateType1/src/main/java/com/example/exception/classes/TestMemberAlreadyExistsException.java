package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class TestMemberAlreadyExistsException extends BaseCustomException {
	
	public TestMemberAlreadyExistsException() {
		super(CustomResponseCode.MEMBER_ALREADY_EXISTS);
	}
	
}
