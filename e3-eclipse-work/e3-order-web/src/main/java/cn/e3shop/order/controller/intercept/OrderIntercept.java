package cn.e3shop.order.controller.intercept;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3shop.cart.service.CartService;
import cn.e3shop.manager.pojo.TbItem;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.CookieUtils;
import cn.geek.e3shop.common.utils.JsonUtils;
/**
 * 订单拦截器 实现确认订单前必须让客户登录并且合并购物车功能
 * @author geek_kai
 *
 */
public class OrderIntercept implements HandlerInterceptor {

	@Resource
	private JedisClient jedisClient;
	@Value("${cartKey}")
	private String cartKey;
	@Resource
	private CartService cartService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//判断cookie是否不为空以及其中是否有token
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("token")) {
					String value = cookie.getValue();
					String userjson = jedisClient.get(value);
					//如果userjson失效返回登录页面 如果没没有失效合并购物车
					if(StringUtils.isNotBlank(userjson)) {
						request.setAttribute("user", userjson);
						for (Cookie cookie2 : cookies) {
							if(cookie2.getName().equals(cartKey)) {
								//如果购物车存在合并购物车
								String itemCartJson = CookieUtils.getCookieValue(request, cartKey, true);
								if(StringUtils.isNotBlank(itemCartJson)) {
								List<TbItem> list = JsonUtils.jsonToList(itemCartJson, TbItem.class);
								cartService.getCart(list, userjson);
								return true;}else {
									return true;
								}
							}
						}
						return true;
					}else {
						//返回登录页面
						response.sendRedirect("http://localhost:8088?returnUrl=http://localhost:8094/order/order-cart.html");
						return false;
					}
				}
			}
			//如果cookie中没有token返回登录页面
			response.sendRedirect("http://localhost:8088?returnUrl=http://localhost:8094/order/order-cart.html");
			return false;
			
		}
		else {
			//返回登录页面
			response.sendRedirect("http://localhost:8088?returnUrl=http://localhost:8094/order/order-cart.html");
			return false;
		}
	}

}
