package cn.e3shop.manager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shop.manager.pojo.TbItemDesc;
import cn.e3shop.manager.service.ItemDescService;

@Controller
public class ItemDescHandler {
	@Resource
	private ItemDescService itemDescService;
	@RequestMapping(value="/rest/item/query/item/desc/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findItemDescByItemId(@PathVariable Long id) {
	
		Map <String,Object> map=new HashMap<>();
		try {
			
			TbItemDesc itemDesc = itemDescService.findItemDescByItemId(id);
			map.put("status", 200);
			map.put("data", itemDesc);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
		
		
	}
}
