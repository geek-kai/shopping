package cn.e3shop.order.pojo;

import java.util.ArrayList;
import java.util.List;

import cn.e3shop.manager.pojo.TbOrder;
import cn.e3shop.manager.pojo.TbOrderItem;
import cn.e3shop.manager.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder {

	private List <TbOrderItem>orderItems=new ArrayList<>();
	
	private TbOrderShipping orderShipping;

	public List <TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List <TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
}
