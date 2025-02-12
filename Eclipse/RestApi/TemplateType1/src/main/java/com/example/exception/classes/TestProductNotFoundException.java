package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class TestProductNotFoundException extends BaseCustomException {
	
	public TestProductNotFoundException() {
		super(CustomResponseCode.PRODUCT_NOT_FOUND);
	}
	
}
