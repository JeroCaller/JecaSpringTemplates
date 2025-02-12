package com.example.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 실행 전 현재 사용자의 인증 여부가 필요한 클래스 또는 메서드에 적용한다. 
 * AOP를 이용하여 이 기능을 수행.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthChecker {

}
