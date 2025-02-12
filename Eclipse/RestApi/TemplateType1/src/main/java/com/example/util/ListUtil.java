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

}
