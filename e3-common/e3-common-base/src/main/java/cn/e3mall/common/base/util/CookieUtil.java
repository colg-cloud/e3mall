package cn.e3mall.common.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Cookie 工具类
 *
 * @author colg
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtil {

    /** 本机地址 */
    private static final String HOST = "127.0.0.1";

    /**
     * 根据cookie名称获取Cookie的值, 不编码
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 根据cookie名称获取Cookie的值
     *
     * @param request
     * @param cookieName
     * @param isDecoder  true: UTF-8编码, false: 不编码
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        if (StringUtils.isBlank(cookieName)) {
            return null;
        }

        Cookie[] cookieList = request.getCookies();
        String retValue = null;
        if (ArrayUtils.isNotEmpty(cookieList)) {
            for (Cookie cookie : cookieList) {
                if (cookieName.equals(cookie.getName())) {
                    if (isDecoder) {
                        try {
                            retValue = URLDecoder.decode(cookie.getValue(), CharsetUtil.UTF_8);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        retValue = cookie.getValue();
                    }
                    break;
                }
            }
        }

        return retValue;
    }

    /**
     * 根据cookie名称和指定编码获取Cookie的值
     *
     * @param request
     * @param cookieName
     * @param encodeString 编码格式
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        if (StringUtils.isBlank(cookieName)) {
            return null;
        }

        Cookie[] cookieList = request.getCookies();
        String retValue = null;
        if (ArrayUtils.isNotEmpty(cookieList)) {
            for (Cookie cookie : cookieList) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        retValue = URLDecoder.decode(cookie.getValue(), encodeString);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,但编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode     true: UTF-8编码, false: 不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 删除Cookie带cookie域名
     *
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效, 指定UTF-8编码
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie生效的最大秒数
     * @param isEncode
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        if (cookieValue == null) {
            cookieValue = "";
        } else {
            try {
                cookieValue = URLEncoder.encode(cookieValue, CharsetUtil.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (cookieMaxage > 0) {
            cookie.setMaxAge(cookieMaxage);
        }
        if (null != request) {
            // 设置域名的cookie
            String domainName = getDomainName(request);
            if (!HOST.equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie 有效时间: 秒
     * @param encodeString
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        if (cookieValue == null) {
            cookieValue = "";
        } else {
            try {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (cookieMaxage > 0) {
            cookie.setMaxAge(cookieMaxage);
        }
        if (null != request) {
            // 设置域名的cookie
            String domainName = getDomainName(request);
            if (!HOST.equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取cookie的域名
     *
     * @param request
     * @return
     */
    private static String getDomainName(HttpServletRequest request) {
        String domainName;

        String serverName = request.getRequestURL()
                                   .toString();
        if (StrUtil.isBlank(serverName)) {
            domainName = "";
        } else {
            // http://
            serverName = serverName.toLowerCase()
                                   .substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            int domainLength = 3;
            if (len > domainLength) {
                // www.xxx.com.cn
                domainName = "." + domains[len - domainLength] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        String colon = ":";
        if (domainName.indexOf(colon) > 0) {
            String[] ary = domainName.split(":");
            domainName = ary[0];
        }
        return domainName;
    }

}
