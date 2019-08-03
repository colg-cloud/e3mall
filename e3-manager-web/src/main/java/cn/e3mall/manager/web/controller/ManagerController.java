package cn.e3mall.manager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;

/**
 * 后台管理系统路由控制
 *
 * @author colg
 */
@Controller
public class ManagerController {

    /**
     * 页面跳转
     *
     * @param view 页面路径
     * @return
     */
    @GetMapping("/{view}")
    public String showView(@PathVariable String view) {
        return view;
    }

    /**
     * 页面跳转
     *
     * @param dir  目录
     * @param view 页面路径
     * @return
     */
    @GetMapping("/{dir}/{view}")
    public String showView(@PathVariable String dir, @PathVariable String view) {
        return dir + File.separatorChar + view;
    }

}
