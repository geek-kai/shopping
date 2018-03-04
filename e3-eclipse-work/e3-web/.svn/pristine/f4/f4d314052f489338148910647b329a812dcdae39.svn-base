package cn.e3shop.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shoop.content.service.ContentService;
import cn.e3shop.manager.pojo.TbContent;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;
@Controller
public class ContentHandler {

	@Resource
	private ContentService contentService;
	/**
	 * 获得EasyUiDateGridResult数据前端进行数据展示 并且分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/content/query/list",method = { RequestMethod.GET })
	@ResponseBody
	public EasyUiDateGridResult pageQuery(Long categoryId,Integer page, Integer rows) {

		return contentService.getEasyUiDateGridResult(categoryId, page, rows);
	}
	/**
	 * 新增content
	 * @param tbContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	public E3Result addContent(TbContent tbContent) {
		return contentService.addContent(tbContent);
	}
	
	/**
	 * 删除content
	 * @param ids
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	public E3Result deleteContent(long ids[]) {
		return contentService.deleteContent(ids);
	}
	/**
	 * 更新content
	 * @param tbcontent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/rest/content/edit",method=RequestMethod.POST)
	public E3Result updateContent(TbContent tbcontent) {
		return contentService.updateContent(tbcontent);
	}
	
}
