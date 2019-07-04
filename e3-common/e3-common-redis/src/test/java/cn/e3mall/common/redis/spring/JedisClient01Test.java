package cn.e3mall.common.redis.spring;

import cn.e3mall.common.redis.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.e3mall.common.redis.JedisClient;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * JedisClient 测试
 *
 * @author colg
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/redis-01.xml")
public class JedisClient01Test extends BaseTest {

    private static final String KEY = "e3-redis";

    @Autowired
    private JedisClient jedisClient;

    /**
     * Test method for {@link cn.e3mall.common.redis.JedisClient#del(java.lang.String[])}.
     */
    @Test
    public final void testDel() {
        Long result = jedisClient.del(KEY);
        log.info("result: {}", result);
    }

    /**
     * Test method for {@link cn.e3mall.common.redis.JedisClient#get(java.lang.String)}.
     */
    @Test
    public final void testGet() {
        String result = jedisClient.get(KEY);
        log.info("result: {}", result);
    }

    /**
     * Test method for {@link cn.e3mall.common.redis.JedisClient#set(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testSet() {
        String result = jedisClient.set(KEY, "存入时间: " + DateUtil.now());
        jedisClient.expire(KEY, 3600);
        log.info("result: {}", result);
    }

}
