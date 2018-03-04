package cn.e3shop.order.service;

import cn.e3shop.order.pojo.OrderInfo;
import cn.geek.e3shop.common.utils.E3Result;

public interface OrderService {

	/**
	 * 把订单信息保存到数据库中
	 * @param orderInfo
	 * @return
	 */
	public E3Result insertOrderInfo(OrderInfo orderInfo);
}
