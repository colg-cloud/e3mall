package cn.e3mall.sso.web.core;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.sso.service.*;

/**
 * BaseController 用于抽取注入的Service
 *
 * @author colg
 */
public class BaseController {
    
    @Autowired
    protected UserService userService;
}
