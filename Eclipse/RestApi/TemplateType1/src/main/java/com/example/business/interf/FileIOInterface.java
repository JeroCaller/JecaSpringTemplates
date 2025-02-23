package com.example.business.interf;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드 및 다운로드를 구현하기 위한 인터페이스.
 * 현재 인증된 사용자만을 대상으로 실행. 
 * 
 * @param <RESP> - 응답용 DTO 클래스. 
 * @param <REQ> - 요청용 DTO 클래스.
 * @param <BY> - 조건 검색용 데이터의 타입. 
 */
public interface FileIOInterface<RESP, REQ, BY, RANGE> extends ReadInterface<RESP, BY, RANGE> {
	
	/**
	 * 클라이언트로부터 넘어온 파일과 그 경로를 각각 서버 내 폴더 및 
	 * DB에 저장한다. 
	 * 
	 * @param files - 여러 개의 파일들의 업로드 허용.
	 * @return - 작업 결과 성공 또는 실패, 또는 그 외 결과를 어떻게 표시할지 
	 * 자유롭게 하기 위해 Object로 선언함. 
	 */
	default Object uploadFiles(List<MultipartFile> files) {
		return null;
	}
	
	/**
	 * 클라이언트로부터 파일과 그에 대한 정보를 받아 이를 저장한다. 
	 * 
	 * @param file
	 * @param fileRequest - 파일 정보.
	 * @return
	 */
	default Object uploadFileWithAdditionalInfo(MultipartFile file, REQ fileRequest) {
		return null;
	}
	
	/**
	 * 특정 조건을 만족하는 파일 한 건 다운로드.
	 * 
	 * @param field - 조건 데이터
	 * @return - 파일 다운로드를 위한 반환값
	 */
	Object downloadOneFileBy(BY field);
	
	/**
	 * 특정 조건을 만족하는 파일 하나를 서버와 DB 둘 모두로부터 삭제한다. 
	 * 
	 * @param field - 조건 데이터
	 * @return - 작업 결과를 알릴 반환값
	 */
	Object deleteOneFileBy(BY field);
	
	/**
	 * 현재 인증된 사용자가 보유한 모든 파일 삭제. 
	 * 사용자가 모든 파일들을 삭제하려고 하거나 회원 탈퇴할 때 사용. 
	 * 
	 * 파일을 물리적으로도 삭제하고, DB 내에서도 해당 정보들을 삭제하게끔 구현해야 함.
	 * 
	 * @return - 파일 삭제 작업 후 이를 알릴 반환값. 
	 */
	default Object deleteAllFiles() {
		return null;
	}
	
}
