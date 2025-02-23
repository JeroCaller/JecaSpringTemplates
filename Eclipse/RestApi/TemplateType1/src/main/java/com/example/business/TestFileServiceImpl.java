package com.example.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.advice.annotation.AuthChecker;
import com.example.advice.annotation.ExcludeFromAOP;
import com.example.business.interf.FileIOInterface;
import com.example.data.entity.TestFile;
import com.example.data.entity.TestMember;
import com.example.data.repository.TestFileRepository;
import com.example.dto.request.TestFileRequest;
import com.example.dto.response.TestFileResponse;
import com.example.dto.response.TestFileResultResponse;
import com.example.exception.classes.FileCreationFailedException;
import com.example.exception.classes.FileInfoInDBNotDeletedException;
import com.example.exception.classes.FileNotDeletedException;
import com.example.exception.classes.FileNotFoundOrReadableException;
import com.example.exception.classes.IORuntimeException;
import com.example.exception.classes.TestFileNotFoundException;
import com.example.exception.classes.TestOtherMemberFileAccessException;
import com.example.util.AuthUtil;
import com.example.util.ListUtil;
import com.example.util.PageUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 업로드 및 다운로드 서비스 계층 클래스.
 *  
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Primary
@AuthChecker
public class TestFileServiceImpl 
	implements FileIOInterface<
		TestFileResponse, 
		TestFileRequest, 
		Integer,
		Integer
	> {
	
	private final TestFileRepository testFileRepository;
	
	@Value("${file.upload-dir}")
	private String uploadBaseDir;
	
	private Path uploadBaseDirPath;
	
	/**
	 * 업로드될 모든 파일들이 저장될 루트 디렉토리 초기화.
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	@ExcludeFromAOP
	public void init() throws IOException {
		
		uploadBaseDirPath = Paths.get(uploadBaseDir);
		
		// 업로드된 파일들이 저장될 루트 디렉토리가 존재하지 않으면 새로 생성한다. 
		if (Files.notExists(uploadBaseDirPath)) {
			Files.createDirectories(uploadBaseDirPath);
		}
		
	}
	
	@Override
	public TestFileResponse getOne(Integer field) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		TestFile fileEntity = testFileRepository.findById(field)
			.orElseThrow(() -> new TestFileNotFoundException());
		
		// 다른 회원의 파일 접근 방지.
		if (!fileEntity.getMember().equals(currentMember)) {
			throw new TestOtherMemberFileAccessException();
		}
		
		return TestFileResponse.toDto(fileEntity);
	}
	
	@Override
	public List<TestFileResponse> getAllInList() {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		List<TestFile> files = testFileRepository.findAllByMember(currentMember);
		
		if (ListUtil.isEmpty(files)) {
			throw new TestFileNotFoundException();
		}
		
		return files.stream()
			.map(TestFileResponse :: toDto)
			.collect(Collectors.toList());
	}

	@Override
	public Page<TestFileResponse> getAllInPage(Pageable pageRequest) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		Page<TestFile> files = testFileRepository
			.findAllByMember(currentMember, pageRequest);
		if (PageUtil.isEmtpy(files)) {
			throw new TestFileNotFoundException();
		}
		
		return files.map(TestFileResponse :: toDto);
	}
	
	@Override
	public TestFileResponse uploadFileWithAdditionalInfo(
		MultipartFile file, 
		TestFileRequest fileRequest
	) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		// 파일을 서버에 업로드.
		
		// 파일 내용이 다르나 서버 내에 똑같은 파일명이 존재할 경우를 방지하기 위해 
		// 클라이언트가 전달한 파일명에 부가 정보를 추가한다. 
		// 여기서는 현재 시간이란 정보를 이용한다. 
		String fileName = System.currentTimeMillis() // 현재 시각을 밀리초로 반환.
			+ "_" 
			+ file.getOriginalFilename(); // 멀티파일의 원래 파일명.
					
		// 부모 디렉토리 경로와 방금 생성한 파일명을 합쳐 최종 경로를 생성함.
		Path filePath = uploadBaseDirPath.resolve(fileName); 
		
		try {
			// 파일을 특정 경로로 복사 하기. 
			// 이미 동일 파일 존재 시 덮어쓰기
			// Files.copy() : InputStream으로부터 파일을 읽어 filePath 위치에 
			// 파일을 저장함. 
			// StandardCopyOption.REPLACE_EXISTING : 덮어쓰기 옵션
			Files.copy(
				file.getInputStream(), 
				filePath, 
				StandardCopyOption.REPLACE_EXISTING
			);
		} catch (IOException e) {
			throw new FileCreationFailedException(e.getMessage());
		}
		
		// 파일 정보를 DB에 저장.
		TestFile willBeSavedFile = TestFile.builder()
			.path(filePath.toString())
			.description(fileRequest.getDescription())
			.member(currentMember)
			.build();
		TestFile savedFile = testFileRepository.save(willBeSavedFile);
		
		return TestFileResponse.toDto(savedFile);
	}
	

	@Override
	public Object downloadOneFileBy(Integer field) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		TestFile targetFile = testFileRepository.findById(field)
			.orElseThrow(() -> new TestFileNotFoundException());
		
		// 다른 회원의 파일 다운로드 방지.
		if (!targetFile.getMember().equals(currentMember)) {
			throw new TestOtherMemberFileAccessException();
		}
		
		// 서버 상에 존재하는 파일 경로 추출.
		// normalize() : ".", ".."과 같이 상대 경로 지정에 쓰이는 문자들을 
		// 불필요한 문자로 간주하고 삭제해주는 기능.
		// 예) abc/./test.png -> abc/test.png
		Path filePath = Paths.get(targetFile.getPath()).normalize();
		
		// 파일, 이미지 등의 모든 리소스를 표현하는 객체
		// 기존 문자열 형식의 경로에서, file://~ 프로토콜로 시작하는 URI 형식으로 변경됨.
		Resource resource = null;
		try {
			resource = new UrlResource(filePath.toUri());
		} catch (MalformedURLException e) {
			throw new IORuntimeException("Path -> Resource로 변경 중 URI가 잘못 생성되었습니다.");
		}
				
		if (!resource.exists() || !resource.isReadable()) {
			String exceptionPath = null;
			try {
				exceptionPath = resource.getURL().getPath();
				throw new FileNotFoundOrReadableException(
					"해당 파일이 존재하지 않거나 열 수 없습니다." + exceptionPath
				);
			} catch (IOException e) {
				throw new IORuntimeException("resource 객체로부터 URL 문자열로 변환 중 예외 발생.");
			}
		}
		
		// 파일의 MIME-TYPE 정보 파악 후 타입 결정 (이미지인지 동영상인지 등을 판별)
		String contentType = null;
		try {
			contentType = Files.probeContentType(filePath);
		} catch (IOException e) {
			contentType = "application/octet-stream";
		}
				
		// 만약 MIME-TYPE을 읽어오지 못한다면 다운로드 작업 수행.
		if (contentType == null) {
			// 해당 타입은 브라우저가 해석하지 못하는 타입이다. 
			// 브라우저가 해석하지 못하는 컨텐트 타입은 무조건 다운로드를 실행하는 
			// 특성이 있으니 이를 이용하는 것이다. 
			contentType = "application/octet-stream";
		}
				
		// 파일의 원래 이름 가져오기
		String originalFileName = null;
		try {
			// 파일명에 한글이 포함된 경우 다운로드 도중 예외가 발생하므로 이를 utf-8로 인코딩한다. 
			originalFileName = URLEncoder.encode(filePath.getFileName().toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("잘못된 인코딩 방식입니다.");
			originalFileName = filePath.getFileName().toString();
		}
				
		// CONTENT_DISPOSITION : 해당 파일이 브라우저에서 열리지 않도록 함.
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + originalFileName + "\"")
			.body(resource);
	}

	@Override
	public Object deleteOneFileBy(Integer field) {
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		
		TestFile targetFile = testFileRepository.findById(field)
			.orElseThrow(() -> new TestFileNotFoundException());
		
		// 다른 회원 파일 삭제 방지.
		if (!targetFile.getMember().equals(currentMember)) {
			throw new TestOtherMemberFileAccessException();
		}
		
		// 파일의 물리적 삭제 작업 시도.
		Path targetPath = Paths.get(targetFile.getPath());
		try {
			Files.deleteIfExists(targetPath);
		} catch (IOException e) {
			throw new IORuntimeException("IO 관련 예외 발생. " + e.getMessage());
		}
				
		log.info("정말 파일이 물리적으로 삭제되었는가? " + Files.notExists(targetPath));
		if (Files.exists(targetPath)) {
			throw new FileNotDeletedException(targetPath.toString());
		}
		
		// 파일을 물리적으로 삭제 성공 후, 해당 파일 정보를 DB 상에서 삭제.
		try {
			testFileRepository.delete(targetFile);
		} catch (Exception e) {
			throw new FileInfoInDBNotDeletedException(e.getMessage());
		}
		
		// 아무것도 반환하지 않는다. 
		return null;
	}

	@Override
	public Object deleteAllFiles() {
		
		TestFileResultResponse fileResult = new TestFileResultResponse();
		
		TestMember currentMember = (TestMember) AuthUtil.getCurrentUserInfo();
		List<TestFile> files = testFileRepository.findAllByMember(currentMember);
		
		for (TestFile file : files) {
			Path filePath = Paths.get(file.getPath());
			
			// 먼저 서버 내에 물리적으로 존재하는 파일들을 모두 삭제한다.
			try {
				Files.deleteIfExists(filePath);
			} catch (IOException e) {
				// 다른 파일들의 삭제를 위해 
				// 예외가 발생해도 다음 파일로 넘어가도록 함.
				// 대신 실패한 파일 경로 및 원인을 기록.
				fileResult.getFailedPaths().put(
					filePath.toString(), 
					"물리적 파일 삭제 실패. \n" + e.getMessage()
				);
				continue;
			}
			
			// DB 내 해당 파일 정보 삭제
			try {
				testFileRepository.delete(file);
			} catch (Exception e) {
				fileResult.getFailedPaths().put(
					filePath.toString(), 
					"DB 삭제 실패.\n" + e.getMessage()
				);
				continue;
			}
			
			fileResult.getSucceededFileNames().add(filePath.toString());
		}
		
		return fileResult;
	}

}
