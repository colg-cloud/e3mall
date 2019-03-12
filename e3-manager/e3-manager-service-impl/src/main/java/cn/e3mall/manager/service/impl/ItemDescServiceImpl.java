package cn.e3mall.manager.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.e3mall.common.base.entity.ItemDesc;
import cn.e3mall.manager.service.ItemDescService;
import cn.e3mall.manager.service.core.BaseServiceImpl;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author colg
 */
@Slf4j
@Service
public class ItemDescServiceImpl extends BaseServiceImpl implements ItemDescService {
    
    /** 商品数据在缓存中 key 的前缀 */
    @Value("${item_info_pre}")
    private String itemInfoPre;
    /** key 的过期时间 */
    @Value("${expire_seconds}")
    private int expireSeconds;

    @Override
    public ItemDesc findById(Long itemId) {
        // 查询缓存
        String key = itemInfoPre + ":" + itemId + ":desc";
        String result = jedisClient.get(key);
        if (StrUtil.isNotEmpty(result)) {
            log.info("从 redis 获取的商品详情: {}", result);
            return JSON.parseObject(result, ItemDesc.class);
        }
        
        // 查询数据库
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        
        // 保存到缓存
        jedisClient.set(key, JSON.toJSONString(itemDesc));
        jedisClient.expire(key, expireSeconds);
        return itemDesc;
    }

}
