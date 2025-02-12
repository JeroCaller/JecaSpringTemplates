package com.example.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // 이 클래스를 상속받는 자식 클래스에서도 
// 이 클래스 내 필드들까지 builder에 포함시키기 위함.
public class BaseResponse {

	private LocalDate createdAt;
	private LocalDate updatedAt;
	
}
