package com.example.business.interf;

/**
 * 유저 CRUD 인터페이스
 * 회원가입, 회원 탈퇴 등 유저(회원)와 관련된 작업을 위한 인터페이스
 * 
 * 모두 현재 인증된 사용자를 기준으로 한다. 
 * 
 * 현재 사용자의 인증 여부 사전 검증은 advice를 이용하는 것을 권고.
 * 
 * @param <Service> - 이 인터페이스를 구현하는 클래스명
 * @param <RESP> - 회원 정보 응답 DTO 클래스
 * @param <REQ> - 회원 정보 요청 DTO 클래스
 */
public interface UserCRUDInterface<RESP, REQ> {
	
	/**
	 * 현재 인증된 사용자 정보 반환.
	 * 
	 * @return
	 */
	RESP getCurrentUser();
	
	/**
	 * 회원 가입 요청 정보를 토대로 회원 가입.
	 * 
	 * @param registerRequest
	 * @return - 회원 가입 성공한 회원 정보 반환.
	 */
	RESP register(REQ registerRequest);
	
	/**
	 * 현재 인증된 사용자의 회원 정보 수정.
	 * 
	 * advice를 이용하여 세션 내 회원 정보 수정 권고.
	 * 
	 * @param memberRequest
	 * @return
	 */
	Object update(REQ memberRequest);
	
	/**
	 * 현재 인증된 사용자의 회원 정보 탈퇴.
	 * 
	 * advice를 이용하여 세션 내 회원 정보 삭제 권고.
	 * 
	 * @return
	 */
	RESP unregister();
	
	/**
	 * 주어진 유저 이름을 가지는 회원 정보가 있는지 확인.
	 * 
	 * @param username
	 * @return
	 */
	default boolean exists(String username) {
		return false;
	}
	
}
