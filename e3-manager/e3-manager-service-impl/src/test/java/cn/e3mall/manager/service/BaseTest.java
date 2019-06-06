package cn.e3mall.manager.service;

import org.junit.After;
import org.junit.Before;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础测试类
 *
 * @author colg
 */
@Slf4j
public abstract class BaseTest {

    private long time;

    @Before
    public void before() {
        time = System.currentTimeMillis();
    }

    @After
    public void after() {
        log.info("Junit: [{}ms]", DateUtil.spendMs(time));
        log.info("----------------------------------------------------------------------------------------------------");
    }
}
