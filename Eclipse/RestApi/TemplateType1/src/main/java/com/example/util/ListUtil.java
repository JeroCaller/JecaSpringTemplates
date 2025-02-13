package com.example.util;

import java.util.List;

public class ListUtil {
	
	/**
	 * 주어진 List 객체가 null이거나 size가 0인지 판별하는 메서드.
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		return (list == null || list.size() == 0);
	}
	
	/**
	 * 리스트 내 모든 요소들의 toString()을 호출하여 
	 * 하나의 문자열로 합쳐 반환한다. 
	 * 
	 * @param <T>
	 * @param delemeter 각 문자들을 구분할 구분자. null값 입력 시 ", "로 설정됨.
	 * @param list
	 * @return
	 */
	public static <T> String toOneString(List<T> list, String delemeter) {
		
		if (delemeter == null) {
			delemeter = ", ";
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (T element : list) {
			stringBuilder.append(element.toString());
			stringBuilder.append(delemeter);
		}
		
		return stringBuilder.toString();
	}

}
