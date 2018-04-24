package cn.e3mall.search.solrj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * solrj 测试
 *
 * @author colg
 */
public class SolrTest {

	/**
	 * Solr服务器的URL。
	 * 
	 * 例如，如果您在本地计算机上使用标准分发版Solr Web应用程序，则为"http//127.0.0.18983/solr/"。
	 * 
	 * 默认连接 collection1，可指定
	 * 
	 */
	private static final String BASE_URL = "http://192.168.21.103:8080/solr/collection1";

	/**
	 * 向索引库添加文档
	 * 
	 */
	@Test
	public void addDocument() throws SolrServerException, IOException {
		// 创建一个SolrServer对象，创建一个连接。参数：sorl服务的url
		SolrServer solrServer = new HttpSolrServer(BASE_URL);
		// 创建一个文档对象SolrInputDocument
		SolrInputDocument doc = new SolrInputDocument();
		// 向文档对象中添加域，文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义
		doc.addField("id", "doc01");
		doc.addField("item_title", "测试商品01");
		doc.addField("item_price", 1000);
		// 把文档写入索引库
		solrServer.add(doc);
		// 提交
		solrServer.commit();
	}

	/**
	 * 删除文档
	 */
	@Test
	public void deleteDocument() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer(BASE_URL);
		// 根据id删除
		// solrServer.deleteById("doc01");
		// 根据查询删除
		solrServer.deleteByQuery("id:doc01");
		solrServer.commit();
	}

	/**
	 * 查询索引库 - 简单
	 */
	@Test
	public void queryIndex() throws SolrServerException {
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(BASE_URL);
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 执行查询QueryResponse对象
		QueryResponse queryResponse = solrServer.query(query);
		// 取文档列表，取查询结果的总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		System.out.println("当前记录数：" + solrDocumentList.size());
		// 遍历文档列表，取域的内容
		/*
		for (SolrDocument doc : solrDocumentList) {
			doc.get("id");
			doc.get("item_title");
			doc.get("item_title");
			doc.get("item_sell_point");
			doc.get("item_price");
			doc.get("item_image");
			doc.get("item_category_name");
		}
		*/
		System.out.println(JSON.toJSONString(solrDocumentList));
	}

	/**
	 * 查询索引库 - 复杂
	 * 
	 * @throws SolrServerException
	 */
	@Test
	public void queryIndexFuza() throws SolrServerException {
		SolrServer solrServer = new HttpSolrServer(BASE_URL);
		SolrQuery query = new SolrQuery();
		// 查询条件
		query.setStart(0).setRows(10).setQuery("手机");
		// 默认搜索域
		query.set("df", "item_title");
		// 高亮显示
		query.setHighlight(true);
		// 高亮显示字段
		query.addHighlightField("item_title");
		// 高亮显示标签
		query.setHighlightSimplePre("<em>").setHighlightSimplePost("</em>");

		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);

		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println(JSON.toJSONString(solrDocumentList));

		// 高亮结果
		Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
		System.out.println(JSON.toJSON(map));

	}
}
