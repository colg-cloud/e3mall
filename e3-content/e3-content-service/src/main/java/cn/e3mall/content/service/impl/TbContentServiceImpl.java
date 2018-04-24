package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.content.core.BaseServiceImpl;
import cn.e3mall.content.service.TbContentService;
import cn.e3mall.manager.pojo.TbContent;

/**
 * 
 *
 * @author colg
 */
@Service
public class TbContentServiceImpl extends BaseServiceImpl implements TbContentService {

	/** 内容列表在 redis 中缓存的key */
	@Value("${CONTENT_LIST}")
	private String contentList;

	@Override
	public EasyUIDataGridResult queryListByCategoryId(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<TbContent> list = tbContentMapper.queryListByCategoryId(categoryId);
		return EasyUIDataGridResult.ok(list);
	}

	@Override
	public E3Result saveContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);

		// 缓存同步，删除缓存中对应的数据
		jedisClient.hdel(contentList, tbContent.getCategoryId() + "");
		return E3Result.ok();
	}

	@Override
	public E3Result editContent(Long id, TbContent tbContent) {
		TbContent dbTbContent = tbContentMapper.selectByPrimaryKey(id);
		tbContent.setCreated(dbTbContent.getCreated());
		tbContent.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKey(tbContent);

		// 缓存同步，删除缓存中对应的数据
		jedisClient.hdel(contentList, tbContent.getCategoryId() + "");
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContent(String ids) {
		String[] contentIds = StringUtils.split(ids, ",");
		tbContentMapper.deleteByIds(contentIds);
		// TODO colg redis 未删除缓存
		// 缓存同步，删除缓存中对应的数据
		jedisClient.hdel(contentList, contentIds);
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentListByCategoryId(Long categoryId) {
		// 未使用缓存
		// return tbContentMapper.queryListByCategoryId(categoryId);

		// 使用缓存查询，缓存查不到也不要影响业务
		try {
			// 查询缓存
			String json = jedisClient.hget(contentList, categoryId + "");
			// 如果缓存中有直接响应结果
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JSON.parseArray(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果没有查询数据库
		List<TbContent> list = tbContentMapper.queryListByCategoryId(categoryId);

		// 把结果添加到缓存
		try {
			jedisClient.hset(contentList, categoryId + "", JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
