package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.ListUtil;
import cn.e3mall.manager.pojo.TbUser;
import cn.e3mall.sso.core.BaseServiceImpl;
import cn.e3mall.sso.service.TbUserService;

/**
 * 
 *
 * @author colg
 */
@Service
public class TbUserServiceImpl extends BaseServiceImpl implements TbUserService {

	@Value("${TOKEN_KEY}")
	private String tokenKey;
	@Value("${TOKEN_EXPIRE}")
	private int tokenExpire;

	@Override
	public TbUser findById(Long id) {
		return tbUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public E3Result checkData(String param, Integer type) {
		// 参数类型 - 1:用户名, 2:手机, 3:邮箱
		boolean existed = (type == null || (type != 1 && type != 2 && type != 3));
		if (existed) {
			return E3Result.fail(400, "非法的参数");
		}
		// 执行查询
		List<TbUser> list = tbUserMapper.selectByParam(param, type);
		// 判断结果中是否包含数据
		if (ListUtil.isNotEmpty(list)) {
			// 如果有数据返回false
			return E3Result.ok(false);
		}

		// 如果没有数据返回true
		return E3Result.ok(true);
	}

	@Override
	public E3Result createUser(TbUser tbUser) {
		// 数据有效性校验
		if (StringUtils.isBlank(tbUser.getUsername())) {
			return E3Result.fail(400, "用户名不能为空");
		}
		if (StringUtils.isBlank(tbUser.getPassword())) {
			return E3Result.fail(400, "密码不能为空");
		}
		if (StringUtils.isBlank(tbUser.getPhone())) {
			return E3Result.fail(400, "手机号不能为空");
		}

		// 数据库校验
		int userId = 1;
		if (!(boolean) this.checkData(tbUser.getUsername(), userId).getData()) {
			return E3Result.fail(400, "此用户名已经被注册！请重新输入。");
		}
		int phone = 2;
		if (!(boolean) this.checkData(tbUser.getPhone(), phone).getData()) {
			return E3Result.fail(400, "此手机号已经被注册！");
		}

		// 补全pojo属性
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());

		// 对密码进行md5加密
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));

		// 把用户数据插入到数据库中
		tbUserMapper.insert(tbUser);

		// 返回添加成功
		return E3Result.ok();
	}

	@Override
	public E3Result userLogin(String username, String password) {
		/*
		 * 参数: 用户名, 密码
		 * 
		 * 业务逻辑:
		 * 	1. 判断用户和密码是否正确
		 * 		不正确: 返回登录失败
		 * 		正确:
		 * 			1. 生成token
		 * 			2. 把用户信息写入redis, key: token, value: 用户信息
		 * 			3. 设置key(token)的过期时间
		 * 			4. 返回token
		 *  2. 返回值: E3Result, 其中包含token信息
		 */

		// 校验数据
		if (StringUtils.isBlank(username)) {
			return E3Result.fail(400, "请输入用户名");
		}
		if (StringUtils.isBlank(password)) {
			return E3Result.fail(400, "请输入密码");
		}

		TbUser tbUser = tbUserMapper.findByUserNameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
		if (tbUser == null) {
			// 返回登录失败
			return E3Result.fail(400, "用户名或密码错误");
		}

		// 生成token
		// 密码不要存redis
		tbUser.setPassword(null);
		String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String key = tokenKey + ":" + token;
		jedisClient.set(key, JSON.toJSONString(tbUser));
		jedisClient.expire(key, tokenExpire);
		return E3Result.ok(token);
	}

}
