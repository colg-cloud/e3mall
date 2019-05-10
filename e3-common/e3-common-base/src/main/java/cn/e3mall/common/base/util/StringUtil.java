package cn.e3mall.common.base.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cn.hutool.core.util.CharsetUtil;
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
     * @param str ISO8859-1 String
     * @return UTF-8 String
     */
    public static String iso2Utf(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

}
