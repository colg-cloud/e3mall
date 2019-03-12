package cn.e3mall.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.solr.ItemSearch;
import cn.e3mall.common.solr.SearchResult;
import cn.e3mall.search.service.ItemSearchService;
import cn.e3mall.search.service.core.BaseServiceImpl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author colg
 */
@Slf4j
@Service
public class ItemSearchServiceImpl extends BaseServiceImpl implements ItemSearchService {
    
    @Override
    public E3Result importAllItem() {
        long preTime = System.currentTimeMillis();
        log.info("一键导入所有商品数据到Solr索引库, start......");
        // 查询所有商品列表
        List<ItemSearch> itemSearchList = itemMapper.selectItemSearchList();
        // 添加文档
        List<SolrInputDocument> docList = new ArrayList<>();
        itemSearchList.forEach(itemSearch -> {
            // 创建文档对象
            SolrInputDocument doc = new SolrInputDocument();
            // 向文档对象中添加域
            doc.addField("id", itemSearch.getId());
            doc.addField("item_title", itemSearch.getTitle());
            doc.addField("item_sell_point", itemSearch.getSellPoint());
            doc.addField("item_price", itemSearch.getPrice());
            doc.addField("item_image", itemSearch.getImage());
            doc.addField("item_category_name", itemSearch.getCategoryName());
            docList.add(doc);
        });
        try {
            // 把文档对象写入索引库
            solrServer.add(docList);
            // 提交
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return E3Result.fail("SolrServer 通信/解析异常!");
        } catch (IOException e) {
            e.printStackTrace();
            return E3Result.fail("SolrServer I/O异常!");
        }
        log.info("一键导入所有商品数据到Solr索引库, 结束...... 耗时: {}ms", DateUtil.spendMs(preTime));
        return E3Result.ok();
    }
    
    @Override
    public SearchResult search(SolrQuery solrQuery) {
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrServer.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
            log.error("SolrServer 通信/解析异常!");
        }
        
        // 获取查询结果
        SolrDocumentList documentList = queryResponse.getResults();
        // 获取查询结果总记录数
        long recourdCount = documentList.getNumFound();
        // 获取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        
        // 封装商品信息
        List<ItemSearch> itemSearchs = new ArrayList<>();
        documentList.forEach(doc -> {
            String id = (String)doc.get("id");
            ItemSearch itemSearch = new ItemSearch();
            itemSearch.setId(id)
                      .setSellPoint((String) doc.get("item_sell_point"))
                      .setPrice((Long) doc.get("item_price"))
                      .setImage((String) doc.get("item_image"))
                      .setCategoryName((String) doc.get("item_category_name"));
            // 高亮标题
            List<String> list = highlighting.get(id).get("item_title");
            if (CollUtil.isEmpty(list)) {
                itemSearch.setTitle((String) doc.get("item_title"));
            } else {
                itemSearch.setTitle(list.get(0));
            }
            itemSearchs.add(itemSearch);
        });
        
        // 封装到SearchResult
        return SearchResult.ok(recourdCount, itemSearchs);
    }

    @Override
    public SearchResult search(String keyword, Integer page, Integer rows) {
        // 计算起始行
        Integer start = ((page <= 0 ? 1 : page) - 1) * rows;
        
        // 设置查询条件
        SolrQuery query = new SolrQuery();
        // 查询条件
        query.setQuery(keyword)
             // 分页条件
             .setStart(start).setRows(rows)
             // 高亮显示
             .addHighlightField("item_title").setHighlightSimplePre("<em style=\"color: red;\">").setHighlightSimplePost("</em>")
             // 搜索域
             .set("df", "item_title");
             
        // 执行查询
        SearchResult searchResult = this.search(query);
        // 计算总页数
        Integer totalPages = (int)((searchResult.getRecourdCount() + rows - 1) / rows);
        searchResult.setTotalPages(totalPages);
        return searchResult;
    }

    @Override
    public E3Result addDocument(Long itemId) {
        // 根据商品id查询商品信息
        ItemSearch itemSearch = itemMapper.findItemSearchById(itemId);
        if (itemSearch != null) {
            // 创建一个文档对象
            SolrInputDocument doc = new SolrInputDocument();
            // 向文档对象中添加域
            doc.addField("id", itemSearch.getId());
            doc.addField("item_title", itemSearch.getTitle());
            doc.addField("item_sell_point", itemSearch.getSellPoint());
            doc.addField("item_price", itemSearch.getPrice());
            doc.addField("item_image", itemSearch.getImage());
            doc.addField("item_category_name", itemSearch.getCategoryName());
            try {
                // 把文档对象写入索引库
                solrServer.add(doc);
                // 提交
                solrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
                return E3Result.fail("SolrServer 通信/解析异常!");
            } catch (IOException e) {
                e.printStackTrace();
                return E3Result.fail("SolrServer I/O异常!");
            }
        }
        return E3Result.ok();
    }

}
