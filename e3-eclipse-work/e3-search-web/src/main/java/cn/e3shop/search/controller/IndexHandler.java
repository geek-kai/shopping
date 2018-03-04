package cn.e3shop.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.search.service.ItemSearchService;
import cn.geek.e3shop.common.pojo.SearchResult;

@Controller
public class IndexHandler {

	@Value("${defaultPageNum}")
	private Integer defaultPageNum; 
	@Resource
	private ItemSearchService itemSearchService;
	@RequestMapping(value="/search.html",method=RequestMethod.GET)
	public String gotoIndex(String keyword,Model model,@RequestParam(defaultValue="1") Integer page) {
		byte[] bytes;
		try {
			bytes = keyword.getBytes("iso-8859-1");
			keyword=new String(bytes,"utf-8");
			model.addAttribute("query", keyword);
			model.addAttribute("page", page);
			SearchResult searchResultByKeywords = itemSearchService.findSearchResultByKeywords(keyword, page, defaultPageNum);
			model.addAttribute("itemList",searchResultByKeywords.getItemList());
			model.addAttribute("recourdCount",searchResultByKeywords.getRecourdCount());
			model.addAttribute("totalPages",searchResultByKeywords.getTotalPages());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return"search";
	}
}
