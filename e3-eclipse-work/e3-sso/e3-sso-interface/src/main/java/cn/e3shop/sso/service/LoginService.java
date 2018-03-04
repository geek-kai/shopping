package cn.e3shop.sso.service;

import cn.e3shop.manager.pojo.TbUser;
import cn.geek.e3shop.common.utils.E3Result;
/**
 * 登录功能
 * @author geek_kai
 *
 */
public interface LoginService {

	/**
	 * 登录
	 * @param tbuser
	 * @return
	 */
	public E3Result login(TbUser tbuser);
}
