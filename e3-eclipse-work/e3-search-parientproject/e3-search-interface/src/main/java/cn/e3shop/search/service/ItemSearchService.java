package cn.e3shop.search.service;
import cn.geek.e3shop.common.pojo.SearchResult;
import cn.geek.e3shop.common.utils.E3Result;

public interface ItemSearchService {

	public SearchResult findSearchResultByKeywords(String keywords,Integer page,Integer pageNum);
	
	public E3Result insertItemsToSolr();
}
