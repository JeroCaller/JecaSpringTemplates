package com.example.dto.response.rest;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponseCode {
	
	// 응답 코드 모음. 원하는 대로 커스텀 가능.
	
	// 응답 상태 양호 코드 모음.
	OK(HttpStatus.OK, "OK", "응답 성공"),
	MEMBER_CREATED(HttpStatus.CREATED, "MEMBER_CREATED", "회원 가입 성공"),
	FILE_CREATED(HttpStatus.CREATED, "FILE_CREATED", "파일 생성 성공"),
	
	// 에러 코드 모음.
	MEMBER_NOT_FOUND(
		HttpStatus.NOT_FOUND, 
		"MEMBER_NOT_FOUND", 
		"조회된 회원이 없습니다."
	),
	MEMBER_ALREADY_EXISTS(
		HttpStatus.BAD_REQUEST, 
		"MEMBER_ALREADY_EXISTS", 
		"이미 해당 회원이 존재합니다."
	),
	OTHER_MEMBER_FILE_ACCESS_DENIED(
		HttpStatus.UNAUTHORIZED,
		"OTHER_MEMBER_FILE_ACCESS_DENIED",
		"다른 유저의 파일 접근 권한이 없습니다."
	),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE_NOT_FOUND", "조회된 파일이 없습니다."),
	FILE_NOT_DELETED(
		HttpStatus.INTERNAL_SERVER_ERROR, 
		"FILE_NOT_DELETED", 
		"파일 삭제 실패."
	),
	FILE_INFO_NOT_DELETED(
		HttpStatus.INTERNAL_SERVER_ERROR, 
		"FILE_INFO_NOT_DELETED",
		"DB 내 파일 정보 삭제 실패."
	),
	NOT_AUTHENTICATED(
		HttpStatus.UNAUTHORIZED, 
		"NOT_AUTHENTICATED", 
		"미인증된 사용자입니다."
	),
	VALIDATION_FAILED(
		HttpStatus.BAD_REQUEST, 
		"VALIDATION_FAILED", 
		"입력된 데이터가 유효하지 않습니다."
	), 
	
	// 서버 내 에러 시 코드
	AOP_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR, 
		"AOP_ERROR", 
		"서버 내 AOP에서 문제 발생. 자세한 사항은 serverErrorMsg 프로퍼티 참조."
	),
	INTERNAL_SERVER_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR, 
		"INTERNAL_SERVER_ERROR", 
		"서버 내 예상 범위 내 에러 발생. 자세한 사항은 서버 로그 기록 확인 요망."
	),
	UNKNOWN_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR, 
		"UNKNOWN_ERROR",
		"서버 내 미상의 에러 발생."
	);
	
	private HttpStatus httpStatus;
	private String code;
	private String message;
	
}
