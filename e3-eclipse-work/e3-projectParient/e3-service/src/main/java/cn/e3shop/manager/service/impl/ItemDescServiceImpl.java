package cn.e3shop.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3shop.manager.mapper.TbItemDescMapper;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbItemDesc;
import cn.e3shop.manager.pojo.TbItemDescExample;
import cn.e3shop.manager.pojo.TbItemDescExample.Criteria;
import cn.e3shop.manager.service.ItemDescService;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.utils.JsonUtils;
@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private JedisClient jedisClient;
	//缓存分类
	@Value("${itemInfoDetils}")
	private String itemInfoDetils;
	//缓存过期时间
	@Value("${expireTime}")
	private String expireTime;
	/**
	 * 根据itemId获取ItemDesc对象
	 */
	@Override
	public TbItemDesc findItemDescByItemId(Long id) {
		TbItemDescExample example=new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		
		List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return  null;
	}
	@Override
	public TbItemDesc findItemDescByItemIdForShow(Long id) {
		try {
			String json = jedisClient.get(itemInfoDetils+":"+id+":desc");
			if(StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		TbItemDescExample example=new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		
		List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			jedisClient.set(itemInfoDetils+":"+id+":desc", JsonUtils.objectToJson(list.get(0)));
			jedisClient.expire(itemInfoDetils+":"+id+":desc", Integer.valueOf(expireTime));
			return list.get(0);
		}
		return  null;
	}

}
