package cn.e3mall.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis generator 逆向工程
 *
 * @author colg
 */
public class GeneratorTest {

    /**
     * Project Base Path
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 配置文件路径
     */
    private static final String GENERATOR_XML_PATH = "/src/main/resources/conf/generatorInv.xml";

    @Test
    public void testGenerator() throws Exception {
        List<String> warnings = new ArrayList<>();
        File configFile = new File(PROJECT_PATH + GENERATOR_XML_PATH);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
