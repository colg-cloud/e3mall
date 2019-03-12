package cn.e3mall.item.web.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cn.e3mall.manager.service.*;

/**
 * BaseController 用于抽取注入的Service
 *
 * @author colg
 */
public class BaseController {
    
    @Autowired
    protected FreeMarkerConfig freeMarkerConfig;

    @Autowired
    protected ItemService itemService;
    @Autowired
    protected ItemDescService itemDescService;
    @Autowired
    protected ItemParamItemService itemParamItemService;
}
