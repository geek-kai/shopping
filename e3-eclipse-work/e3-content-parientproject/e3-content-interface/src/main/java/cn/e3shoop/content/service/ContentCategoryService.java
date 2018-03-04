package cn.e3shoop.content.service;

import java.util.List;

import cn.geek.e3shop.common.pojo.EasyUiTreeResult;
import cn.geek.e3shop.common.utils.E3Result;

public interface ContentCategoryService {

	/**
	 * 获得tree数据
	 * @param parientId
	 * @return
	 */
	public List<EasyUiTreeResult> getEasyUiTreeResult(Long parientId);
	/**
	 * 新增内容类别
	 * @param parentId
	 * @param name
	 * @return
	 */
	public E3Result insertContentCategory(Long parentId,String name);
	/**
	 * 根据id删除contentCategory对象
	 * @param id
	 * @return
	 */
	public E3Result deleteContentCategory(Long id);
	/**
	 * 更新contentCategory
	 * @param parentId
	 * @param name
	 */
	public void updateContentCategory(Long id,String name);
}
