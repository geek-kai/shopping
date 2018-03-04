package cn.e3shop.portal.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.e3shoop.content.service.ContentService;
import cn.e3shop.manager.pojo.TbContent;

@Controller
public class IndexHandle {
	@Resource
	private ContentService contentService;
	//轮播图id
	@Value(value="${LBPICTURE_CATEGORYID}")
	private  Long LBPICTURE_CATEGORYID;
	@RequestMapping(value="/index.html",method=RequestMethod.GET)
	public String gotoIndex(Model model) {
		List<TbContent> tbContents= contentService.findContentbyCategoryId(LBPICTURE_CATEGORYID);
		model.addAttribute("ad1List", tbContents);
		return "index";
	}
}
