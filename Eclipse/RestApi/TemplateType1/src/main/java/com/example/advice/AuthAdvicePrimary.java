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
import com.example.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class AuthAdvicePrimary {
	
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
	
}
