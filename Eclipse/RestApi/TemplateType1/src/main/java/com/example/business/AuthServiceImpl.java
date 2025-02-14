package com.example.business;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.business.interf.AuthInterface;
import com.example.data.entity.TestMember;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestMemberResponse;
import com.example.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthInterface<
	AuthServiceImpl, 
	TestMemberResponse, 
	TestMemberRequest
> {
	
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;
	private final SecurityContextLogoutHandler logoutHandler;
	
	@Override
	public TestMemberResponse login(
		TestMemberRequest loginRequest, 
		HttpServletRequest httpRequest,
		HttpServletResponse httpResponse
	) {
		
		// 유저 닉네임 및 패스워드 기반으로 인증 토큰을 생성.
		UsernamePasswordAuthenticationToken token = 
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()
			);
		
		// 인증 매니저로 인증 시도. 내부적으로
		// CustomUserDetailService 클래스의 loadUserByUsername() 호출하여
		// 사용자 정보를 획득. 따라서 개발자가 별도로 CustomUserDetailService를 
		// 호출하여 사용할 필요가 없다고 함. 
		Authentication authentication = authenticationManager
			.authenticate(token);
		
		// 인증 성공 시 SecurityContextHolder에 인증 객체 전달.
		// 이제 어느 클래스건 SecurityContextHolder.getContext().getAuthentication()
		// 만 호출하면 현재 인증된 사용자 정보에 접근 가능.
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication); // 인증 정보를 세션에 저장.
		
		// 인증 정보가 포함된 세션을 서버에 영속화하여 다른 request가 와도 인증 정보를 유지하도록 한다.
		// 이 코드를 사용하지 않으면 인증을 하더라도 그 인증 정보가 유지되지 않아서 
		// 다른 request가 오면 다시 익명 사용자로 변경된다는 문제가 발생한다. 
		securityContextRepository.saveContext(
			securityContext, 
			httpRequest, 
			httpResponse
		);
		
		// 세션에 저장된 유저 정보를 DTO로 변환하여 반환.
		return TestMemberResponse
			.toDto((TestMember) AuthUtil.getCurrentUserInfo());
	}

	@Override
	public void logout(
		HttpServletRequest httpRequest, 
		HttpServletResponse httpResponse
	) {
		
		logoutHandler.logout(httpRequest, httpResponse, AuthUtil.getAuth());

	}
	
}
