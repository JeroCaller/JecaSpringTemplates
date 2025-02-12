package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

/**
 * DB 내 파일 정보 삭제 실패 시 사용하는 커스텀 예외 클래스.
 */
public class FileInfoInDBNotDeletedException extends BaseCustomException {
	
	public FileInfoInDBNotDeletedException() {
		super(CustomResponseCode.FILE_INFO_NOT_DELETED);
	}
	
	public FileInfoInDBNotDeletedException(String message) {
		super(message, CustomResponseCode.FILE_INFO_NOT_DELETED);
	}
	
}
