package com.example.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 * 파일 업로드 작업 성공, 실패한 파일 정보 및 에러 메시지를 저장, 응답하기 위한 DTO 클래스.
 */
@Getter
public class TestFileResultResponse {
	
	/**
	 * 파일 작업에 성공한 파일명을 기록한다. 
	 */
	private List<String> succeededFileNames = new ArrayList<String>();
	
	/**
	 * <파일 경로, 에러원인> 형태로 파일 업로드에 실패한 파일 정보를 기록한다.
	 */
	private Map<String, String> failedPaths = new HashMap<String, String>();

}
