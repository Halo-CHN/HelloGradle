package com.chn.halo.util;

/**
 *
 * @description String扩展类
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年6月24日
 *
 * @version 1.0
 *
 */
public class StringEx {
	public static CharSequence checkNull(String str) {
		return null != str ? String.valueOf(str) : "";
	}

	@SuppressWarnings("finally")
	public static String getUtf8String(byte[] bytes) {
		String result = "";
		try {
			result = new String(bytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
}
