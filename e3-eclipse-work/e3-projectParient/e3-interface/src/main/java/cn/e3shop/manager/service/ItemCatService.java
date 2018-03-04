package cn.e3shop.manager.service;

import java.util.List;

import cn.geek.e3shop.common.pojo.EasyUiTreeResult;
public interface ItemCatService {

	public List<EasyUiTreeResult> getEasyUiTreeResults(Long parentId) ;
}
