package cn.e3shop.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3shop.cart.service.CartService;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbUser;
import cn.e3shop.manager.service.ItemService;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

	@Resource
	private JedisClient jedisClient;

	@Resource
	private ItemService itemService;
	
	/**
	 * 每个user对应自己的购物车信息 userKey为key的开头部分
	 */
	@Value("${userKey}")
	private String userKey;

	@Override
	public E3Result addItemToRedis(Long product_id, Integer num,String userJson) {
		try {
			TbItem tbItem=null;
			TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
			List<TbItem>list=null;
			String itemJson = jedisClient.get(userKey+tbUser.getId()+tbUser.getUsername());
			//如果购物车中已经有了该商品增加个数
			if(StringUtils.isNotBlank(itemJson)) {
				list= JsonUtils.jsonToList(itemJson, TbItem.class);
				for (TbItem item : list) {
					if(item.getId().equals(product_id))
					{
						item.setNum(item.getNum()+num);
						String json = JsonUtils.objectToJson(list);
						jedisClient.set(userKey+tbUser.getId()+tbUser.getUsername(), json);
						return E3Result.ok();
					}
				}
				
				tbItem = itemService.findByItemId(product_id);
				if (tbItem != null) {
					tbItem.setNum(num);
					list.add(tbItem);
					String json = JsonUtils.objectToJson(list);
					jedisClient.set(userKey+tbUser.getId()+tbUser.getUsername(), json);
					return E3Result.ok();
				}
				else {
					return E3Result.build(400, "添加购物车失败");
				}
			}
			tbItem = itemService.findByItemId(product_id);
			list=new ArrayList<>();
			if (tbItem != null) {
				tbItem.setNum(num);
				list.add(tbItem);
				String json = JsonUtils.objectToJson(list);
				jedisClient.set(userKey+tbUser.getId()+tbUser.getUsername(), json);
				return E3Result.ok();
			} else {
				return E3Result.build(400, "添加购物车失败");
			}
		} catch (Exception e) {
			return E3Result.build(400, "添加购物车失败");
		}
	}
	
	public E3Result getCart(List<TbItem> list,String userJson) {
		
		try {
			
			if(list!=null&&list.size()>0) {
				for (TbItem tbItem : list) {
					addItemToRedis(tbItem.getId(), tbItem.getNum(), userJson);
				}
			}
			TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
			String json = jedisClient.get(userKey+tbUser.getId()+tbUser.getUsername());
			List<TbItem> items = JsonUtils.jsonToList(json, TbItem.class);
			
			return E3Result.ok(items);
		} catch (Exception e) {
			return E3Result.build(400, "获取购物车失败");
		}
		
	}

	@Override
	public void changeNum(Long id, Integer num, String userJson) {
		TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
		String itemJson = jedisClient.get(userKey+tbUser.getId()+tbUser.getUsername());
		if(StringUtils.isNotBlank(itemJson)) {
			List<TbItem> list= JsonUtils.jsonToList(itemJson, TbItem.class);
			for (TbItem item : list) {
				if(item.getId().equals(id))
				{
					item.setNum(num);
					String json = JsonUtils.objectToJson(list);
					jedisClient.set(userKey+tbUser.getId()+tbUser.getUsername(), json);
					break;
				}
			}
		}
	}

	@Override
	public void delete(Long id, String userJson) {
		TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
		String itemJson = jedisClient.get(userKey+tbUser.getId()+tbUser.getUsername());
		if(StringUtils.isNotBlank(itemJson)) {
			List<TbItem> list= JsonUtils.jsonToList(itemJson, TbItem.class);
			for (TbItem item : list) {
				if(item.getId().equals(id))
				{
					list.remove(item);
					String json = JsonUtils.objectToJson(list);
					jedisClient.set(userKey+tbUser.getId()+tbUser.getUsername(), json);
					break;
				}
			}
		}
		
	}

	@Override
	public void deleteAll(String userJson) {
		
		TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
		jedisClient.del(userKey+tbUser.getId()+tbUser.getUsername());
	}
	

}
