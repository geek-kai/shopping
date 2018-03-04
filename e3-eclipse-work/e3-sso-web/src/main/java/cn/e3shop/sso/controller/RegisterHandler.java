package cn.e3shop.sso.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shop.manager.pojo.TbUser;
import cn.e3shop.sso.service.RegisterService;
import cn.geek.e3shop.common.utils.E3Result;

@Controller
public class RegisterHandler {

	@Resource
	private RegisterService registerService;
	@RequestMapping(value="/reg",method=RequestMethod.GET)
	public String gotoRegisterPage(String returnUrl) {
		
		return "register";
		
	}
	
	@RequestMapping(value="/user/check/{checkdata}/{checkflag}",method=RequestMethod.GET)
	@ResponseBody
	public E3Result check(@PathVariable String checkdata,@PathVariable String checkflag) {
		E3Result result = registerService.check(checkdata, checkflag);
		
		return result;
			
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser tbUser) {
		E3Result result = registerService.register(tbUser);
		return result;
			
	}
}
