package cn.e3shop.sso.service;

import cn.geek.e3shop.common.utils.E3Result;

public interface TokenService {

	public E3Result getUserInfo(String token);
}
