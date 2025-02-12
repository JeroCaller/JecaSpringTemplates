package com.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.business.AuthServiceImpl;
import com.example.business.interf.AuthInterface;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.exception.aop.classes.TestTypeCastingException;
import com.example.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthAdvice {
	
	private final HttpServletRequest httpRequest;
	private final HttpServletResponse httpResponse;
	private final AuthInterface<
		AuthServiceImpl, TestMemberResponse, TestMemberRequest
	> authService;
	
	/**
	 * 현재 사용자가 인증되었는지 여부를 체크한다. 
	 * 
	 * <div>
	 * 포인트컷 표현식 설명) <br/>
	 * <ul>
	 * <li><code>@within(annotation)</code> - 클래스 수준에 적용된 어노테이션이 적용된 모든 클래스 대상.</li>
	 * <li><code>@annotation(annotation)</code> - 메서드 수준에 적용된 어노테이션이 적용된 모든 
	 * 메서드 대상. 앞에 !가 붙었으므로, 해당 어노테이션이 적용되지 않았을 때만 적용됨. </li>
	 * </ul>
	 * 
	 * 즉 여기서 사용된 포인트컷 표현식은 클래스 수준으로 <code>@AuthChecker</code> 어노테이션이 
	 * 적용되어 있고, <code>@ExcludeFromAOP</code> 어노테이션은 적용되지 않은 클래스 내 모든 메서드에 
	 * 적용시키겠다는 뜻이다. 
	 * </div><br/>
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("""
		( @within(com.example.advice.annotation.AuthChecker) &&
		!@annotation(com.example.advice.annotation.ExcludeFromAOP) ) || 
		@annotation(com.example.advice.annotation.AuthChecker)
	""")
	public Object authChecker(ProceedingJoinPoint joinPoint) throws Throwable {
		
		log.info("=== AOP-authChecker 호출됨. ===");
		
		// 현재 인증된 사용자 정보를 반환하는 코드. 
		// 만약 현재 사용자가 인증되지 않았다면 
		// NotAuthenticatedUserException이 발생하며, 
		// 이는 예외 처리되어 응답된다. 
		AuthUtil.getCurrentUserInfo();
		
		Object result = joinPoint.proceed();
		
		log.info("=== AOP-authChecker 끝. ===");
		
		return result;
	}
	
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
		args(.., @RequestDTO requestDto, ..)
	""")
	public Object updateAuthInfo(ProceedingJoinPoint joinPoint, Object requestDto) 
		throws Throwable {
		
		log.info("=== AOP-updateAuthInfo 호출됨. ===");
		
		Object result = joinPoint.proceed();
		
		// 새로 수정된 회원 정보로 세션 내 정보 재설정.
		// 타입 캐스팅 실패 시 ClassCastException (RuntimeException) 발생. 
		try {
			authService.login(
				(TestMemberRequest) requestDto, 
				httpRequest, 
				httpResponse
			);
		} catch (ClassCastException e) {
			throw new TestTypeCastingException(e.getMessage());
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
	@Around("@annotation(com.example.advice.anntation.InvalidateAuth)")
	public Object invalidateAuth(ProceedingJoinPoint joinPoint) throws Throwable {
		
		log.info("=== AOP-invalidateAuth 호출됨. ===");
		
		Object result = joinPoint.proceed();
		
		authService.logout(httpRequest, httpResponse);
		
		log.info("=== AOP-invalidateAuth 끝. ===");
		
		return result;
		
	}
	
}
