package com.example.util;

import java.util.Map;

public class MapUtil {
	
	/**
	 * 주어진 Map 객체가 null이거나 size가 0인지 판별하는 메서드.
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.size() == 0);
	}
	
}
