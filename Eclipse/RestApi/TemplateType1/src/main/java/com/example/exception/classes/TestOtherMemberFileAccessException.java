package com.example.exception.classes;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.exception.BaseCustomException;

/**
 * 다른 회원 파일에 접근하려고 할 때 발생시킬 커스텀 예외 클래스. 
 */
public class TestOtherMemberFileAccessException extends BaseCustomException {
	
	public TestOtherMemberFileAccessException() {
		super(CustomResponseCode.OTHER_MEMBER_FILE_ACCESS_DENIED);
	}
	
}
