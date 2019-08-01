package cn.e3mall.common.fastdfs;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;

/**
 * 基础测试类
 *
 * @author colg
 */
@Slf4j
public abstract class BaseTest {

    private long time;

    /**
     * 项目基础路径
     */
    protected static final String PROJECT_PATH = System.getProperty("user.dir");

    @Before
    public void setUp() {
        time = System.currentTimeMillis();
    }

    @After
    public void tearDown() {
        log.info("Junit: [{}ms]", DateUtil.spendMs(time));
        log.info("----------------------------------------------------------------------------------------------------");
    }

}
