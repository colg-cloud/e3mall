package cn.e3mall.sso.service.core;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.redis.JedisClient;
import cn.e3mall.sso.dao.*;

/**
 * BaseServiceImpl 用于抽取注入的Mapper
 *
 * @author colg
 */
public abstract class BaseServiceImpl {
    
    @Autowired
    protected JedisClient jedisClient;
    
    @Autowired
    protected UserMapper userMapper;
}
