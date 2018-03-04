package cn.e3shop.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3shop.manager.mapper.TbOrderItemMapper;
import cn.e3shop.manager.mapper.TbOrderMapper;
import cn.e3shop.manager.mapper.TbOrderShippingMapper;
import cn.e3shop.manager.pojo.TbOrder;
import cn.e3shop.manager.pojo.TbOrderItem;
import cn.e3shop.manager.pojo.TbOrderShipping;
import cn.e3shop.order.pojo.OrderInfo;
import cn.e3shop.order.service.OrderService;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.E3Result;
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private TbOrderMapper tbOrderMapper;
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Value("${OrderId_redis_key}")
	private String OrderId_redis_key;
	@Resource
	private JedisClient jedisClient;
	public E3Result insertOrderInfo(OrderInfo orderInfo) {
		Long orderId = jedisClient.incr(OrderId_redis_key);
		orderInfo.setOrderId(orderId+"");
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		TbOrder tbOrder=orderInfo;
		tbOrderMapper.insert(tbOrder);
		 List<TbOrderItem>tborderItems=orderInfo.getOrderItems();
		 for (TbOrderItem tbOrderItem : tborderItems) {
			tbOrderItem.setId(UUID.randomUUID().toString());
			tbOrderItem.setOrderId(orderId+"");
			tbOrderItemMapper.insert(tbOrderItem);
		}
		 TbOrderShipping tbOrderShipping=orderInfo.getOrderShipping();
		 tbOrderShipping.setOrderId(orderId+"");
		 tbOrderShippingMapper.insert(tbOrderShipping);
		return E3Result.ok(tbOrder);
	}

}
