package com.example.dto.response;

import com.example.data.entity.TestMember;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, exclude = "id")
public class TestMemberResponse extends BaseResponse {
	
	private int id;
	private String username;
	
	@JsonIgnore
	private String password;
	
	public static TestMemberResponse toDto(TestMember entity) {
		
		return TestMemberResponse.builder()
			.id(entity.getId())
			.username(entity.getUsername())
			.password(entity.getPassword())
			.createdAt(entity.getCreatedAt())
			.updatedAt(entity.getUpdatedAt())
			.build();
	}
	
}
