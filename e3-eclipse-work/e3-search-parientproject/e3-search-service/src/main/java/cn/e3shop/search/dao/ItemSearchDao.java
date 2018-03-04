package cn.e3shop.search.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import cn.geek.e3shop.common.pojo.SearchItems;
import cn.geek.e3shop.common.pojo.SearchResult;
@Repository
public class ItemSearchDao {
@Resource
private SolrServer solrService;

public SearchResult getSearchResult(SolrQuery query) throws SolrServerException {
	SearchResult searchResult=new SearchResult();
	QueryResponse response = solrService.query(query);
	SolrDocumentList results = response.getResults();
	Map<String, Map<String, List<String>>> maps = response.getHighlighting();
	searchResult.setRecourdCount(results.getNumFound());
	for (SolrDocument document : results) {
		SearchItems item=new SearchItems();
		String id = (String) document.get("id");
		item.setId(id);
		String categoryName=(String) document.get("item_category_name");
		item.setCategoryName(categoryName);
		String image=(String) document.get("item_image");
		item.setImage(image);
		int num=(int) document.get("item_num");
		item.setNum(num);
		String sell_point=(String) document.get("item_sell_point");
		item.setSellPoint(sell_point);
		Long price=(Long) document.get("item_price");
		item.setPrice(price);
		List<String> list = maps.get(id).get("item_title");
		String title;
		if(list!=null){
			title=list.get(0);
			item.setTitle(title);
		}else {
		title=(String) document.get("title");
		item.setTitle(title);
		}
		searchResult.getItemList().add(item);
	}
	return searchResult;
}
		
}
