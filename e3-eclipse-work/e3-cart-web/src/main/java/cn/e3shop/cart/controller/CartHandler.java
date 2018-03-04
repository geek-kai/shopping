package cn.e3shop.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.e3shop.cart.service.CartService;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.service.ItemService;
import cn.geek.e3shop.common.utils.CookieUtils;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;

@Controller
public class CartHandler {

	@Resource
	private ItemService itemService;
	@Resource
	private CartService cartService;

	@Value("${cartKey}")
	private String cartKey;

	/**
	 * 添加购物车
	 * 
	 * @param product_id
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cart/add/{product_id}",method=RequestMethod.GET)
	public String addItemToCart(@PathVariable Long product_id, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		String userJson = (String) request.getAttribute("user");
		if (StringUtils.isNotBlank(userJson)) {
			E3Result e3Result = cartService.addItemToRedis(product_id, num, userJson);
			if (e3Result.getStatus().equals(200)) {
				return "cartSuccess";
			} else {
				return "cartFail";
			}
		}
		// 判断购物车是否已经存在该商品如果有的话改变商品数量
		String cookieValue = CookieUtils.getCookieValue(request, cartKey, true);
		List<TbItem> list = null;
		if (StringUtils.isNotBlank(cookieValue)) {
			list = JsonUtils.jsonToList(cookieValue, TbItem.class);
			for (TbItem tbItem : list) {
				if (tbItem.getId().equals(product_id)) {
					tbItem.setNum(tbItem.getNum() + num);
					CookieUtils.setCookie(request, response, cartKey, JsonUtils.objectToJson(list).toString(), 3600,
							true);
					return "cartSuccess";
				}
			}

			TbItem tbItem = itemService.findByItemId(product_id);
			tbItem.setNum(num);
			list.add(tbItem);
			CookieUtils.setCookie(request, response, cartKey, JsonUtils.objectToJson(list).toString(), 3600, true);
			return "cartSuccess";
		} else {
			list = new ArrayList<>();
			TbItem tbItem = itemService.findByItemId(product_id);
			tbItem.setNum(num);
			if (tbItem != null) {
				list.add(tbItem);
				CookieUtils.setCookie(request, response, cartKey, JsonUtils.objectToJson(list).toString(), 3600, true);
				return "cartSuccess";
			}

			return "cartFail";

		}

	}
	@RequestMapping(value="/cart/getCart",method=RequestMethod.GET)
	public String getCart(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		String userJson = (String) request.getAttribute("user");
		List<TbItem> list=null;
		String cookieValue = CookieUtils.getCookieValue(request, cartKey, true);
		if(StringUtils.isNotBlank(cookieValue)) {
			list= JsonUtils.jsonToList(cookieValue, TbItem.class);
		}
		if (StringUtils.isNotBlank(userJson)) {
		E3Result e3Result = cartService.getCart(list, userJson);
		if(e3Result.getStatus().equals(200)) {
			model.addAttribute("cartList", e3Result.getData());
			if(StringUtils.isNotBlank(cookieValue)) {
			CookieUtils.setCookie(request, response, cartKey, null, 0);
			}
			return "cart";
		}
		}
		model.addAttribute("cartList", list);
		return "cart";
		
	}
	

	@RequestMapping(value="/cart/update/num/{id}/{num}",method=RequestMethod.POST)
	public void changeNum(@PathVariable Long id,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response) {
		String userJson = (String) request.getAttribute("user");
		if(StringUtils.isNotBlank(userJson)) {
			cartService.changeNum(id, num, userJson);
		}else {
		String cookieValue = CookieUtils.getCookieValue(request, cartKey, true);
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		for (TbItem tbItem : list) {
			if(tbItem.getId().equals(id)) {
				tbItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, cartKey, JsonUtils.objectToJson(list).toString(), 3600, true);
		}
	}

	/**
	 * 删除购物车中的商品
	 * @return
	 */
	@RequestMapping(value="/cart/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) {
		String userJson = (String) request.getAttribute("user");
		if(StringUtils.isNotBlank(userJson)) {
			cartService.delete(id, userJson);
		}else {
		String cookieValue = CookieUtils.getCookieValue(request, cartKey, true);
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		for (TbItem tbItem : list) {
			if(tbItem.getId().equals(id)) {
				list.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, cartKey, JsonUtils.objectToJson(list).toString(), 3600, true);
		}
		return "redirect:/cart/getCart.html";	
	}
	

}
