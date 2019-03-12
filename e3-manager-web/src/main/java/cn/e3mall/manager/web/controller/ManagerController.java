package cn.e3mall.manager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理系统
 *
 * @author colg
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

	/**
	 * 页面跳转
	 *
	 * @param page
	 * @return
	 */
	@GetMapping("/{view}")
	public String showView(@PathVariable String view) {
		return view;
	}
	
}
