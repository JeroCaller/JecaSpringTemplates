package com.example.business.interf;

/**
 * Spring Data JPA를 이용하여 CRUD하기 위한 인터페이스.
 * 
 * @param <Service> - 서비스 구현 클래스.
 * @param <RESP> - 응답용 DTO 클래스. 
 * @param <REQ> - 요청용 DTO 클래스. 
 * @param <BY> - 조건 검색용 데이터의 타입.
 * @param <RANGE> - 한 범위 내 여러 레코드들을 조회하기 위한 조건 검색용 데이터의 타입.
 */
public interface CRUDInterface<RESP, REQ, BY, RANGE> extends 
	ReadInterface<RESP, BY, RANGE> {
	
	/**
	 * 레코드 한 건 생성.
	 * 
	 * @param request - 새로운 정보가 담긴 DTO 겍체.
	 * @return - 새 데이터 DB 저장 작업 후 작업 결과를 알릴 반환값.
	 */
	Object create(REQ request);
	
	/**
	 * 레코드 한 건 수정. 
	 * 
	 * @param request - 수정된 정보가 담긴 DTO 겍체.
	 * @return - 수정 작업 후 작업 결과를 알릴 반환값.
	 */
	Object update(REQ request);

	/**
	 * 데이터 한 건 삭제.
	 * 
	 * @param field - 삭제 조건이 될 데이터
	 * @return - 삭제 작업 후 작업 결과를 알릴 반환값.
	 */
	Object deleteOneBy(BY field);
	
	/**
	 * 특정 조건에 해당하는 모든 데이터들을 삭제.
	 * 
	 * @param field - 삭제 조건이 될 데이터.
	 * @return - 삭제 작업 후 작업 결과를 알릴 반환값.
	 */
	default Object deleteSomeBy(RANGE field) {
		return null;
	}
	
	/**
	 * 모든 데이터 삭제.
	 * 
	 * @return - 삭제 작업 후 작업 결과를 알릴 반환값.
	 */
	default Object deleteAll() {
		return null;
	}
	
}
