package com.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.business.AuthServiceImpl;
import com.example.business.interf.AuthInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.exception.aop.classes.TestTypeCastingException;

import jakarta.annotation.PostConstruct;
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
	 * <code>@RequestDTO</code> 어노테이션은 <code>@UpdateAuth</code> 어노테이션이 적용된 메서드의 DTO 클래스 타입의 
	 * 파라미터에 적용한다. <code>@RequestDTO</code> 어노테이션은 수정된 회원 정보가 담긴 요청 DTO 클래스 타입 
	 * 파라미터에 적용한다. 
	 * </p>
	 * 
	 * <p>
	 * args 표현식은 메서드의 인자를 기준으로 포인트컷을 지정할 때 사용되는 표현식이라고 한다. 
	 * 괄호 안에는 적용 대상 메서드의 파라미터 타입을 기준으로 매칭한다고 한다. 
	 * 여기서 사용된 표현식에서, <code>@RequestDTO requestDTO</code>라고 설정하였는데, 해당 파라미터의 이름은 
	 * 이 AOP 메서드의 파라미터 명과 일치시켜야 한다. 그래야 AOP 메서드 내부에서 해당 
	 * 인자를 가지고 작업을 할 수 있게 된다.
	 * </p>
	 * </div><br/>
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("""
		@annotation(com.example.advice.annotation.UpdateAuth) &&
		@args(com.example.advice.annotation.RequestDTO)
	""")
	/*
	@Around("""
		@annotation(com.example.advice.annotation.UpdateAuth)
	""")
	*/
	public Object updateAuthInfo(ProceedingJoinPoint joinPoint) 
		throws Throwable {
		
		log.info("=== AOP-updateAuthInfo 호출됨. ===");
		
		Object result = joinPoint.proceed();
		
		// 새로 수정된 회원 정보로 세션 내 정보 재설정.
		// 타입 캐스팅 실패 시 ClassCastException (RuntimeException) 발생.
		/*
		try {
			authService.login(
				(TestMemberRequest) requestDto, 
				httpRequest, 
				httpResponse
			);
		} catch (ClassCastException e) {
			throw new TestTypeCastingException(e.getMessage());
		}*/
		
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
