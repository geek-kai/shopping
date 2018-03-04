package cn.geek.e3shop.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResult implements Serializable {

	public Integer totalPages;
	
	public Long recourdCount;
	
	
	public List itemList=new ArrayList<>();

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getRecourdCount() {
		return recourdCount;
	}

	public void setRecourdCount(Long recourdCount) {
		this.recourdCount = recourdCount;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
	
	
	
}
