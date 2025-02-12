package com.example.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 세션에 저장된 인증 정보 무효화 작업이 필요한 메서드에 적용하는 어노테이션. 
 * AOP를 이용하여 이 작업이 실행됨. 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidateAuth {

}
