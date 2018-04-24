package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.manager.pojo.TbUser;
import cn.e3mall.sso.core.BaseServiceImpl;
import cn.e3mall.sso.service.TokenService;

/**
 * 
 *
 * @author colg
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl implements TokenService {

	@Value("${TOKEN_KEY}")
	private String tokenKey;
	@Value("${TOKEN_EXPIRE}")
	private int tokenExpire;

	@Override
	public E3Result getUserByToken(String token) {
		// 根据token到redis中获取用户信息
		String key = tokenKey + ":" + token;
		String jsonString = jedisClient.get(key);
		if (StringUtils.isBlank(jsonString)) {
			// 取不到用户信息,登录已经过期,返回登录过期
			return E3Result.fail(400, "用户登录已经过期，请重新登录。");
		}

		// 取到用户信息,更新token的过期时间
		jedisClient.expire(key, tokenExpire);

		// 返回结果,E3Result包含TbUser对象
		return E3Result.ok(JSON.parseObject(jsonString, TbUser.class));
	}

}
