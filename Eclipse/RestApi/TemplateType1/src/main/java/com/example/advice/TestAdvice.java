package com.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Order(3)
public class TestAdvice {
	
	@Around("@annotation(com.example.advice.annotation.TestForAOPInMethod)")
	public Object testAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		log.info("=== testAdvice 호출됨 ===");
		
		Object result = joinPoint.proceed();
		
		log.info("=== testAdvice 호출끝 ===");
		
		return result;
	}

}
