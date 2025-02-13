package com.example.business;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.advice.annotation.AuthChecker;
import com.example.advice.annotation.ExcludeFromAOP;
import com.example.advice.annotation.InvalidateAuth;
import com.example.advice.annotation.RequestDTO;
import com.example.advice.annotation.UpdateAuth;
import com.example.business.interf.FileIOInterface;
import com.example.business.interf.UserCRUDInterface;
import com.example.data.entity.TestMember;
import com.example.data.repository.TestMemberRepository;
import com.example.dto.request.TestFileRequest;
import com.example.dto.request.TestMemberRequest;
import com.example.dto.response.TestFileResponse;
import com.example.dto.response.TestMemberHistory;
import com.example.dto.response.TestMemberResponse;
import com.example.exception.classes.TestMemberAlreadyExistsException;
import com.example.util.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AuthChecker
@Transactional
public class TestMemberServiceImpl implements UserCRUDInterface<
	TestMemberServiceImpl, TestMemberResponse, TestMemberRequest
> {
	
	private final TestMemberRepository testMemberRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsService customUserDetailsService;
	private final FileIOInterface<
		TestFileServiceImpl, TestFileResponse, TestFileRequest, Integer, Integer
	> fileService;
	
	@Override
	@ExcludeFromAOP
	public TestMemberResponse getCurrentUser() {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		return TestMemberResponse.toDto(currentMember);
	}
	
	@Override
	@ExcludeFromAOP
	public TestMemberResponse register(TestMemberRequest loginRequest) {
		
		// 사용자가 입력한 회원 가입 정보가 이미 DB 상에 존재하는지 확인. 
		// 존재 시 예외를 던지며 메서드를 종료한다.
		try {
			customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
			throw new TestMemberAlreadyExistsException();
		} catch (UsernameNotFoundException e) {
			// Empty Code. Continue.
		}
		
		// 패스워드는 인코딩하여 DB에 저장. 
		String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
		
		TestMember willBeSavedMember = TestMember.builder()
			.username(loginRequest.getUsername())
			.password(encodedPassword)
			.build();
		TestMember savedMember = testMemberRepository.save(willBeSavedMember);
		
		return TestMemberResponse.toDto(savedMember);
	}
	
	@Override
	@UpdateAuth
	public Object update(@RequestDTO TestMemberRequest memberRequest) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		// 패스워드는 인코딩하여 DB에 저장. 
		String encodedPassword = passwordEncoder.encode(
			memberRequest.getPassword()
		);
		
		TestMember willBeUpdatedMember = TestMember.builder()
			.id(currentMember.getId())
			.username(memberRequest.getUsername())
			.password(encodedPassword)
			.build();
		TestMember updatedMember = testMemberRepository.save(willBeUpdatedMember);
		
		return TestMemberHistory.builder()
			.pastUsername(currentMember.getUsername())
			.newUsername(updatedMember.getUsername())
			.createdAt(updatedMember.getCreatedAt())
			.updatedAt(updatedMember.getUpdatedAt())
			.build();
	}
	
	@Override
	@InvalidateAuth
	public TestMemberResponse unregister() {
		
		// 현재 인증된 사용자 정보 취득.
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		// 탈퇴하고자 하는 회원이 보유한 모든 파일을 서버 내에서 삭제.
		fileService.deleteAllFiles();
		
		// 회원 정보를 DB에서 삭제.
		testMemberRepository.delete(currentMember);
		
		return TestMemberResponse.toDto(currentMember);
	}

	@Override
	@ExcludeFromAOP
	public boolean exists(String username) {
		
		Optional<TestMember> targetMember 
			= testMemberRepository.findByUsername(username);
		if (targetMember.isEmpty()) return false;
		return true;
	}
	
}
