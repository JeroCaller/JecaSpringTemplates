package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class TestProductAlreadyExistsException extends BaseCustomException {
	
	public TestProductAlreadyExistsException() {
		super(CustomResponseCode.PRODUCT_ALREADY_EXISTS);
	}
	
}
