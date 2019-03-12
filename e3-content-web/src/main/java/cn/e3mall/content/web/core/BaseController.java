package cn.e3mall.content.web.core;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.content.service.*;

/**
 * BaseController 用于抽取注入的Service
 *
 * @author colg
 */
public class BaseController {

    @Autowired
    protected ContentService contentService;
}
