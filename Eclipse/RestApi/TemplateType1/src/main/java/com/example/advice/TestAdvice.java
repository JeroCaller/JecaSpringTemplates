package com.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.advice.annotation.RequestDTO;
import com.example.advice.annotation.TestForAOPInMethod;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Order(3)
public class TestAdvice {
	
	//@Around("@annotation(com.example.advice.annotation.TestForAOPInMethod)")
	public Object testAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		log.info("=== testAdvice 호출됨 ===");
		
		Object result = joinPoint.proceed();
		
		log.info("=== testAdvice 호출끝 ===");
		
		return result;
	}
	
	/**
	 * 포인트컷 표현식 참고 자료)<br/>
	 * 
	 * https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-common-pointcuts
	 * https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/advice.html#aop-ataspectj-advice-params-passing
	 * https://blog.leaphop.co.kr/blogs/54/Spring_Core_Technolohies_2__AOP__2___AspectJ%EB%A5%BC_%EC%9D%B4%EC%9A%A9%ED%95%B4_Aspect_%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
	 * https://www.eclipse.org/lists/aspectj-users/msg15403.html
	 * 
	 * @param joinPoint
	 * @param anno
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "@annotation(testForAOPInMethod)")
	public Object testAdvicePara(
		ProceedingJoinPoint joinPoint, 
		TestForAOPInMethod anno
	) throws Throwable {
		
		log.info("=== testAdvicePara 호출됨 ===");
		log.info(anno.toString());
		log.info(anno.DtoType().toString());
		
		Object result = joinPoint.proceed();
		
		log.info("------------ args... ----------------");
		for (Object arg : joinPoint.getArgs()) {
			log.info(arg.toString());
			log.info("{}", arg.getClass().equals(anno.DtoType()));
		}
		
		log.info(joinPoint.getTarget().toString());
		log.info(joinPoint.getSignature().getDeclaringTypeName());
		
		//log.info(anno.DtoType().getName());
		
		//log.info(param.toString());
		log.info("=== testAdvicePara 호출끝 ===");
		
		return result;
	}

}
