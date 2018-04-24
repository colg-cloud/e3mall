package cn.e3mal.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 生成静态页面测试Controller
 *
 * @author colg
 */
@RestController
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;

	@GetMapping("/genhtml")
	public String genHtml() {
		Configuration configuration = freeMarkerConfig.getConfiguration();
		// 加载模版对象
		try {
			Template template = configuration.getTemplate("hello.ftl");
			// 创建一个数据集
			Map<String, Object> map = new HashMap<>(1);
			map.put("hello", 123456);
			// 指定文件输出路径以及文件名
			Writer out = new FileWriter(new File("hello2.html"));
			// 输出文件
			template.process(map, out);
			// 关闭流
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return "OK";
	}
}
