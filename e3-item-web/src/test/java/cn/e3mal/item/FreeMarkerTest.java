package cn.e3mal.item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freeMarker 测试类
 *
 * @author colg
 */
public class FreeMarkerTest {

	@Test
	public void testFreeMarker() throws IOException, TemplateException {
		// 1. 创建一个模版文件
		// 2. 创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 3. 设置模版文件保存的目录
		File dir = new File("D:\\spring-tool-suite-3.7.3\\sts-3.7.3-workspace\\e3\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl");
		configuration.setDirectoryForTemplateLoading(dir);
		// 4. 加载一个模版文件,创建一个模版对象
		Template template = configuration.getTemplate("hello.ftl");
		// 5. 创建一个数据集,可以是pojo也可以是map,推荐使用map
		Map<String, Object> data = new HashMap<>(1);
		data.put("hello", "hello freemarker!");
		// 6. 创建一个Writer对象,指定输出文件的路径及文件名
		Writer out = new FileWriter("html/hello.txt");
		// 7. 生成静态页面
		template.process(data, out);
		// 8. 关闭流
		out.close();

	}

}
