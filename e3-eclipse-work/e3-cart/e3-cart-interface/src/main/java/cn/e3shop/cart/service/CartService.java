package cn.e3shop.cart.service;

import java.util.List;

import cn.e3shop.manager.pojo.TbItem;
import cn.geek.e3shop.common.utils.E3Result;

public interface CartService {

	/**
	 * 如果用户已经登录了增加商品信息去redis中
	 * @param product_id
	 * @param num
	 * @return
	 */
	public E3Result addItemToRedis(Long product_id,Integer num,String userJson);
	/**
	 * 取出redis中的信息显示到前端 如果cookie中有信息进行合并
	 * @param list
	 * @return
	 */
	public E3Result getCart(List<TbItem> list,String userJson);
	/**
	 * 改变购物车商品数量信息
	 * @param id
	 * @param num
	 * @param userJson
	 */
	public void changeNum(Long id,Integer num,String userJson);
	/**
	 * 删除购物车中的商品
	 * @param id
	 * @param userJson
	 */
	public void delete(Long id,String userJson);
	/**
	 * 清空购物车
	 * @param userJson
	 */
	public void deleteAll(String userJson);
	
	
}
