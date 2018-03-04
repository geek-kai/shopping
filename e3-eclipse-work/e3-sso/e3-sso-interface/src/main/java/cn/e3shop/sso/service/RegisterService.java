package cn.e3shop.sso.service;

import cn.e3shop.manager.pojo.TbUser;
import cn.geek.e3shop.common.utils.E3Result;

public interface RegisterService {

	/**
	 * 检测注册数据是否已经存在
	 * @param checkdata
	 * @param checkflag
	 * @return
	 */
	public E3Result check(String checkdata,String checkflag);
	/**
	 * 进行注册
	 * @return
	 */
	public E3Result register (TbUser tbuser);
}
