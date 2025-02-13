package com.example.config;

import java.lang.reflect.Type;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <div>
 * <p>설명)</p>
 * <p>Swagger에서 파일 업로드 시도 시 다음의 에러가 발생한다.</p>
 * 
 * <p>
 * 에러 메시지)<br/>
 * [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/octet-stream' is not supported]
 * </p>
 * 
 * MIME 타입이 multipart/form-data인 타입의 파일과 파일의 세부 정보를
 * JSON으로 받기 위한 application/json 타입의 DTO가 동시에 요청 들어오고, 
 * <code> @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) </code>
 * 으로 설정하면, 파일은 이미 multipart/form-data 타입이기에 이를 잘 받아오지만, 
 * application/json 타입의 DTO는 consumes로 설정한 multipart/form-data와 맞지 않기에 
 * 스프링에서는 이를 application/octet-stream 타입으로 해석하는 것으로 추측된다. 
 * 해탕 MIME 타입은 스프링에서 해당 데이터를 어떤 타입으로 해석해야할지 모를 때 자동으로 설정되는 타입이라고 한다. 
 * 문제는 application/octet-stream 타입을 처리할 스프링 내 messageConverter가 기본적으로 제공되지 않는다고 한다. 
 * 따라서 이 타입을 해석할 messageConverter를 이 클래스로 정의한다. 
 * 
 * </div>
 * <div>
 * <p>참고 사이트)</p>
 * <ul>
 * <li>https://one-armed-boy.tistory.com/entry/Swagger-RequestPart%EB%A5%BC-%ED%86%B5%ED%95%B4-%ED%8C%8C%EC%9D%BC-Dto-%EB%8F%99%EC%8B%9C-%EC%9A%94%EC%B2%AD-%EC%8B%9C-%EB%B0%9C%EC%83%9D-%EC%97%90%EB%9F%AC</li>
 * <li>https://eggrok1.tistory.com/entry/springboot-orgspringframeworkwebHttpMediaTypeNotSupportedException-Content-type-applicationoctet-stream-not-supported</li>
 * </ul>
 * </div>
 */
@Component
public class OctetMessageConverter extends AbstractJackson2HttpMessageConverter {

	public OctetMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
	}
	
	/**
	 * application/octet-stream 타입에 대해 쓰기 모드로 다루는 메시지 컨버터가 
	 * 이미 ByteArrayHttpMessageConverter로 존재한다고 한다. 
	 * 따라서 여기서 만들 컨버터에서는 쓰기 작업을 정의해선 안되므로 false를 반환하도록 함.
	 */
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	protected boolean canWrite(MediaType mediaType) {
		return false;
	}
	
}
