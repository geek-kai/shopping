package cn.e3shop.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3shop.item.pojo.Item;
import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.manager.pojo.TbItemDesc;
import cn.e3shop.manager.service.ItemDescService;
import cn.e3shop.manager.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 当后台添加商品的时候对新增商品创建静态化页面
 * @author geek_kai
 *
 */
public class CreateStaticHtmlPageMessageListener implements MessageListener {

	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource
	private ItemService itemService; 
	@Resource
	private ItemDescService itemDescService;
	@Value("${outPath}")
	private String outPath;
	@Override
	public void onMessage(Message message) {
		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			TextMessage textMessage=(TextMessage) message;
			String text = textMessage.getText();
			Long ItemId=new Long(text);
			Thread.sleep(100);
			TbItem tbItem = itemService.findByItemId(ItemId);
			if(tbItem!=null) {
			Map<String,Object>data=new HashMap<>();
			Item item=new Item(tbItem);
			data.put("item", item);
			TbItemDesc itemDesc = itemDescService.findItemDescByItemId(ItemId);
			data.put("itemDesc", itemDesc);
			Template template = configuration.getTemplate("item.ftl");
			Writer out=new FileWriter(new File(outPath+"/"+item.getId()+".html"));
			template.process(data, out);
			out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
