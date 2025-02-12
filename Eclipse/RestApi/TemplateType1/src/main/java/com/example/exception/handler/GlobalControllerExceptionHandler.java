package com.example.exception.handler;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.dto.response.rest.CustomResponseCode;
import com.example.dto.response.rest.RestResponse;
import com.example.exception.BaseAOPException;
import com.example.exception.BaseCustomException;
import com.example.exception.BaseInternalServerException;
import com.example.util.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 모든 컨트롤러에서 발생하는 예외를 처리하는 클래스.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerExceptionHandler {
	
	private HttpServletRequest httpRequest;
	
	/**
	 * 커스텀 예외 처리 메서드.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BaseCustomException.class)
	public ResponseEntity<Object> handleCustomException(BaseCustomException e) {
		
		return RestResponse.builder()
			.responseCode(e.getResponseCode())
			.uri(httpRequest.getRequestURI())
			.build()
			.toResponse();
	}
	
	/**
	 * 서버 내 AOP에서 발생한 예외 처리.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BaseAOPException.class)
	public ResponseEntity<Object> handleAOPException(BaseAOPException e) {
		
		log.error("=== 서버 내 AOP에서 예기치 못한 에러가 발생했습니다. 다음 에러 메시지를 참고하세요. ===");
		log.error(e.getMessage());
		
		return RestResponse.builder()
			.responseCode(e.getResponseCode())
			.uri(httpRequest.getRequestURI())
			.build()
			.toResponse();
	}
	
	/**
	 * 서버 내 예외 발생 시 발생하는 커스텀 서버 예외를 처리한다. 
	 * 
	 * 서버 내 예외 메시지는 클라이언트에 노출되지 않게끔 주의. 
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BaseInternalServerException.class)
	public ResponseEntity<Object> handleInternalServerException(
		BaseInternalServerException e
	) {
		
		log.error("=== 서버 내 예외 발생. ===");
		log.error(e.getMessage());
		
		return RestResponse.builder()
			.responseCode(e.getResponseCode())
			.uri(httpRequest.getRequestURI())
			.build()
			.toResponse();
	}
	
	/**
	 * 예기치 못한 에러 처리 메서드
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleUnexpectedException(Exception e) {
		
		log.error("=== 예기치 못한 에러가 발생했습니다. 다음 에러 메시지를 참고하세요. ===");
		log.error(e.getMessage());
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.UNKNOWN_ERROR)
			.uri(httpRequest.getRequestURI())
			.build()
			.toResponse();
	}
	
	/**
	 * 유효성 검사 실패 시 발생하는 예외 처리 메서드.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationFailedException(
		MethodArgumentNotValidException e
	) {
		
		Map<String, String> validationFailedMessage 
			= ValidationUtil.getValidationFailedMessage(e);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.VALIDATION_FAILED)
			.uri(httpRequest.getRequestURI())
			.validFailedMsg(validationFailedMessage)
			.build()
			.toResponse();
	}
	
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFoundException(
		UsernameNotFoundException e
	) {
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.MEMBER_NOT_FOUND)
			.uri(httpRequest.getRequestURI())
			.data(e.getMessage())
			.build()
			.toResponse();
	}
	
}
