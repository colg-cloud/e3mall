package cn.e3mall.sso.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.e3mall.common.base.entity.User;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.sso.service.UserService;
import cn.e3mall.sso.service.core.BaseServiceImpl;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 
 *
 * @author colg
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    
    /** token 在 redis 中缓存的key */
    @Value("${token_key}")
    private String tokenKey;
    /** key 的过期时间 */
    @Value("${expire_seconds}")
    private int expireSeconds;

    @Override
    public E3Result checkData(String param, Integer type) {
        // 参数类型 - 1: 用户名; 2: 手机; 3: 邮箱
        if (type == null || (type != 1 && type != 2 && type != 3)) {
            return E3Result.fail("非法的参数类型!");
        }
        // 根据参数和参数类型查询用户
        return E3Result.ok(userMapper.findByParam(param, type));
    }

    @Override
    public E3Result addUser(User user) {
        // 数据有效性校验
        E3Result e3Result = this.checkAddUser(user);
        if (!e3Result.getStatus().equals(E3Result.OK)) {
            return e3Result;
        }
        Date now = new Date();
        user.setCreated(now)
            .setUpdated(now)
            .setPassword(SecureUtil.md5(user.getPassword()));
        userMapper.insertSelective(user);
        return E3Result.ok();
    }

    /**
     * 注册, 添加用户 - 数据有效性校验
     *
     * @param user
     * @return
     */
    private E3Result checkAddUser(User user) {
        String username = user.getUsername();
        if (StrUtil.isBlank(username)) {
            return E3Result.fail("用户名不能为空!");
        }
        String phone = user.getPhone();
        if (StrUtil.isBlank(phone)) {
            return E3Result.fail("手机号不能为空!");
        }
        if (StrUtil.isBlank(user.getPassword())) {
            return E3Result.fail("密码不能为空");
        }
        // 参数类型 - 1: 用户名; 2: 手机; 3: 邮箱
        if (this.checkData(username, 1).getData() != null) {
            return E3Result.fail("此用户名已经被注册, 请重新输入!");
        }
        if (this.checkData(phone, 2).getData() != null) {
            return E3Result.fail("此手机号已经被注册!");
        }
        return E3Result.ok();
    }

    @Override
    public E3Result login(String username, String password) {
        // 数据有效性校验
        E3Result e3Result = this.checkLogin(username, password);
        if (!e3Result.getStatus().equals(E3Result.OK)) {
            return e3Result;
        }
        
        // 获取登录用户
        User user = (User)e3Result.getData();
        
        // 生成token, 存入redis: key : token:uuid; value: user
        String token = IdUtil.fastSimpleUUID().toUpperCase();
        String key = tokenKey + ":" + token;
        jedisClient.set(key, JSON.toJSONString(user));
        jedisClient.expire(key, expireSeconds);
        return E3Result.ok(token);
    }

    /**
     * 登录 - 数据有效性校验
     *
     * @param username
     * @param password
     * @return
     */
    private E3Result checkLogin(String username, String password) {
        if (StrUtil.isBlank(username)) {
            return E3Result.fail("用户名不能为空!");
        }
        if (StrUtil.isBlank( password)) {
            return E3Result.fail("密码不能为空!");
        }
        User user = userMapper.findByUserNameAndPassword(username, SecureUtil.md5(password));
        if (user == null) {
            return E3Result.fail("用户名或密码错误!");
        }
        // 密码不需要存
        return E3Result.ok(user.setPassword(null));
    }

}
