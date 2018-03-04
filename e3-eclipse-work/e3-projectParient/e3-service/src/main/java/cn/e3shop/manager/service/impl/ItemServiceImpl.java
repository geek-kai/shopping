package cn.e3shop.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3shop.manager.mapper.TbItemDescMapper;
import cn.e3shop.manager.mapper.TbItemMapper;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbItemDesc;
import cn.e3shop.manager.pojo.TbItemExample;
import cn.e3shop.manager.pojo.TbItemExample.Criteria;
import cn.e3shop.manager.service.ItemService;
import cn.geek.e3shop.common.jedis.JedisClient;
import cn.geek.e3shop.common.pojo.EasyUiDateGridResult;
import cn.geek.e3shop.common.utils.E3Result;
import cn.geek.e3shop.common.utils.IDUtils;
import cn.geek.e3shop.common.utils.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private JmsTemplate jmsTemplate;
	@Resource(name="topicDestination")
	private Destination destination;
	@Resource
	private JedisClient jedisClient;
	//缓存分类
	@Value("${itemInfoDetils}")
	private String itemInfoDetils;
	//缓存过期时间
	@Value("${expireTime}")
	private String expireTime;
	@Override
	public TbItem findByItemId(Long id) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andIdEqualTo(id);
		criteria.andStatusEqualTo((byte)1);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public EasyUiDateGridResult getEasyUiDataGridResultPojo(Integer page, Integer rows) {
		EasyUiDateGridResult result = new EasyUiDateGridResult();
		// 设置分页查询
		PageHelper.startPage(page, rows);
		// 开始查询
		TbItemExample tbItemExample = new TbItemExample();
		tbItemExample.setOrderByClause("id");
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
		// 处理结果集
		PageInfo<TbItem> info = new PageInfo<>(list);
		long total = info.getTotal();

		result.setTotal((int) total);
		result.setRows(info.getList());
		return result;
	}

	@Override
	public E3Result addItem( TbItem item, String desc) {
		
		try {
			
			final long itemId = IDUtils.genItemId();
			if(item!=null) {
//			手动设置id
				item.setId(itemId);
//			设置商品状态 '商品状态，1-正常，2-下架，3-删除',
				item.setStatus((byte)1);
				item.setUpdated(new Date());
				item.setCreated(new Date());
				tbItemMapper.insert(item);
			}
			
			TbItemDesc tbItemDesc=new TbItemDesc();
			
			if(StringUtils.isNotBlank(desc)) {
//			手动设置id
				tbItemDesc.setItemId(itemId);
				tbItemDesc.setCreated(new Date());
				tbItemDesc.setUpdated(new Date());
				tbItemDesc.setItemDesc(desc);
				tbItemDescMapper.insert(tbItemDesc);
			}
			
			
			jmsTemplate.send(destination, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(itemId+"");
					//注意发送消息的时候可能事务还没有提交所以数据库没有这个对象
					return textMessage;
				}});
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 更新商品列表
	 */
	@Override
	public E3Result updateItem(final TbItem item, String desc) {
	try {
			if(item!=null) {
				item.setUpdated(new Date());
				tbItemMapper.updateByPrimaryKeySelective(item);
			}
			
			TbItemDesc tbItemDesc=new TbItemDesc();
			
			if(StringUtils.isNotBlank(desc)) {
//				设置itemId
				tbItemDesc.setItemId(item.getId());
				tbItemDesc.setUpdated(new Date());
				tbItemDesc.setItemDesc(desc);
				tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
			}
			
			jmsTemplate.send(destination, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(item.getId()+"");
					//注意发送消息的时候可能事务还没有提交所以数据库没有这个对象
					return textMessage;
				}});
			
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public E3Result deleteByUpdateStatus(Long[] ids) {
		
		return changeItemStatus(ids, (byte)3);
	}
	

	@Override
	public E3Result updateItemStatusTOInstock(Long[] ids) {
		return changeItemStatus(ids, (byte)2);
	}
	@Override
	public E3Result updateItemStatusTOreshelf(Long[] ids) {
		return changeItemStatus(ids, (byte)1);
	}

	/**
	 * 改变指定商品的status
	 * @param ids
	 * @param status
	 * @return
	 */
	public E3Result changeItemStatus(final Long ids[],byte status) {
		TbItem tbItem=new TbItem();
		try {
			
			for ( final Long id : ids) {
				tbItem.setId(id);
				tbItem.setStatus(status);
				tbItem.setUpdated(new Date());
				tbItemMapper.updateByPrimaryKeySelective(tbItem);
				jmsTemplate.send(destination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(id+"");
						//注意发送消息的时候可能事务还没有提交所以数据库没有这个对象
						return textMessage;
					}});
			}
			
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public TbItem getItemDetailsById(Long id) {
		try {
			String json = jedisClient.get(itemInfoDetils+":"+id+":info");
			if(StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andIdEqualTo(id);
		criteria.andStatusEqualTo((byte)1);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if (list != null && list.size() > 0)
		{
		jedisClient.set(itemInfoDetils+":"+id+":info", JsonUtils.objectToJson(list.get(0)));
		jedisClient.expire(itemInfoDetils+":"+id+":info", Integer.valueOf(expireTime));
			return list.get(0);
	}
		return null;
	}

}
