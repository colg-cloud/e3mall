package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.manager.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户登录处理拦截器
 *
 * @author colg
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Value("${TOKEN_KEY}")
	private String tokenKey;

	@Autowired
	private TokenService tokenService;

	/**
	 * 拦截处理程序的执行。在HandlerMapping之后调用确定了合适的处理程序对象，但在HandlerAdapter调用处理程序之前调用。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*
		 * return true: 放行, false: 拦截
		 * 1. 从cookie中取token
		 * 		没有token:	未登录状态, 直接放行
		 * 		有token:	
		 * 				1). 调用sso系统的服务,根据token取用户信息
		 * 					没有用户信息:	登录过期, 直接放行
		 * 					有用户信息:		登录状态, 把用户信息放到request中, 只需要在controller中判断request中是否包含user信息.放行
		 */
 
		// 从cookie中取token
		String token = CookieUtils.getCookieValue(request, tokenKey);
		// 如果没有token,直接放行
		if (StringUtils.isBlank(token)) {
			return true;
		}

		// 取得token, 调用sso系统服务,根据token取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		if (!e3Result.isSuccess()) {
			// 没有用户信息
			return true;
		}

		// 取得用户信息
		TbUser tbUser = (TbUser) e3Result.getData();
		request.setAttribute("user", tbUser);
		return true;
	}

}
