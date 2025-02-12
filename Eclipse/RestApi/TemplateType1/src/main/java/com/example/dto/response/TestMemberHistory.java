package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 회원 정보 수정 시 바뀐 정보를 응답으로 전송한다. 
 * 
 * 여기서는 닉네임에 대해서만 변경 사항을 전송.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class TestMemberHistory extends BaseResponse {
	
	private Integer id;
	private String pastUsername;
	private String newUsername;
	
}
