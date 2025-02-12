package com.example.business;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.data.entity.TestMember;
import com.example.data.repository.TestMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final TestMemberRepository testMemberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		TestMember member = testMemberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(
				username + "님은 존재하지 않습니다."
			));
		
		return member;
	}

}
