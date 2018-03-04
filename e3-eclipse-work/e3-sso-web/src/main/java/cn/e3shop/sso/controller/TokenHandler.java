package cn.e3shop.sso.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 从cookie中取数据进行回显
 * @author geek_kai
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3shop.sso.service.TokenService;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;
@Controller
public class TokenHandler {

	@Resource
	private TokenService tokenService;
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET
			,produces="application/json;charset=utf-8")
	@ResponseBody
	public String getUserInfo(@PathVariable String token,String callback) {
		E3Result result = tokenService.getUserInfo(token);
		String json = JsonUtils.objectToJson(result);
		if(StringUtils.isNotBlank(callback)) {
			return callback+"("+json+");";
		}
		return json;	
	}
	
}
