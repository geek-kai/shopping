package cn.e3shop.sso.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3shop.manager.mapper.TbUserMapper;
import cn.e3shop.manager.pojo.TbUser;
import cn.e3shop.manager.pojo.TbUserExample;
import cn.e3shop.manager.pojo.TbUserExample.Criteria;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;
@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private TbUserMapper tbUserMapper;
	@Resource
	private JedisClient jedisClient;
	@Value("${tokenExpireTime}")
	private String tokenExpireTime;
	public E3Result login(TbUser tbuser) {
		TbUserExample tbUserExample=new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		criteria.andUsernameEqualTo(tbuser.getUsername());
		List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
		if(list==null||list.size()==0) {
			return E3Result.build(400, "用户不存在");
		}
		else {
			TbUser user = list.get(0);
			if(!DigestUtils.md5DigestAsHex(tbuser.getPassword().getBytes()).equals(user.getPassword())) {
				return E3Result.build(400, "密码错误");
			}
		}
		//得到一个token存到redis中模拟session
		String token=UUID.randomUUID().toString();
		TbUser user = list.get(0);
		//不将密码传到前端
		user.setPassword(null);
		jedisClient.set(token, JsonUtils.objectToJson(user));
		jedisClient.expire(token, new Integer(tokenExpireTime));
		//把token传到前端存储到cookie中
		return E3Result.ok(token);
	}

}
