package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

/**
 * 삭제할 파일이 없을 때 사용할 커스텀 예외 클래스.
 */
public class NoFileToDeleteException extends BaseCustomException {
	
	public NoFileToDeleteException() {
		super(CustomResponseCode.NO_FILE_TO_DELETE);
	}
	
}
