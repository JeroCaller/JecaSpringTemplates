package com.example.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.upload-dir}")
	private String uploadBaseDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
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
		
		// registry.addResourceHandler("/files/**") // 이것과 동일
		registry.addResourceHandler(contextPath + "/**")
			.addResourceLocations("file:" + fullPath + "/");
		
	}

}
