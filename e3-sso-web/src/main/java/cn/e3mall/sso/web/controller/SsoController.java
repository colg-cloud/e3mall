package cn.e3mall.sso.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员系统
 *
 * @author colg
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

    /**
     * 页面跳转 - 注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }
    
    /**
     * 页面跳转 - 登录页面; 实现单点登录系统的页面回调
     *
     * @param redirect 页面传递来的url
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String showLogin(@RequestParam(defaultValue = "") String redirect, Model model) {
        // 登录成功后, 判断页面传递的url, 返回页面传递的url
        model.addAttribute(redirect, redirect);
        return "login";
    }
}
