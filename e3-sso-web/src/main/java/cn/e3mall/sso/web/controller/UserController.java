package cn.e3mall.sso.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.entity.User;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.util.CookieUtil;
import cn.e3mall.sso.web.core.BaseController;

/**
 * 用户管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/sso/user")
public class UserController extends BaseController {
    
    /** token 在 redis 中缓存的key */
    @Value("${token_key}")
    private String tokenKey;

    /**
     * 注册, 数据校验
     *
     * @param param 参数
     * @param type 参数类型 - 1: 用户名; 2: 手机; 3: 邮箱
     * @return
     */
    @GetMapping("/check/{param}/{type}")
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
        return userService.checkData(param, type);
    }
    
    /**
     * 注册, 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public E3Result register(User user) {
        return userService.addUser(user);
    }
    
    /**
     * 登录; 登录成功呢后, 把token写入cookie
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        E3Result e3Result = userService.login(username, password);
        // 判断是否登录成功, 登录成功则把token写入cookie
        if (e3Result.getStatus().equals(E3Result.OK)) {
            String token =  (String)e3Result.getData();
            CookieUtil.setCookie(request, response, tokenKey, token);
        }
        return e3Result;
    }
}
