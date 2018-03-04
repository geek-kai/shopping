package cn.e3shop.search.mapper;

import java.util.List;

import cn.geek.e3shop.common.pojo.SearchItems;

public interface ItemMapper {

	public List<SearchItems> getAllItem();
	
	public SearchItems findItemById(Long itemId);
}
