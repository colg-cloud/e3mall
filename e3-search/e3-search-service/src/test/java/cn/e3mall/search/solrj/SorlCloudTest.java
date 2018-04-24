package cn.e3mall.search.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * solrj 集群测试
 *
 * @author colg
 */
public class SorlCloudTest {

	/**
	 * zkHost： zookeeper的地址列表
	 * 
	 * 格式为HOST：PORT。逗号分隔
	 * 
	 */
	private static final String ZK_HOST = "192.168.21.103:2181,192.168.21.103:2182,192.168.21.103:2183";

	/**
	 * 向集群索引库添加文档
	 */
	@Test
	public void addDocument() throws SolrServerException, IOException {
		// 创建一个集群的连接，应该使用CluodSolrServer创建
		CloudSolrServer solrServer = new CloudSolrServer(ZK_HOST);
		// 设置一个defaultCollection属性
		solrServer.setDefaultCollection("collection2");
		// 创建一个文档对象
		SolrInputDocument doc = new SolrInputDocument();
		// 向文档中添加域
		doc.setField("id", "doc01");
		doc.addField("item_title", "测试商品01");
		doc.addField("item_price", 1000);
		// 把文件写入索引库
		solrServer.add(doc);
		// 提交
		solrServer.commit();
	}

	/**
	 * 从集群索引库里删除文档
	 */
	@Test
	public void deleteDocument() throws SolrServerException, IOException {
		CloudSolrServer solrServer = new CloudSolrServer(ZK_HOST);
		solrServer.setDefaultCollection("collection2");
		solrServer.deleteByQuery("id:doc01");
		solrServer.commit();
	}

	/**
	 * 查询集群索引库
	 */
	@Test
	public void queryIndex() throws SolrServerException {
		CloudSolrServer solrServer = new CloudSolrServer(ZK_HOST);
		solrServer.setDefaultCollection("collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		System.out.println("当前记录数：" + solrDocumentList.size());
		System.out.println(JSON.toJSONString(solrDocumentList));
	}
}
