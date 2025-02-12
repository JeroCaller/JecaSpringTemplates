package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class TestFileNotFoundException extends BaseCustomException {
	
	public TestFileNotFoundException() {
		super(CustomResponseCode.FILE_NOT_FOUND);
	}
	
}
