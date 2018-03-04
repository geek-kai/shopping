package cn.e3shop.manager.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.service.ItemService;
import cn.e3shop.search.service.ItemSearchService;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;

@Controller
public class ItemHandler {

	@Resource(name = "itemService")
	private ItemService itemService;
	@Resource
	private ItemSearchService itemSearchService;
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET }, value = "/item/pageQuery")
	private @ResponseBody EasyUiDateGridResult pageQuery(Integer page, Integer rows) {

		return itemService.getEasyUiDataGridResultPojo(page, rows);
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem tbItem,String desc) {
		return itemService.addItem(tbItem, desc);
		
	}
	
	@RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
	@ResponseBody
	public E3Result updateItem(TbItem tbItem,String desc) {
		return itemService.updateItem(tbItem, desc);
		
	}
	
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteByUpdateStatus(Long ids[]) {
		return itemService.deleteByUpdateStatus(ids);
		
	}
	/**
	 * 上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public E3Result reshelfItems(Long ids[]) {
		return itemService.updateItemStatusTOreshelf(ids);
		
	}
	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public E3Result instockItems(Long ids[]) {
		return itemService.updateItemStatusTOInstock(ids);
		
	}
	/**
	 * 创建商品索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/index/item/import",method=RequestMethod.POST)
	public E3Result insertAllItemToSolr() {
		return itemSearchService.insertItemsToSolr();
	}
}
