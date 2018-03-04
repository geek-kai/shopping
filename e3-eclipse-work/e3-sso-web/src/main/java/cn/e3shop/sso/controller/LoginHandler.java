package cn.e3shop.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shop.manager.pojo.TbUser;
import cn.e3shop.sso.service.LoginService;
import cn.geek.e3shop.common.utils.CookieUtils;
import cn.geek.e3shop.common.utils.E3Result;

@Controller
public class LoginHandler {

	@Resource
	private LoginService loginService;
	@RequestMapping(value= {"/","/page/login"},method=RequestMethod.GET)
	public String gotoLoginPage(String returnUrl,Model model) {
		model.addAttribute("redirect", returnUrl);
		return "login";
	}
	@RequestMapping(value= "/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(TbUser tbuser,HttpServletRequest request,HttpServletResponse response) {
		E3Result result = loginService.login(tbuser);
		if(result.getData()!=null)
		CookieUtils.setCookie(request, response, "token", result.getData().toString(),3600);
		return result;
	}
	
	
	
}
