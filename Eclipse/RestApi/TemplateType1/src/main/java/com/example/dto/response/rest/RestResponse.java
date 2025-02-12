package com.example.dto.response.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.example.dto.response.TestFileResultResponse;
import com.example.util.ListUtil;
import com.example.util.MapUtil;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 클라이언트에 전송할 JSON 형태 응답 데이터와 매핑될 DTO 클래스.
 * 
 * <pre>
 * 사용 예시)
 * <code>
 * RestResponse.builder()
 *  .responseCode(...)
 *  .uri(...)
 *  .data(...)
 *  .build()
 *  .toResponse();
 * </code>
 * </pre>
 *  
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestResponse {
	
	@Builder.Default
	private CustomResponseCode responseCode = CustomResponseCode.OK;
	
	private String uri;
	
	/**
	 * 유효성 검사 실패 시 실패 원인 메시지를 담을 필드. 
	 * 유효성 검사와 관련 없을 시 해당 필드에 값을 담지 않는다. 
	 */
	private Map<String, String> validFailedMsg;
	
	/**
	 * 파일 업로드 작업 시에만 필요. 
	 * 파일 업로드 작업 후의 파일들의 정보. 
	 * 파일 업로드와 관련 없을 시 해당 필드에 값을 담지 않는다.
	 */
	private TestFileResultResponse fileResult;
	
	private Object data; 
	
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonFilter("restResponseFilter") // 응답 데이터에 유효성 검사 실패 메시지 필드 필터링을 위함.
	public static class DataResponse {
		
		private String code;
		private String message;
		private String uri;
		private Map<String, String> validFailedMsg;
		private TestFileResultResponse fileResult;
		private Object data;
		
	}
	
	/**
	 * 클라이언트에 전송할 HTTP 응답 데이터를 반환한다. 
	 * 
	 * @return
	 */
	public ResponseEntity<Object> toResponse() {
		
		DataResponse response = DataResponse.builder()
			.code(this.getResponseCode().getCode())
			.message(this.getResponseCode().getMessage())
			.uri(this.getUri())
			.validFailedMsg(this.getValidFailedMsg())
			.fileResult(this.getFileResult())
			.data(this.getData())
			.build();
		
		MappingJacksonValue mapped = getFilteredData(response);
		
		return ResponseEntity.status(this.getResponseCode().getHttpStatus())
				.body(mapped);
	}
	
	/**
	 * 유효성 검사 실패 원인 메시지를 담는 validFailedMsg 필드에 아무런 값도 초기화되지 않은 경우 
	 * 해당 필드만 JSON 응답 데이터에서 제외하고, 값이 있다면 해당 필드를 포함. 
	 * 모든 필드들을 JSON 응답 데이터에 포함시키는 필터링 메서드. 
	 * 즉, 특정 필드에 값이 할당되지 않은 경우 해당 필드들을 응답 데이터에서 제외하도록 
	 * 필터링하는 메서드.
	 * 
	 * @param response
	 * @return - JSON 응답 데이터로 반환 준비된 필터링된 데이터.
	 */
	private MappingJacksonValue getFilteredData(DataResponse response) {
		
		SimpleBeanPropertyFilter filter = getFilter("validFailedMsg", "fileResult");
		
		// 앞서 정의한 필터를 등록.
		FilterProvider filterProvider = new SimpleFilterProvider()
			.addFilter("restResponseFilter", filter);
				
		MappingJacksonValue mapping = new MappingJacksonValue(response);
		mapping.setFilters(filterProvider); // 응답 데이터에 필터 등록.
		
		// 필터링 된 데이터 반환.
		return mapping;
	}
	
	/**
	 * JSON 응답 데이터 제외 여부 대상에 해당되는 필드들의 이름을 문자열로 입력한다. 
	 * 각각의 필드에 할당된 값이 없거나 사이즈가 0일 경우, JSON 응답 데이터에서 제외시킨다. 
	 * 
	 * @param exceptFieldNames
	 * @return
	 */
	private SimpleBeanPropertyFilter getFilter(String... exceptFieldNames) {
		
		SimpleBeanPropertyFilter filter = null;
		
		// 주어진 필드에 값이 없을 경우에만 응답 데이터 제외 대상에 포함.
		List<String> fieldNamesList = new ArrayList<String>();
		
		for (String fieldName : exceptFieldNames) {
			// 문자열로 넘겨진 필드명으로 실제 해당 필드의 값을 얻어온다.
			Field field = null;
			Object fieldValue = null;
			try {
				field = DataResponse.class.getDeclaredField(fieldName);
				field.setAccessible(true);  // private 필드에도 접근 허용.
				fieldValue = field.get(fieldNamesList); // 필드에 할당된 값 가져오기.
			} catch (NoSuchFieldException | SecurityException | 
					IllegalArgumentException | IllegalAccessException e) {
				// 해당 필드가 없다고 간주하고 작업을 건너뛴다.
				continue;
			}
			
			// 가져온 필드에 값이 없으면 JSON 응답 데이터 제외 대상 목록에 등록.
			if (fieldValue == null) {
				fieldNamesList.add(fieldName);
			} else if (fieldValue instanceof Map<?, ?>) {
				Map<?, ?> fieldMap = (Map<?, ?>) fieldValue;
				if (MapUtil.isEmpty(fieldMap)) {
					fieldNamesList.add(fieldName);
				}
			} else if (fieldValue instanceof List<?>) {
				List<?> fieldList = (List<?>) fieldValue;
				if (ListUtil.isEmpty(fieldList)) {
					fieldNamesList.add(fieldName);
				}
			}
		}
		
		String[] fieldNames = (String[]) fieldNamesList.toArray();
		filter = SimpleBeanPropertyFilter.serializeAllExcept(fieldNames);
		
		return filter;
	}
}
