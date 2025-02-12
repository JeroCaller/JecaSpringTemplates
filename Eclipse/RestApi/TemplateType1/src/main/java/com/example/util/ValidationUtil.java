package com.example.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 유효성 검사 관련 유틸리티 클래스.
 */
public class ValidationUtil {
	
	/**
	 * 유효성 검사 실패 시 유효하지 않은 필드와 메시지를 추출하여 반환.
	 * 
	 * @param e
	 * @return <유효하지 않은 필드명, 메시지> 형태의 HashMap 객체
	 */
	public static Map<String, String> getValidationFailedMessage(
		MethodArgumentNotValidException e
	) {
		
		Map<String, String> failedMessage = new HashMap<String, String>();
		
		e.getBindingResult().getFieldErrors().forEach(error -> {
			failedMessage.put(error.getField(), error.getDefaultMessage());
		});
		
		return failedMessage;
	}
	
}
