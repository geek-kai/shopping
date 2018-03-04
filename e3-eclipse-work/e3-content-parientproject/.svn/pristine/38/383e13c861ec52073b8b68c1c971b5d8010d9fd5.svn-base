package cn.e3shop.content.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3shoop.content.service.ContentService;
import cn.e3shop.manager.mapper.TbContentMapper;
import cn.e3shop.manager.pojo.TbContent;
import cn.e3shop.manager.pojo.TbContentExample;
import cn.e3shop.manager.pojo.TbContentExample.Criteria;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.JsonUtils;
@Service
public class ContentServiceImpl implements ContentService {

	/**
	 * 在redis中缓存的字段
	 */
	@Value(value="Content_List")
	private String Content_List;
	@Resource
	private TbContentMapper tbContentMapper;
	@Resource
	private JedisClient jedisClient;
	@Override
	public EasyUiDateGridResult getEasyUiDateGridResult(Long categoryId,Integer page, Integer rows) {
		EasyUiDateGridResult result = new EasyUiDateGridResult();
		// 设置分页查询
		PageHelper.startPage(page, rows);
		// 开始查询
		TbContentExample tbContentExample = new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
		// 处理结果集
		PageInfo<TbContent> info = new PageInfo<>(list);
		long total = info.getTotal();
		result.setTotal((int) total);
		result.setRows(info.getList());
		return result;
	}

	@Override
	public E3Result addContent(TbContent tbContent) {
		//清空缓存达到缓存同步的目的
		jedisClient.hdel(Content_List, tbContent.getCategoryId()+"");
		try {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insertSelective(tbContent);
		return E3Result.ok();
			
		} catch (Exception e) {
			// TODO: handle exception
	}
		return null;

	}

	@Override
	public E3Result deleteContent(long[] ids) {
		//清空缓存达到缓存同步的目的
				jedisClient.hdel(Content_List, tbContentMapper.selectByPrimaryKey(ids[0]).getCategoryId()+"");
		try {
			for (long id : ids) {
				tbContentMapper.deleteByPrimaryKey(id);
			}
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public E3Result updateContent(TbContent tbcontent) {
		//清空缓存达到缓存同步的目的
		jedisClient.hdel(Content_List, tbcontent.getCategoryId()+"");
		try {
			tbcontent.setUpdated(new Date());
			tbContentMapper.updateByPrimaryKeySelective(tbcontent);
			return E3Result.ok();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<TbContent> findContentbyCategoryId(Long categoryId) {
		//查询缓存中有没有数据有的话直接返回
		//缓存不能影响到原定业务因此放到 try catch
		try {
			String json = jedisClient.hget(Content_List, categoryId+"");
			if(StringUtils.isNotBlank(json)) {
			List<TbContent> TbContents = JsonUtils.jsonToList(json, TbContent.class);
			return TbContents;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> TbContents = tbContentMapper.selectByExampleWithBLOBs(example);
		//查到的数据存入到缓存中
		try {
			jedisClient.hset(Content_List, categoryId+"", JsonUtils.objectToJson(TbContents));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return TbContents;
	}
}
