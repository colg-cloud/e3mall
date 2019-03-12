package cn.e3mall.content.service.core;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.redis.JedisClient;
import cn.e3mall.content.dao.*;

/**
 * BaseServiceImpl 用于抽取注入的Mapper
 *
 * @author colg
 */
public abstract class BaseServiceImpl {
    
    @Autowired
    protected JedisClient jedisClient;

    @Autowired
    protected ContentMapper contentMapper;
    @Autowired
    protected ContentCategoryMapper contentCategoryMapper;
}
