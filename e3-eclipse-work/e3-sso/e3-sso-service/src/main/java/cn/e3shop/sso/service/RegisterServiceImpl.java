package cn.e3shop.sso.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3shop.manager.mapper.TbUserMapper;
import cn.e3shop.manager.pojo.TbUser;
import cn.e3shop.manager.pojo.TbUserExample;
import cn.e3shop.manager.pojo.TbUserExample.Criteria;
import cn.geek.e3shop.common.utils.E3Result;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Resource
	private TbUserMapper tbUserMapper;

	public E3Result check(String checkdata, String checkflag) {
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		if (checkflag.equals("1")) {
			criteria.andUsernameEqualTo(checkdata);
			List<TbUser> lists = tbUserMapper.selectByExample(tbUserExample);
			if (lists !=null && lists.size()>0) {
				return E3Result.build(400, "用户名已存在", false);
			}
		}
		if (checkflag.equals("2")) {
			criteria.andPhoneEqualTo(checkdata);
			List<TbUser> lists = tbUserMapper.selectByExample(tbUserExample);
			if (lists !=null && lists.size()>0)  {
				// 不存在返回true
				return E3Result.build(400, "号码已存在", false);

			}
		}
		if (checkflag.equals("3")) {
			criteria.andEmailEqualTo(checkdata);
			List<TbUser> lists = tbUserMapper.selectByExample(tbUserExample);
			if (lists !=null && lists.size()>0) {
				// 不存在返回true
				return E3Result.build(400, "邮箱已存在", false);

			}
		}
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser tbuser) {
		if (StringUtils.isBlank(tbuser.getUsername()) || StringUtils.isBlank(tbuser.getPassword())
				|| StringUtils.isBlank(tbuser.getPhone())) {
			return E3Result.build(400, "注册失败,用户名、密码、号码不能为空");
		}
		tbuser.setCreated(new Date());
		tbuser.setUpdated(new Date());
		String passwordMd5 = DigestUtils.md5DigestAsHex(tbuser.getPassword().getBytes());
		tbuser.setPassword(passwordMd5);
		tbUserMapper.insert(tbuser);

		return E3Result.ok();
	}

}
