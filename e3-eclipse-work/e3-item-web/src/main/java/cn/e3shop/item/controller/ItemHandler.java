package cn.e3shop.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.e3shop.item.pojo.Item;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbItemDesc;
import cn.e3shop.manager.service.ItemDescService;
import cn.e3shop.manager.service.ItemService;

@Controller
public class ItemHandler {
	@Resource
	private ItemService itemService; 
	@Resource
	private ItemDescService itemDescService;
	@RequestMapping(value="/item/{itemId}",method=RequestMethod.GET)
	public String getItemById(@PathVariable Long itemId,Model model) {
		TbItem tbItem = itemService.getItemDetailsById(itemId);
		Item item=new Item(tbItem);
		model.addAttribute("item", item);
		TbItemDesc tbItemDesc = itemDescService.findItemDescByItemIdForShow(itemId);
		model.addAttribute("itemDesc", tbItemDesc);
		return "item";
		
	}

}
