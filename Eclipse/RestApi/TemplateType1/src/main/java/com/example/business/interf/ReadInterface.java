package com.example.business.interf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CRUD 작업 중 R을 담당하는 인터페이스.
 * 
 * @param <RESP> - 응답용 DTO 클래스.
 * @param <BY> - 조건 검색 데이터의 타입.
 */
public interface ReadInterface<RESP, BY> {
	
	/**
	 * 레코드 한 건 조회.
	 * 
	 * @param field - 조회 기준이 될 데이터
	 * @return
	 */
	default RESP getOne(BY field) {
		return null;
	}
	
	/**
	 * 레코드 전체 조회.
	 * 
	 * @return - 조회된 데이터들을 List 형태로 반환.
	 */
	default List<RESP> getAllInList() { 
		return null;
	}
	
	/**
	 * 레코드 전체 범위 내에서 조회. 페이징을 통해 일부만 조회. 
	 * 
	 * @param pageRequest
	 * @return
	 */
	default Page<RESP> getAllInPage(Pageable pageRequest) {
		return null;
	}
	
	/**
	 * 특정 조건을 만족하는 데이터들을 페이징하여 반환. 
	 * 
	 * @param field - 조회하고자 하는 조건 데이터
	 * @param pageRequest
	 * @return
	 */
	default Page<RESP> getSomeInPageBy(BY field, Pageable pageRequest) {
		return null;
	}
	
	/**
	 * 특정 조건을 만족하는 모든 데이터들을 리스트 형태로 반환.
	 * 
	 * @param field - 조회하고자 하는 조건 데이터.
	 * @return
	 */
	default List<RESP> getSomeInListBy(BY field) {
		return null;
	}
	
	/**
	 * 조건에 맞는 데이터가 DB에 존재하는지 여부 반환.
	 * 
	 * @param field
	 * @return
	 */
	default boolean exists(BY field) {
		return false;
	}

}
