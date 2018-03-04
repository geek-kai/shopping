package cn.e3shop.manager.service;

import cn.e3shop.manager.pojo.TbItemDesc;

public interface ItemDescService {

	/**
	 * 通过ItemId查询商品信息 后台用不放到缓存
	 * @param id
	 * @return
	 */
	 public  TbItemDesc findItemDescByItemId(Long id);
	 /**
		 * 通过ItemId查询商品信息 前台用放到缓存
		 * @param id
		 * @return
		 */
	 public  TbItemDesc findItemDescByItemIdForShow(Long id);
	
}
