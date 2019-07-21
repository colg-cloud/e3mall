package cn.e3mall.common.freemarker.java;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.e3mall.common.freemarker.BaseTest;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.junit.Test;

import cn.e3mall.common.freemarker.model.Student;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * freemarker 测试
 *
 * @author colg
 */
@Slf4j
public class FreemarkerTest extends BaseTest {

    /**
     * 项目基础路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    @Test
    public void testFreemarkerText() throws Exception {
        // 1. 创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2. 设置模版文件目录
        String pathname = PROJECT_PATH + "\\src\\test\\resources\\template";
        configuration.setDirectoryForTemplateLoading(new File(pathname));
        // 3. 创建一个数据集
        Dict data = Dict.create()
                        .set("hello", "hello freemarker!");
        // 4. 创建一个Writer对象, 指定输出文件的路径及文件名
        String fileName = PROJECT_PATH + "\\src\\test\\resources\\txt\\hello.txt";
        Writer out = new FileWriter(fileName);
        // 5. 加载一个模版文件, 生成静态文件
        configuration.getTemplate("hello.ftl")
                     .process(data, out);
        // 6. 关闭流
        out.close();

        log.info("模版路径: {}", pathname + "\\hello.ftl");
        log.info("输出路径: {}", fileName);
    }

    /**
     * 测试生成静态页面
     *
     * @throws Exception
     */
    @Test
    public void testFreemarkerHtml() throws Exception {
        // 1. 创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2. 设置模版文件目录
        String pathname = PROJECT_PATH + "\\src\\test\\resources\\template";
        configuration.setDirectoryForTemplateLoading(new File(pathname));
        // 3. 创建一个数据集
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "Tom", 20, "南美"));
        list.add(new Student(2, "Jack", 18, "美国"));
        list.add(new Student(3, "Rose", 22, null));
        Dict data = Dict.create()
                        .set("stuList", list)
                        .set("student", CollUtil.getFirst(list))
                        .set("date", new Date())
                        .set("val", null)
                        .set("hello", "hello freemarker! - html");
        // 4. 创建一个Writer对象, 指定输出文件的路径及文件名
        String fileName = PROJECT_PATH + "\\src\\test\\resources\\html\\student.html";
        Writer out = new FileWriter(fileName);
        // 5. 加载一个模版文件, 生成静态文件
        configuration.getTemplate("student.ftl")
                     .process(data, out);
        // 6. 关闭流
        out.close();

        log.info("模版路径: {}", pathname + "\\student.ftl");
        log.info("输出路径: {}", fileName);
    }

}
