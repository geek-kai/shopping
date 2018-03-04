package cn.e3shop.search.message;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import cn.e3shop.search.mapper.ItemMapper;
import cn.geek.e3shop.common.pojo.SearchItems;
/**
 * 当添加商品或更新商品的时候同步到solr库
 * @author geek_kai
 *
 */
public class SynchronizeItemChangeTosolr implements MessageListener {

	@Resource
	private ItemMapper itemMapper;
	@Resource
	private SolrServer solrServer;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage) message;
		try {
			//因为消息过来数据还没进数据库所以要等待一会
			Thread.sleep(1000);
			String text = textMessage.getText();
			Long itemId=new Long(text);
			SearchItems item = itemMapper.findItemById(itemId);
			if(item!=null) {
			SolrInputDocument document=new SolrInputDocument();
			document.addField("item_title", item.getTitle());
			document.addField("item_category_name", item.getCategoryName());
			document.addField("item_image", item.getImage());
			document.addField("item_price", item.getPrice());
			document.addField("item_num", item.getNum());
			document.addField("item_sell_point", item.getSellPoint());
			document.addField("id", item.getId());
			solrServer.add(document);
			solrServer.commit();
			}else {
				solrServer.deleteById(text);
				solrServer.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
