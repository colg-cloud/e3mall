package cn.e3mll.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.manager.pojo.TbOrderItem;
import cn.e3mall.manager.pojo.TbOrderShipping;
import cn.e3mall.manager.pojo.TbUser;
import cn.e3mll.order.core.BaseServiceImpl;
import cn.e3mll.order.pojo.OrderInfo;
import cn.e3mll.order.service.OrderService;

/**
 * 
 *
 * @author colg
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

	/** 订单号id */
	@Value("${ORDER_ID_GEN_KEY}")
	private String orderIdGenKey;
	/** 订单号初始值 */
	@Value("${ORDER_ID_BEGIN}")
	private String orderIdBegin;
	/** 订单明细id */
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String orderItemIdGenKey;

	@Override
	public E3Result createOrder(OrderInfo orderInfo, TbUser tbUser) {
		// 判断是否有orderId
		if (!jedisClient.exists(orderIdGenKey)) {
			// 设置初始值
			jedisClient.set(orderIdGenKey, orderIdBegin);
		}

		// 生成订单号, 使用redis的incr生成
		String orderId = jedisClient.incr(orderIdGenKey).toString();

		// 补全orderInfo的属性
		orderInfo.setOrderId(orderId);
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		orderInfo.setUserId(tbUser.getId());
		orderInfo.setBuyerNick(tbUser.getUsername());
		// 插入订单表
		tbOrderMapper.insert(orderInfo);

		// 向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			// 生成订单明细id
			String orderItemId = jedisClient.incr(orderItemIdGenKey).toString();
			tbOrderItem.setId(orderItemId);
			tbOrderItem.setOrderId(orderId);
			// 插入数据
			tbOrderItemMapper.insert(tbOrderItem);
		}

		// 向订单物流表插入数据
		TbOrderShipping tbOrderShipping = orderInfo.getOrderShipping();
		tbOrderShipping.setOrderId(orderId);
		tbOrderShipping.setCreated(new Date());
		tbOrderShipping.setUpdated(new Date());
		tbOrderShipping.setReceiverName(tbUser.getUsername());
		tbOrderShipping.setReceiverMobile(tbUser.getPhone());
		// 插入数据
		tbOrderShippingMapper.insert(tbOrderShipping);

		// 返回E3Result,包含订单号
		return E3Result.ok(orderId);
	}

}
