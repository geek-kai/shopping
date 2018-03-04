package cn.e3shop.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.e3shop.manager.mapper.TbItemCatMapper;
import cn.e3shop.manager.pojo.TbItemCat;
import cn.e3shop.manager.pojo.TbItemCatExample;
import cn.e3shop.manager.pojo.TbItemCatExample.Criteria;
import cn.e3shop.manager.service.ItemCatService;
import cn.geek.e3shop.common.pojo.EasyUiTreeResult;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<EasyUiTreeResult> getEasyUiTreeResults(Long parentId) {
		List<EasyUiTreeResult>results=new ArrayList<EasyUiTreeResult>();
		TbItemCatExample example=new TbItemCatExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
		for (TbItemCat tbItemCat : tbItemCats) {
			EasyUiTreeResult result=new EasyUiTreeResult();
			result.setId(tbItemCat.getId());
			result.setText(tbItemCat.getName());
			result.setState(tbItemCat.getIsParent()?"closed":"open");
			results.add(result);
		}
		return results;
	}

}
