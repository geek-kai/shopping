package cn.e3shop.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3shop.manager.pojo.TbItem;
import cn.e3shop.search.dao.ItemSearchDao;
import cn.e3shop.search.mapper.ItemMapper;
import cn.e3shop.search.service.ItemSearchService;
import cn.geek.e3shop.common.pojo.SearchItems;
import cn.geek.e3shop.common.pojo.SearchResult;
import cn.geek.e3shop.common.utils.E3Result;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Resource
	private ItemMapper itemMapper;
	@Value(value = "${defaultSearchFiled}")
	private String defaultSearchFiled;
	@Resource
	private ItemSearchDao itemSearchDao;
	@Resource
	private SolrServer solrServer;
	
	public SearchResult findSearchResultByKeywords(String requirement,Integer page,Integer pageNum) {
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(requirement);
			query.set("df", defaultSearchFiled);
			query.setHighlight(true);
			query.addHighlightField("item_title");
			query.setHighlightSimplePre("<span style='color:red'>");
			query.setHighlightSimplePost("</span>");
			int statrt=(page-1)*pageNum;
			query.setStart(statrt);
			query.setRows(pageNum);
			SearchResult searchResult = itemSearchDao.getSearchResult(query);
			Integer recourdCount = searchResult.getRecourdCount().intValue();
			searchResult.setTotalPages(recourdCount/pageNum);
			Integer totalPages = searchResult.getTotalPages();
			if(totalPages%pageNum!=0) {
				totalPages++;
				searchResult.setTotalPages(totalPages);
			}
			return searchResult;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public E3Result insertItemsToSolr() {
		List<SearchItems> items = itemMapper.getAllItem();
		try {
			for (SearchItems item : items) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("item_title", item.getTitle());
				document.addField("item_category_name", item.getCategoryName());
				document.addField("item_image", item.getImage());
				document.addField("item_price", item.getPrice());
				document.addField("item_num", item.getNum());
				document.addField("item_sell_point", item.getSellPoint());
				document.addField("id", item.getId());
				solrServer.add(document);
				solrServer.commit();
			}
			return E3Result.ok();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return E3Result.build(500, "创建索引失败");
		}
	}

}
