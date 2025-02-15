package com.example.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 세션 내 인증 정보를 바꿔야하는 메서드에 적용.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateAuth {
	
	/**
	 * 회원 정보 요청 DTO 타입을 넣는다. AuthInterface.login() 메서드의 
	 * 첫 번째 파라미터의 타입과 동일해야 한다. 
	 * 
	 * @return
	 */
	Class<?> DtoType();
}
