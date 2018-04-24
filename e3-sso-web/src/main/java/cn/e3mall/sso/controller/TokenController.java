package cn.e3mall.sso.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.sso.core.BaseController;

/**
 * 用户token管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/token")
public class TokenController extends BaseController {

	/**
	 * 根据token获取user信息
	 * 
	 * 由于使用了fastjson框架解析,直接返回String,会自动拼上双引号,js认不出此对象,所以 直接用response回显回去,绕过fastjson
	 * 
	 * "test123({\"data\":{\"created\":1521914815000,\"id\":7,\"phone\":\"12344444443\",\"updated\":1521914815000,\"username\":\"Jack\"},\"msg\":\"OK\",\"status\":200,\"success\":true});"
	 * 
	 * 此格式不行
	 * 
	 * @param token
	 * @param callback
	 *            jsonp 跨域请求
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/user2/{token}")
	public void getUserByToken2(@PathVariable String token, String callback, HttpServletResponse response) {
		E3Result e3Result = tokenService.getUserByToken(token);
		PrintWriter out = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			out = response.getWriter();
			// 响应结果之前,判断是否为jsonp请求, jsonp会自动带 callback
			if (StringUtils.isNotBlank(callback)) {
				// 把结果封装成一个js语句响应
				String jsonString = callback + "(" + JSON.toJSONString(e3Result) + ");";
				out.write(jsonString);
			} else {
				out.write(JSON.toJSONString(e3Result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 根据token获取user信息
	 * 
	 * 使用 fastjson JSONPObject对象处理
	 * 
	 * @param token
	 * @param callback
	 *            跨域请求
	 * @return
	 */
	@GetMapping(value = "/user/{token}")
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);

		// 响应结果之前,判断是否为jsonp请求, jsonp会自动带 callback
		if (StringUtils.isNotBlank(callback)) {
			// 创建jsonp对象
			JSONPObject jsonpObject = new JSONPObject(callback);
			jsonpObject.addParameter(e3Result);
			return jsonpObject;
		}

		return e3Result;
	}

}
