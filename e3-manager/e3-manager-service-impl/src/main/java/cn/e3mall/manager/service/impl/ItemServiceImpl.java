package cn.e3mall.manager.service.impl;

import java.util.Date;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.entity.ItemDesc;
import cn.e3mall.common.base.entity.ItemParamItem;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.common.base.util.SnUtil;
import cn.e3mall.manager.service.ItemService;
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
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {
    
    /** 商品数据在缓存中 key 的前缀 */
    @Value("${item_info_pre}")
    private String itemInfoPre;
    /** key 的过期时间 */
    @Value("${expire_seconds}")
    private int expireSeconds;

    @Override
    public Item getTbItemById(Long id) {
        /*
         * colg  [使用前缀对redis中的key进行分类]
         *  1. 第一层: 表名
         *  2. 第二层: 主键
         *  3. 第三层: 业务
         */
        
        // 查询缓存
        String key = itemInfoPre + ":" + id + ":base";
        String result = jedisClient.get(key);
        if (StrUtil.isNotEmpty(result)) {
            log.info("从 redis 获取的商品信息: {}", result);
            return JSON.parseObject(result, Item.class);
        }
        
        // 查询数据库
        Item item = itemMapper.selectByPrimaryKey(id);
        
        // 保存到缓存
        jedisClient.set(key, JSON.toJSONString(item));
        jedisClient.expire(key, expireSeconds);
        return item;
    }

    @Override
    public EasyUIDataGridResult selectItemList(Integer page, Integer rows) {
        return EasyUIDataGridResult.ok(PageHelper.startPage(page, rows).doSelectPage(() -> itemMapper.selectItemList()));
    }

    @Override
    public E3Result addItem(Item item, String desc, String paramData) {
        long itemId = SnUtil.genItemId();
        Date now = new Date();
        
        // 添加商品
        // 商品状态，1-正常，2-下架，3-删除
        item.setId(itemId)
            .setStatus((byte)1)
            .setCreated(now)
            .setUpdated(now);
        itemMapper.insertSelective(item);
        
        // 添加商品详情
        ItemDesc itemDesc = new ItemDesc(itemId, now, now, desc);
        itemDescMapper.insertSelective(itemDesc);
        
        // 添加商品规格参数
        ItemParamItem itemParamItem = new ItemParamItem(itemId, now, now, paramData);
        itemParamItemMapper.insertSelective(itemParamItem);
        
        // 发送 activemq 消息; 同步solr索引库, 生成静态页面
        sendMsg(destinationChangeItem, itemId);
        
        return E3Result.ok(item);
    }

    @Override
    public E3Result updateItem(Long itemId, Item item, String desc, Long itemParamId, String paramData) {
        Date now = new Date();
        
        // 修改商品
        Item dbItem = itemMapper.selectByPrimaryKey(itemId);
        item.setStatus(dbItem.getStatus())
            .setCreated(dbItem.getCreated())
            .setUpdated(now);
        itemMapper.updateByPrimaryKeySelective(item);
        
        // 修改商品详情
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        if (itemDesc != null) {
            itemDesc.setItemDesc(desc)
                    .setUpdated(now);
            itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        } else {
            itemDesc = new ItemDesc(itemId, now, now, desc);
            itemDescMapper.insertSelective(itemDesc);
        }

        // 修改商品规格参数
        ItemParamItem itemParamItem = itemParamItemMapper.selectByPrimaryKey(itemParamId);
        if (itemParamItem != null) {
            itemParamItem.setParamData(paramData)
                         .setUpdated(now);
            itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
        } else {
            itemParamItem = new ItemParamItem(itemId, now, now, paramData);
            itemParamItemMapper.insertSelective(itemParamItem);
        }
        
        // 发送 activemq 消息; 同步solr索引库, 生成静态页面
        sendMsg(destinationChangeItem, itemId);
        
        // 缓存同步, 删除缓存中对应的数据; 下次查询时会先查询数据库再添加到缓存
        jedisClient.del(itemInfoPre + ":" + itemId + ":base");
        jedisClient.del(itemInfoPre + ":" + itemId + ":desc");
        
        return E3Result.ok(item);
    }

    @Override
    public E3Result delete(String ids) {
        // 商品状态，1-正常，2-下架，3-删除
        itemMapper.updateItemStatus(StrUtil.split(ids, ','), 3);
        return E3Result.ok();
    }

    @Override
    public E3Result updateInstock(String ids) {
        // 商品状态，1-正常，2-下架，3-删除
        itemMapper.updateItemStatus(StrUtil.split(ids, ','), 2);
        return E3Result.ok();
    }

    @Override
    public E3Result updateReshelf(String ids) {
        // 商品状态，1-正常，2-下架，3-删除
        itemMapper.updateItemStatus(StrUtil.split(ids, ','), 1);
        return E3Result.ok();
    }
    
    /**
     * activemq 发送消息
     *
     * @param destination
     * @param itemId
     */
    private void sendMsg(Destination destination, long itemId) {
        jmsTemplate.send(destination, session -> {
            String text = itemId + "";
            log.info("activemq 发送消息: {}", text);
            return session.createTextMessage(text);
        });
    }
    
}
