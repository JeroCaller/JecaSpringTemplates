package com.example.dto.response;

import com.example.data.entity.TestFile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder  // 부모 클래스 내 필드까지 builder에 포함.
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, exclude = "id")
public class TestFileResponse extends BaseResponse {
	
	private int id;
	private String path;
	private String description;
	
	public static TestFileResponse toDto(TestFile entity) {
		return TestFileResponse.builder()
			.id(entity.getId())
			.path(entity.getPath())
			.description(entity.getDescription())
			.createdAt(entity.getCreatedAt())
			.updatedAt(entity.getUpdatedAt())
			.build();
	}
	
}
