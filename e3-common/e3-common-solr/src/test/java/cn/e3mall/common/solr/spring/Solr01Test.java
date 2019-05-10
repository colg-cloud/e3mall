package cn.e3mall.common.solr.spring;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Solr 测试
 *
 * @author colg
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/solr-01.xml")
public class Solr01Test {

    @Autowired
    private SolrServer solrServer;

    /**
     * 向索引库添加文档
     *
     * @throws Exception
     */
    @Test
    public void testAddDocument() throws Exception {
        // 创建一个文档对象
        SolrInputDocument doc = new SolrInputDocument();
        // 向文档对象中添加域, 文档中必须包含一个id域, 所有域的名称必须在schema.xml中定义
        doc.addField("id", IdUtil.fastSimpleUUID());
        doc.addField("item_title", "solr" + RandomUtil.randomInt(1000));
        doc.addField("item_price", RandomUtil.randomInt(100, 10000) * 100);
        // 写入索引库: 如果当前id已存在, 则会更新该文档
        solrServer.add(doc);
        // 提交
        solrServer.commit();
    }

    /**
     * 从索引库中删除文档
     *
     * @throws Exception
     */
    @Test
    public void testDeleteDocument() throws Exception {
        // 1. 根据id删除, 插入的是
        // solrServer.deleteById(id);
        // 2. 根据查询结果
        solrServer.deleteByQuery("item_title:solr");
        solrServer.commit();
    }

    /**
     * 查询索引库 - 简单查询(默认查询10条记录)
     *
     * @throws Exception
     */
    @Test
    public void testSolrQueryCommon() throws Exception {
        // 查询条件
        SolrQuery solrQuery = new SolrQuery("item_title:手机");
        // 执行查询
        QueryResponse queryResponse = solrServer.query(solrQuery);
        // 查询结果
        SolrDocumentList documentList = queryResponse.getResults();

        log.info("当前记录数: {}", documentList.size());
        log.info("查询结果总记录数: {}", documentList.getNumFound());
        log.info("查询的结果集: {}", JSON.toJSONString(documentList));
    }

    /**
     * 查询索引库 - 复杂查询
     *
     * @throws Exception
     */
    @Test
    public void testSolrQuery() throws Exception {
        // 查询
        SolrQuery solrQuery = new SolrQuery();
        // 搜索域
        solrQuery.set("df", "item_title");
        // 条件
        solrQuery.setQuery("手机");
        // 分页
        solrQuery.setStart(0).setRows(10);
        // 高示; 高亮字段; 高亮样式/标签
        solrQuery.setHighlight(true).setHighlightSimplePre("<em>").setHighlightSimplePost("</em>");

        // 执行查询
        QueryResponse queryResponse = solrServer.query(solrQuery);

        // 查询结果
        SolrDocumentList documentList = queryResponse.getResults();

        log.info("当前记录数: {}", documentList.size());
        log.info("查询结果总记录数: {}", documentList.getNumFound());
        log.info("查询的结果集: {}", JSON.toJSONString(documentList));

        // 高亮结果
        Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
        log.info("高亮结果集: {}", JSON.toJSONString(map));
    }
}
