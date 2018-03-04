package cn.geek.e3shop.common.pojo;

import java.io.Serializable;

public class SearchItems implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryName;
	private String id;
	private String title;
	private long price;
	private String sellPoint;
	private Integer num;
	private String image;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String[] getImages() {
		return image.split(",");
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	
}
