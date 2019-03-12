package cn.e3mall.search.web.core;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.*;

/**
 * BaseController 用于抽取注入的Service
 *
 * @author colg
 */
public class BaseController {

    @Autowired
    protected ItemSearchService itemSearchService;
}
