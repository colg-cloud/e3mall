package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.manager.pojo.TbUser;
import cn.e3mall.sso.core.BaseController;

/**
 * 用户Controller
 *
 * @author colg
 */
@RestController
@RequestMapping("/tb/user")
public class TbUserController extends BaseController {

	/** cookie中保存token的key */
	@Value("${TOKEN_KEY}")
	private String tokenKey;

	@GetMapping("/{id}")
	public E3Result find(@PathVariable Long id) {
		TbUser tbUser = tbUserService.findById(id);
		return E3Result.ok(tbUser);
	}

	/**
	 * 注册,校验
	 * 
	 * @param param
	 * @param type
	 *            参数类型 - 1:用户名, 2:手机, 3:邮箱
	 * @return
	 */
	@GetMapping("/check/{param}/{type}")
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		return tbUserService.checkData(param, type);
	}

	/**
	 * 注册,添加
	 * 
	 * @param tbUser
	 * @return
	 */
	@PostMapping("/register")
	public E3Result register(TbUser tbUser) {
		return tbUserService.createUser(tbUser);
	}

	/**
	 * 登录
	 * 
	 * 登录成功后，把token写入cookie
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		E3Result e3Result = tbUserService.userLogin(username, password);
		// 判断是否登录成功
		if (e3Result.isSuccess()) {
			String token = e3Result.getData().toString();
			// 如果登录成功需要把token写入cookie
			CookieUtils.setCookie(request, response, tokenKey, token);
		}
		return e3Result;
	}

}
