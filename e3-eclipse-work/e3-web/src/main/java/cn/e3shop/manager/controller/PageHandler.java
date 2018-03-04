package cn.e3shop.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageHandler {

	@RequestMapping("/{page}")
	public String getPage(@PathVariable String page) {
		
		return page;
	}
}
