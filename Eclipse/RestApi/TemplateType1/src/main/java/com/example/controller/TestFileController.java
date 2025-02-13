package com.example.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.business.TestFileServiceImpl;
import com.example.business.interf.FileIOInterface;
import com.example.dto.request.TestFileRequest;
import com.example.dto.response.TestFileResponse;
import com.example.dto.response.TestFileResultResponse;
import com.example.dto.response.rest.CustomResponseCode;
import com.example.dto.response.rest.RestResponse;
import com.example.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/files/my")
public class TestFileController {
	
	private final HttpServletRequest httpRequest;
	private final FileIOInterface<
		TestFileServiceImpl, 
		TestFileResponse,
		TestFileRequest, 
		Integer, Integer
	> fileService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneFile(
		@PathVariable("id") int fileId
	) {
		
		TestFileResponse fileResponse = fileService.getOne(fileId);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(fileResponse)
			.build()
			.toResponse();
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllFiles(
		@RequestParam("startPage") int startPage, 
		@RequestParam("size") int size
	) {
		
		Pageable pageRequest = PageUtil.toOneBasedPageable(startPage, size);
		Page<TestFileResponse> pagedFiles 
			= fileService.getAllInPage(pageRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.READ_SUCCESS)
			.uri(httpRequest.getRequestURI())
			.data(pagedFiles)
			.build()
			.toResponse();
	}
	
	/**
	 * 
	 * <div>
	 * 참고) <br/>
	 * 파일과 나머지 JSON 데이터들을 함께 매핑하여 업로드하려면 
	 * MultipartFile 파라미터와 dto 파라미터 둘 다 <code>@RequestPart</code> 
	 * 어노테이션을 사용해야한다고 한다. 
	 * </div><br/>
	 * 
	 * 참고 사이트)<br/>
	 * <ul>
	 * <li>https://leeggmin.tistory.com/7</li>
	 * </ul>
	 * 
	 * @param file
	 * @param fileRequest
	 * @return
	 */
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Object> uploadFiles(
		@RequestPart(name = "file") MultipartFile file,
		@RequestPart(name = "info", required = false) TestFileRequest fileRequest
	) {
		
		TestFileResponse fileResponse = (TestFileResponse) fileService
			.uploadFileWithAdditionalInfo(file, fileRequest);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.FILE_CREATED)
			.uri(httpRequest.getRequestURI())
			.data(fileResponse)
			.build()
			.toResponse();
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadOneFile(@PathVariable("id") int fileId) {
		return (ResponseEntity<Resource>) fileService.downloadOneFileBy(fileId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOneFile(@PathVariable("id") int fileId) {
		
		Object result = fileService.deleteOneFileBy(fileId);
		
		return RestResponse.builder()
			.responseCode(CustomResponseCode.FILE_DELETED)
			.uri(httpRequest.getRequestURI())
			.data(result)
			.build()
			.toResponse();
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteAllFiles() {
		
		TestFileResultResponse fileResult = (TestFileResultResponse) fileService
			.deleteAllFiles();
		
		CustomResponseCode code = null;
		
		switch(fileResult.getFileResult()) {
			case ALL_SUCCESS:
				code = CustomResponseCode.FILE_DELETED;
				break;
			case PARTIAL_SUCCESS:
				code = CustomResponseCode.ONLY_SOME_FILE_DELETED;
				break;
			case ALL_FAILED:
			case NO_RESULT:
				code = CustomResponseCode.FILE_NOT_DELETED;
				break;
		}
		
		return RestResponse.builder()
			.responseCode(code)
			.uri(httpRequest.getRequestURI())
			.fileResult(fileResult)
			.build()
			.toResponse();
	}
	
}
