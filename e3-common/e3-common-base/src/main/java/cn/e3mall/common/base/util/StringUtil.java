package cn.e3mall.common.base.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author colg
 */
public class StringUtil {

	/**
	 * ISO8859-1 转换为 UTF-8 编码，防止GET请求乱码
	 * 
	 * @param str
	 * @return
	 */
	public static String iso2Utf(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
}
