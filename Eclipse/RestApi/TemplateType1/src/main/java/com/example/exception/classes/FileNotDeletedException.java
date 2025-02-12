package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class FileNotDeletedException extends BaseCustomException {
	
	public FileNotDeletedException() {
		super(CustomResponseCode.FILE_NOT_DELETED);
	}
	
	public FileNotDeletedException(String message) {
		super(message, CustomResponseCode.FILE_NOT_DELETED);
	}
}
