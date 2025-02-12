package com.example.exception.classes;

import com.example.exception.BaseInternalServerException;

public class FileNotFoundOrReadableException extends BaseInternalServerException {
	
	public FileNotFoundOrReadableException(String message) {
		super(message);
	}
	
}
