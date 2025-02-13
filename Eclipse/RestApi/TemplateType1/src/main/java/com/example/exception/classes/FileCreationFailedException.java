package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class FileCreationFailedException extends BaseCustomException {
	
	public FileCreationFailedException() {
		super(CustomResponseCode.FILE_NOT_CREATED);
	}
	
	public FileCreationFailedException(String message) {
		super(message, CustomResponseCode.FILE_NOT_CREATED);
	}
	
}
