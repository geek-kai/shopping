package cn.e3shop.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.e3shoop.content.service.ContentCategoryService;
import cn.e3shop.manager.mapper.TbContentCategoryMapper;
import cn.e3shop.manager.pojo.TbContentCategory;
import cn.e3shop.manager.pojo.TbContentCategoryExample;
import cn.e3shop.manager.pojo.TbContentCategoryExample.Criteria;
import cn.geek.e3shop.common.pojo.EasyUiTreeResult;
import cn.geek.e3shop.common.utils.E3Result;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUiTreeResult> getEasyUiTreeResult(Long parientId) {
		List<EasyUiTreeResult>results=new ArrayList<>();
		TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parientId);
		criteria.andStatusEqualTo(1);
		List<TbContentCategory> lists = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
		for (TbContentCategory tbContentCategory : lists) {
			EasyUiTreeResult result=new EasyUiTreeResult();
			result.setId(tbContentCategory.getId());
			result.setText(tbContentCategory.getName());
			result.setState(tbContentCategory.getIsParent()?"closed":"open");
			results.add(result);
		}
		return results;
	}
	@Override
	public E3Result insertContentCategory(Long parentId, String name) {
		TbContentCategory tbContentCategory=new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
//		新创建的TbContentCategory肯定不是父节点
		tbContentCategory.setIsParent(false);
		tbContentCategory.setSortOrder(1);
//		状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.insert(tbContentCategory);
		//判断父节点的isPArient属性 如果是false 更改成true
		TbContentCategory tbContentCategoryParient = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		Boolean isParent = tbContentCategory.getIsParent();
		if(!isParent) {
			tbContentCategoryParient.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(tbContentCategoryParient);
		}
		
		return E3Result.ok(tbContentCategory);
		
	}
	@Override
	public E3Result deleteContentCategory(Long id) {
		//先判断是不是父节点 如果是父节点不允许删除
		TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(id);
		int count = tbContentCategoryMapper.countByExample(tbContentCategoryExample);
		if(count>0) {
			return E3Result.build(100,"不能删除父节点");
		}else {
			TbContentCategory tbContentCategory=new TbContentCategory();
			tbContentCategory.setId(id);
			tbContentCategory.setStatus(2);
			tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
			return E3Result.ok();
		}
	}
	@Override
	public void updateContentCategory(Long id, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory(); 
		tbContentCategory.setId(id);
		tbContentCategory.setName(name);
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
		
	}

}
