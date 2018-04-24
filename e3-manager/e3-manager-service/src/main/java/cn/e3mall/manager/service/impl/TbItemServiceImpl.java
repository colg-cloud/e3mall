package cn.e3mall.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.manager.core.BaseServiceImpl;
import cn.e3mall.manager.pojo.TbItem;
import cn.e3mall.manager.pojo.TbItemDesc;
import cn.e3mall.manager.service.TbItemService;

/**
 * 
 *
 * @author colg
 */
@Service
public class TbItemServiceImpl extends BaseServiceImpl implements TbItemService {

	@Value("${ITEM_INFO_PRE}")
	private String itemInfoPre;
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer itemInfoExpire;

	@Override
	public TbItem getTbItemById(Long id) {
		// 查询缓存
		String key = itemInfoPre + ":" + id + ":BASE";
		try {
			String jsonString = jedisClient.get(key);
			if (StringUtils.isNotBlank(jsonString)) {
				return JSON.parseObject(jsonString, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 缓存中没有,查询数据库
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);

		if (tbItem != null) {
			try {
				// 把结果添加到缓存
				jedisClient.set(key, JSON.toJSONString(tbItem));
				// 设置缓存的有效期
				jedisClient.expire(key, itemInfoExpire);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tbItem;
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		List<TbItem> list = tbItemMapper.selectAll();
		// 取分页结果
		return EasyUIDataGridResult.ok(list);
	}

	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		// 生成商品id，内部类使用局部变量，定义为final
		final long itemId = IDUtils.genItemId();
		// 补全tbItem的属性
		// 商品状态，1-正常，2-下架，3-删除
		tbItem.setId(itemId);
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		// 向商品表插入数据
		tbItemMapper.insert(tbItem);

		// 创建一个商品描述表对应的pojo对象
		TbItemDesc tbItemDesc = new TbItemDesc();
		// 补全属性
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		// 向商品描述表插入数据
		tbItemDescMapper.insert(tbItemDesc);

		// 发布 "商品添加" 消息
		jmsTemplate.send(topicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});

		// 返回成功
		return E3Result.ok();
	}

	@Override
	public E3Result updateItem(Long id, TbItem tbItem, String desc) {
		TbItem dbTbItem = tbItemMapper.selectByPrimaryKey(id);
		tbItem.setStatus(dbTbItem.getStatus());
		tbItem.setCreated(dbTbItem.getCreated());
		tbItem.setUpdated(new Date());
		tbItemMapper.updateByPrimaryKey(tbItem);

		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.updateByPrimaryKey(tbItemDesc);
		
		// 发布 "商品添加" 消息
		jmsTemplate.send(topicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(id + "");
				return textMessage;
			}
		});
		
		return E3Result.ok();
	}

	@Override
	public E3Result delete(String ids) {
		String[] itemIds = StringUtils.split(ids, ",");
		// 商品状态，1-正常，2-下架，3-删除
		tbItemMapper.updateRemove(itemIds);
		
		return E3Result.ok();
	}

	@Override
	public E3Result updateInstock(String ids) {
		String[] itemIds = StringUtils.split(ids, ",");
		// 商品状态，1-正常，2-下架，3-删除
		tbItemMapper.updateInstock(itemIds);
		
		return E3Result.ok();
	}

	@Override
	public E3Result updateReshelf(String ids) {
		String[] itemIds = StringUtils.split(ids, ",");
		// 商品状态，1-正常，2-下架，3-删除
		tbItemMapper.updateReshelf(itemIds);
		
		return E3Result.ok();
	}

}
