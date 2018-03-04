package cn.e3shop.manager.service;

import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbItemDesc;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;

public interface ItemService {

	/**
	 * 通过商品Id查询商品用于更新商品时数据回显 不放到缓存
	 * @param id
	 * @return
	 */
	public TbItem findByItemId(Long id); 
	/**
	 * 通过商品Id查询商品 用于前台展示 放到缓存
	 * @param id
	 * @return
	 */
	public TbItem getItemDetailsById(Long id); 
	/**
	 * 获得datagrid数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDateGridResult getEasyUiDataGridResultPojo(Integer page,Integer rows);
	/**
	 * 添加商品
	 * @param item
	 * @param desc
	 * @return
	 */
	public E3Result addItem(TbItem item,String desc);
	/**
	 * 更新商品
	 * @param item
	 * @param desc
	 * @return
	 */
	public E3Result updateItem(TbItem item,String desc);
	/**
	 * 通过改变商品状态删除商品
	 * @param ids
	 * @return
	 */
	public E3Result deleteByUpdateStatus(Long ids[]);
	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	public E3Result updateItemStatusTOInstock(Long ids[]);
	/**
	 * 上架商品
	 * @param ids
	 * @return
	 */
	public E3Result updateItemStatusTOreshelf(Long ids[]);
	
	
}
