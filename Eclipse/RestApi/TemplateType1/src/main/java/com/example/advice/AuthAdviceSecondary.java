package com.example.advice;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.advice.annotation.UpdateAuth;
import com.example.business.AuthServiceImpl;
import com.example.business.interf.AuthInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.exception.aop.classes.TestTypeCastingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class AuthAdviceSecondary {
	
	private final HttpServletRequest httpRequest;
	private final HttpServletResponse httpResponse;
	private final AuthInterface<
		AuthServiceImpl, TestMemberResponse, TestMemberRequest
	> authService;
	
	/**
	 * 회원 정보 변경 시 이 사항을 현재 세션에도 반영한다.
	 * 
	 * <div>
	 * 포인트컷 표현식 설명)
	 * <p>
	 * <code>@UpdateAuth</code> 어노테이션은 대상 메서드에 적용한다. 
	 * </p>

	 * </div><br/>
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 * 
	 */
	@Around("@annotation(updateAuth)")
	public Object updateAuthInfo(ProceedingJoinPoint joinPoint, UpdateAuth auth) 
		throws Throwable {
		
		log.info("=== AOP-updateAuthInfo 호출됨. ===");
		log.info(auth.DtoType().toString());
		
		Object result = joinPoint.proceed();
		
		for(Object arg : joinPoint.getArgs()) {
			if (auth.DtoType().isInstance(arg)) {
				// 새로 수정된 회원 정보로 세션 내 정보 재설정.
				// authService.login() 메서드의 첫 번째 인자는 
				// Object가 아닌 TestMemberRequest라는 Type으로 고정되어 있으므로, 
				// Object 타입인 arg를 해당 메서드의 첫 번째 인자로 넣기 위한 코드.
				Method loginMethod = authService.getClass().getMethod(
					"login", 
					auth.DtoType(), 
					HttpServletRequest.class, 
					HttpServletResponse.class
				);
				loginMethod.invoke(authService, arg, httpRequest, httpResponse);
				log.info("login 메서드 호출됨.");
				break;
			}
		}
		
		log.info("=== AOP-updateAuthInfo 끝. ===");
		
		return result;
	}
	
	/**
	 * 현재 세션에 기록된 인증 정보를 무효화.
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.example.advice.annotation.InvalidateAuth)")
	public Object invalidateAuth(ProceedingJoinPoint joinPoint) throws Throwable {
		
		log.info("=== AOP-invalidateAuth 호출됨. ===");
		
		Object result = joinPoint.proceed();
		
		authService.logout(httpRequest, httpResponse);
		
		log.info("=== AOP-invalidateAuth 끝. ===");
		
		return result;
		
	}
	
}
