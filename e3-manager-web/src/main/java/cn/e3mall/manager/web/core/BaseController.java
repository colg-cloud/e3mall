package cn.e3mall.manager.web.core;

import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.manager.service.*;
import cn.e3mall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于抽取注入的Service
 *
 * @author colg
 */
public class BaseController {

    @Autowired
    protected ItemService itemService;
    @Autowired
    protected ItemCatService itemCatService;
    @Autowired
    protected ItemDescService itemDescService;
    @Autowired
    protected ItemParamService itemParamService;
    @Autowired
    protected ItemParamItemService itemParamItemService;

    @Autowired
    protected ContentCategoryService contentCategoryService;
    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ItemSearchService itemSearchService;

}
