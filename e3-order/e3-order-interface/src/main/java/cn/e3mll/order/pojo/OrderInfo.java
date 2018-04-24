package cn.e3mll.order.pojo;

import java.util.List;

import cn.e3mall.manager.pojo.TbOrder;
import cn.e3mall.manager.pojo.TbOrderItem;
import cn.e3mall.manager.pojo.TbOrderShipping;

/**
 * 订单信息
 *
 * @author colg
 */
public class OrderInfo extends TbOrder {

	private static final long serialVersionUID = 1L;

	/** 订单明细集合, 名称对应页面 */
	private List<TbOrderItem> orderItems;

	/** 订单物流信息, 名称对应页面 */
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
