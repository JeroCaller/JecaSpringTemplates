package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestMemberRequest {
	
	@NotBlank(message = "아이디를 공백으로만 짓는 것은 비허용입니다.")
	@Size(min = 5, max = 20, message = "5자 이상 20자 이하 제한")
	private String username;
	
	@NotBlank(message = "패스워드를 공백으로만 구성하는 것은 비허용입니다.")
	private String password;
	
}
