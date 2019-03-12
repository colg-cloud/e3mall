package cn.e3mall.manager.service.core;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import cn.e3mall.common.redis.JedisClient;
import cn.e3mall.manager.dao.*;

/**
 * BaseServiceImpl 用于抽取注入的Mapper
 *
 * @author colg
 */
public abstract class BaseServiceImpl {
    
    @Autowired
    protected JedisClient jedisClient;
    
    @Autowired
    protected JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("destinationChangeItem")
    protected Destination destinationChangeItem;

    @Autowired
    protected ItemMapper itemMapper;
    @Autowired
    protected ItemCatMapper itemCatMapper;
    @Autowired
    protected ItemDescMapper itemDescMapper;
    @Autowired
    protected ItemParamMapper itemParamMapper;
    @Autowired
    protected ItemParamItemMapper itemParamItemMapper;
}
