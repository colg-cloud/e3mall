package cn.e3mll.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.manager.pojo.TbItem;
import cn.e3mll.cart.core.BaseServiceImpl;
import cn.e3mll.cart.service.CartService;

/**
 * 购物车处理服务
 *
 * @author colg
 */
@Service
public class CartServiceImpl extends BaseServiceImpl implements CartService {

	/** # redis存放的购物车信息前缀 */
	@Value("${CART_LIST_PRE}")
	private String cartListPre;

	@Override
	public E3Result addCart(Long userId, Long itemId, Integer num) {
		/*
		 * 向redis中添加购物车, 数据类型是 hash, key:用户id, field:商品id, value:商品信息
		 * 
		 * 1. 判断商品是否存在
		 * 		存在: 	商品数量相加
		 * 		不存在:	根据商品id取商品信息, 添加到购物车列表
		 * 
		 * 2. 返回成功
		 */

		String key = cartListPre + ":" + userId;
		String field = itemId + "";
		Boolean hexists = jedisClient.hexists(key, field);
		// 商品存在
		if (hexists) {
			String jsonString = jedisClient.hget(key, field);
			// 把jsonString转换成tbItem
			TbItem tbItem = JSON.parseObject(jsonString, TbItem.class);
			tbItem.setNum(tbItem.getNum() + num);
			// 写回redis
			jedisClient.hset(key, field, JSON.toJSONString(tbItem));
			return E3Result.ok();
		}

		// 商品不存在, 根据商品id取商品信息
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		// 设置购物车里商品属性
		tbItem.setNum(num);
		String image = tbItem.getImage();
		if (StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		// 添加到购物车列表
		jedisClient.hset(key, field, JSON.toJSONString(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(Long userId, List<TbItem> tbItemList) {
		/*
		 * 1. 遍历cookie购物车商品列表
		 * 2. 判断redis中是否存在此商品
		 * 		1). 判断商品是否存在
		 * 			存在: 	商品数量相加
		 * 			不存在:	根据商品id取商品信息, 添加到购物车列表
		 * 
		 * 3. 返回成功
		 * 		
		 */
		for (TbItem tbItem : tbItemList) {
			// 与添加逻辑相同
			this.addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(Long userId) {
		// 根据用户id查询redis购物车列表
		String key = cartListPre + ":" + userId;
		List<String> jsonArrayString = jedisClient.hvals(key);
		List<TbItem> cartList = new ArrayList<>();
		for (String string : jsonArrayString) {
			cartList.add(JSON.parseObject(string, TbItem.class));
		}
		return cartList;
	}

	@Override
	public E3Result updateCartNum(Long userId, Long itemId, Integer num) {
		// 从redis中取商品信息
		String key = cartListPre + ":" + userId;
		String field = itemId + "";
		String jsonString = jedisClient.hget(key, field);
		TbItem tbItem = JSON.parseObject(jsonString, TbItem.class);
		// 更新商品数量
		tbItem.setNum(num);
		// 写入redis
		jedisClient.hset(key, field, JSON.toJSONString(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(Long userId, Long itemId) {
		// 从redis中删除购车商品
		String key = cartListPre + ":" + userId;
		String field = itemId + "";
		jedisClient.hdel(key, field);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteBathCart(Long userId, String itemId) {
		// 从redis中删除购车商品
		String key = cartListPre + ":" + userId;
		jedisClient.hdel(key, itemId.split(","));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteAllCart(Long userId) {
		// 从redis中当前用户的购物车列表
		String key = cartListPre + ":" + userId;
		jedisClient.del(key);
		return E3Result.ok();
	}

}
