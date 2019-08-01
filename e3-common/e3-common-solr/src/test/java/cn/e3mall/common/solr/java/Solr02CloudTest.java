package cn.e3mall.common.solr.java;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * Solr 测试
 *
 * @author colg
 */
@Slf4j
public class Solr02CloudTest {

    /**
     * zkHost: zookeeper的地址列表
     *
     * <pre>
     *     格式为 host:port 逗号分隔
     * </pre>
     */
    private static final String ZK_HOST = "192.168.21.103:2181,192.168.21.103:2182,192.168.21.103:2183";

    /**
     * 查询集群索引库
     */
    @Test
    public void testSolrQueryCommon() throws SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer(ZK_HOST);
        solrServer.setDefaultCollection("collection2");

        // 查询条件
        SolrQuery solrQuery = new SolrQuery("item_title:手机");
        // 执行查询
        QueryResponse queryResponse = solrServer.query(solrQuery);
        // 查询结果
        SolrDocumentList documentList = queryResponse.getResults();

        log.info("当前记录数: {}", documentList.size());
        log.info("查询结果总记录数: {}", documentList.getNumFound());
        log.info("查询的结果集: \n{}", JSON.toJSONString(documentList, true));
    }

}
