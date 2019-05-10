package cn.e3mall.common.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * Solr 测试
 *
 * @author colg
 */
@Slf4j
public class Solr01Test {

    /**
     * Solr服务器的URL
     *
     * <pre>
     *  例如: 如果在本地计算机上使用标准分发版Solr Web应用程序, 则为"http//127.0.0.18983/solr/"
     *
     *  默认连接: collection1; 可省略, 可指定
     * </pre>
     */
    public static final String BASE_URL = "http://192.168.21.103:8080/solr/collection1";

    @Test
    public void testSolrQueryCommon() throws Exception {
        // 创建一个SolrServer对象,
        SolrServer solrServer = new HttpSolrServer(BASE_URL);

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
}
