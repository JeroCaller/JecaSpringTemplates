package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class TestMemberNotFoundException extends BaseCustomException {
	
	public TestMemberNotFoundException() {
		super(CustomResponseCode.MEMBER_NOT_FOUND);
	}
	
}
