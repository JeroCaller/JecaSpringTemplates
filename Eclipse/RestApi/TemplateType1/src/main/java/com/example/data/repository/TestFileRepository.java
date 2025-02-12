package com.example.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.data.entity.TestFile;
import com.example.data.entity.TestMember;

public interface TestFileRepository
	extends JpaRepository<TestFile, Integer> {
	
	/**
	 * 특정 회원이 보유한 모든 파일 정보를 페이징하여 반환.
	 * 
	 * @param member
	 * @param pageRequest
	 * @return
	 */
	@Query("SELECT f FROM TestFile f JOIN f.member m WHERE m = :#{#member}")
	Page<TestFile> findAllByMember(
		@Param("member") TestMember member, 
		Pageable pageRequest
	);
	
	/**
	 * 특정 회원의 모든 파일 정보 조회.
	 * 
	 * @param member
	 * @return
	 */
	@Query("SELECT f FROM TestFile f JOIN f.member m WHERE m = :#{#member}")
	List<TestFile> findAllByMember(@Param("member") TestMember member);

}
