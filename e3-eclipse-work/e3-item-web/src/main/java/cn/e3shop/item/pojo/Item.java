package cn.e3shop.item.pojo;

import java.util.Date;
import java.util.List;

import cn.e3shop.manager.pojo.TbItem;

public class Item extends TbItem {

	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
	    this.setSellPoint(tbItem.getSellPoint());
	    this.setPrice(tbItem.getPrice());
	    this.setNum(tbItem.getNum());
	    this.setImage(tbItem.getImage());
	    this.setCid(tbItem.getCid());
		
	}
	public String[] getImages() {
		String image= this.getImage();
		String[] images = image.split(",");
		return images;
	}
}
