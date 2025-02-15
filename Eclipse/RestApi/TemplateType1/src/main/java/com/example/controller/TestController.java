package com.example.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.TestService;
import com.example.dto.request.TestProductRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {
	
	private final TestService testService;
	
	@Value("${file.upload-dir}")
	private String uploadBaseDir;
	
	@GetMapping("/upload-path")
	public void uploadPath() {
		// 업로드 될 파일들이 저장될 폴더의 정보를 얻고 그 절대 경로를 얻는다. 
		Path uploadBaseDirPath = Paths.get(uploadBaseDir);
		String fullPath = uploadBaseDirPath.toFile().getAbsolutePath();
				
		// application.properties 파일에 설정한 file.upload-dir의 경로에 "."과 같이 
		// 상대 경로 표기법이 들어가 있는 경우 이를 제거함으로써, 정적 자원들이 들어있는 경로에 접근 가능한 
		// 경로 패턴 문자열을 동적으로 생성한다. 
		// 예) file-upload-dir = ./upload/files 라 되어 있는 경우, 
		// => /upload/files 로 바꾼다. 
		Path normalizedUploadBaseDirPath = Paths.get(uploadBaseDir).normalize();
		String contextPath = "/" + normalizedUploadBaseDirPath.toString().replace("\\", "/");
		
		log.info("contextPath: {}", contextPath);
		log.info(contextPath + "/**");
		log.info("fullPath: {}", fullPath);
	}
	
	@GetMapping("/aop")
	public void getMethodName() {
		
		TestProductRequest request = TestProductRequest.builder()
			.name("테스트")
			.category("테스트")
			.description("테스트입니다.")
			.amount(1)
			.price(1000)
			.build();
		
		testService.aopTest(request, "good");
		
	}
	
}
