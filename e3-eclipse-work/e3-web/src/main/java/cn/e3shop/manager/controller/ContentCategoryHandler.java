package cn.e3shop.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shoop.content.service.ContentCategoryService;
import cn.geek.e3shop.common.pojo.EasyUiTreeResult;
import cn.geek.e3shop.common.utils.E3Result;

@Controller
public class ContentCategoryHandler {

	@Resource
	private ContentCategoryService  contentCategoryService;
	/**
	 * 返回EasyUiTreeResult数据进行tree数据显示
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/content/category/list",method=RequestMethod.GET)
	public List<EasyUiTreeResult> getEasyUiTreeResult(@RequestParam(defaultValue="0",name="id")Long parientId) {
		
		return contentCategoryService.getEasyUiTreeResult(parientId);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	public E3Result insertContentCategory(Long parentId,String name) {
		
		return contentCategoryService.insertContentCategory(parentId, name);
		
	}
	/**
	 * 根据ID删除contentCategory
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/content/category/delete",method=RequestMethod.POST)
	public E3Result deleteContentCategory(Long id) {
		return contentCategoryService.deleteContentCategory(id);
	}
	/**
	 * 更新contentCategory
	 * @param id
	 * @param name
	 */
	
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	public void updateContentCategory(Long id,String name) {
	contentCategoryService.updateContentCategory(id, name);
	}
	
}
