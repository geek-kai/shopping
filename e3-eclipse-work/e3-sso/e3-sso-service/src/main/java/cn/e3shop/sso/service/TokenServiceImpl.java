package cn.e3shop.sso.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3shop.manager.pojo.TbUser;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;
@Service
public class TokenServiceImpl implements TokenService {

	@Value("${tokenExpireTime}")
	private String tokenExpireTime;
	@Resource
	private JedisClient jedisClient;
	@Override
	public E3Result getUserInfo(String token) {
		String userJson = jedisClient.get(token);
		if(StringUtils.isBlank(userJson)) {
			return E3Result.build(400, "token已经过期");
		}
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		jedisClient.expire(token, new Integer(tokenExpireTime));
		return E3Result.ok(user);
	}

}
