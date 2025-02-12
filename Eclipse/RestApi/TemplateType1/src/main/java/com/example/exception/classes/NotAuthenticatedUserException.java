package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

public class NotAuthenticatedUserException extends BaseCustomException {
	
	public NotAuthenticatedUserException() {
		super(CustomResponseCode.NOT_AUTHENTICATED);
	}
	
}
