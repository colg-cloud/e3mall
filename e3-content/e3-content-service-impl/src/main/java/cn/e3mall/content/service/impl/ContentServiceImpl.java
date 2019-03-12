package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import cn.e3mall.common.base.entity.Content;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.content.service.core.BaseServiceImpl;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author colg
 */
@Slf4j
@Service
public class ContentServiceImpl extends BaseServiceImpl implements ContentService {

    /** 内容列表在 redis 中缓存的key */
    @Value("${content_list}")
    private String contentList;
    /** key 的过期时间 */
    @Value("${expire_seconds}")
    private int expireSeconds;

    @Override
    public List<Content> getContentListByCategoryId(Long categoryId) {
        // 查询缓存
        String result = jedisClient.hget(contentList, categoryId + "");
        if (StrUtil.isNotEmpty(result)) {
            log.info("从 redis 获取的内容列表: {}", result);
            return JSON.parseArray(result, Content.class);
        }

        // 缓存没有, 查询数据库
        List<Content> dbContentList = contentMapper.selectContentListByCategoryId(categoryId);

        // 把结果添加到缓存
        jedisClient.hset(contentList, categoryId + "", JSON.toJSONString(dbContentList));
        jedisClient.expire(contentList, expireSeconds);
        return dbContentList;
    }

    @Override
    public EasyUIDataGridResult queryListByCategoryId(Integer page, Integer rows, Long categoryId) {
        return EasyUIDataGridResult.ok(PageHelper.startPage(page, rows).doSelectPage(() -> contentMapper.queryListByCategoryId(categoryId)));
    }

    @Override
    public E3Result saveContent(Content content) {
        Date now = new Date();
        // id 自增
        content.setCreated(now)
               .setUpdated(now);
        contentMapper.insertSelective(content);
        
        // 缓存同步, 删除缓存中对应的数据; 下次查询时会先查询数据库再添加到缓存
        jedisClient.hdel(contentList, content.getCategoryId() + "");
        return E3Result.ok(content);
    }

    @Override
    public E3Result editContent(Long id, Content content) {
        Content dbContent = contentMapper.selectByPrimaryKey(id);
        content.setCreated(dbContent.getCreated())
               .setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        
        // 缓存同步, 删除缓存中对应的数据; 下次查询时会先查询数据库再添加到缓存
        jedisClient.hdel(contentList, content.getCategoryId() + "");
        return E3Result.ok(content);
    }

    @Override
    public E3Result deleteContent(Long categoryId, String ids) {
        contentMapper.deleteContentByIds(categoryId, StrUtil.split(ids, ','));
        
        // 缓存同步, 删除缓存中对应的数据; 下次查询时会先查询数据库再添加到缓存
        jedisClient.hdel(contentList, categoryId + "");
        return E3Result.ok();
    }
}
