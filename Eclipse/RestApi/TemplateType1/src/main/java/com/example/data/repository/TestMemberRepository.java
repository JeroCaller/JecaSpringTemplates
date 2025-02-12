package com.example.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.data.entity.TestMember;

public interface TestMemberRepository 
	extends JpaRepository<TestMember, Integer> {
	
	Optional<TestMember> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	void deleteByUsername(String username);
	
}
