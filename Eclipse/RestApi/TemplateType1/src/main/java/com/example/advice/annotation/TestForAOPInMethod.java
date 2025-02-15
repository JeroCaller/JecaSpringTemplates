package com.example.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 수준에 적용할 어노테이션. AOP 테스트용
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestForAOPInMethod {
	
	Class<?> DtoType();
	
}
