package com.example.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 현재 인증된 본인 사용자에게만 파일 요청 가능하다고 가정.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestFileRequest {
	
	private MultipartFile mediaFile;
	private String description;
	
}
