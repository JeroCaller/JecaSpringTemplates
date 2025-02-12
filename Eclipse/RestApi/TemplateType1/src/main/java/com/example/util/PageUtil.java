package com.example.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
	
	/**
	 * zero-based인 PageRequest.of()를 one-based로 바꾼다.
	 * 
	 * 예) 1페이지 조회 시 PageRequest.of(0)이라고 해야 한다. 
	 * 하지만 이 메서드에서는 페이지 번호 1을 그대로 입력하면 1 페이지 조회 가능.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static Pageable toOneBasedPageable(int pageNumber, int pageSize) {
		return PageRequest.of(pageNumber - 1, pageSize);
	}
	
	/**
	 * zero-based인 PageRequest.of()를 one-based로 바꾼다.
	 * 
	 * 예) 1페이지 조회 시 PageRequest.of(0)이라고 해야 한다. 
	 * 하지만 이 메서드에서는 페이지 번호 1을 그대로 입력하면 1 페이지 조회 가능.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	public static Pageable toOneBasedPageable(int pageNumber, int pageSize, Sort sort) {
		return PageRequest.of(pageNumber, pageSize, sort);
	}
	
	/**
	 * 현재 Page의 개수가 0 또는 null인지 판별
	 * 
	 * @param <?>
	 * @param pages
	 * @return
	 */
	public static boolean isEmtpy(Page<?> pages) {
		return (pages == null || pages.getNumberOfElements() == 0);
	}
	
}
