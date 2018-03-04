package cn.e3shop.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3shop.cart.service.CartService;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbOrder;
import cn.e3shop.order.pojo.OrderInfo;
import cn.e3shop.order.service.OrderService;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.E3Result;

@Controller
public class OrderHandler {

	@Resource
	private JedisClient jedisClient;
	@Resource
	private CartService cartService;
	@Resource
	private OrderService orderService;
	@RequestMapping("/order/order-cart.html")
	/**
	 * 从购物车中取出商品信息并且展示到订单详情页面
	 * @return
	 */
	public String getOrderCart(HttpServletRequest request,Model model) {
		E3Result result = cartService.getCart(null, request.getAttribute("user").toString());
		List<TbItem>list=(List<TbItem>) result.getData();
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	
	@RequestMapping("/order/create.html")
	/**
	 * 从购物车中取出商品信息并且展示到订单详情页面
	 * @return
	 */
	public String createOrder(HttpServletRequest request,Model model,OrderInfo orderInfo) {
		String userJson = request.getAttribute("user").toString();
		E3Result result = orderService.insertOrderInfo(orderInfo);
		if(result.getStatus().equals(200))
		{
			TbOrder tbOrder=(TbOrder) result.getData();
			model.addAttribute("orderId", tbOrder.getOrderId());
			model.addAttribute("payment", tbOrder.getPayment());
			//清空购物车
			cartService.deleteAll(userJson);
			return "success";
		}
		return "success";
	}
	
	
}
