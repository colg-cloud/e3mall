package cn.e3mall.search.service;

import org.apache.solr.client.solrj.SolrQuery;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.solr.SearchResult;

/**
 * 
 *
 * @author colg
 */
public interface ItemSearchService {
    
    /**
     * 一键导入所有商品数据到Solr索引库
     *
     * @return
     */
    E3Result importAllItem();
    
    /**
     * 根据查询条件, 查询Solr服务器
     *
     * @param solrQuery
     * @return
     */
    SearchResult search(SolrQuery solrQuery);
    
    /**
     * 根据关键字, 分页查询Solr服务器
     *
     * @param keyword 关键字
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    SearchResult search(String keyword, Integer page, Integer rows);
    
    /**
     * 根据商品id, 添加到索引库
     *
     * @param itemId
     * @return
     */
    E3Result addDocument(Long itemId);

}
