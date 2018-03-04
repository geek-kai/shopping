package cn.e3shop.cart.controller.intercept;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.geek.e3shop.common.jedis.JedisClient;

public class CartIntercept implements HandlerInterceptor{

	@Resource
	private JedisClient jedisClient;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object handler, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object handler, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
		for (Cookie cookie : cookies) {
		if(cookie.getName().equals("token")) {
			String value = cookie.getValue();
			if(StringUtils.isNoneBlank(value)) {
			String json = jedisClient.get(value);
			if(StringUtils.isNotBlank(json)) {
				request.setAttribute("user", json);
				return true;
			}
			}
		}
		}
		}
		
		return true;
	}

}
