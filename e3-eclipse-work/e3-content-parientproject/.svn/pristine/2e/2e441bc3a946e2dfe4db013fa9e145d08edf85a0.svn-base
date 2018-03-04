package cn.e3shoop.content.service;


import java.util.List;

import cn.e3shop.manager.pojo.TbContent;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;

public interface ContentService {
	/**
	 * 获得EasyUiDateGridResult数据进行前端显示
	 * @param page
	 * @param rows
	 * @return
	 */
	public  EasyUiDateGridResult getEasyUiDateGridResult(Long categoryId,Integer page, Integer rows);
	/**
	 * 新增内容
	 * @param tbContent
	 * @return
	 */
	public E3Result addContent(TbContent tbContent);
	/**
	 * 批量删除Content
	 * @param ids
	 * @return
	 */
	public E3Result deleteContent(long ids[]);
	/**
	 * 更新content
	 * @param tbcontent
	 * @return
	 */
	public E3Result updateContent(TbContent tbcontent);
	/**
	 * 根据categoryId查找内容
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> findContentbyCategoryId(Long categoryId);
}
