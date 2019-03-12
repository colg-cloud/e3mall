package cn.e3mall.search.service.core;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.dao.*;

/**
 * BaseServiceImpl 用于抽取注入的Mapper
 *
 * @author colg
 */
public abstract class BaseServiceImpl {

    @Autowired
    protected SolrServer solrServer;

    @Autowired
    protected ItemMapper itemMapper;
}
