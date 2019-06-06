package cn.e3mall.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisClient 单机
 *
 * @author colg
 */
@Slf4j
public class JedisPoolCache implements JedisClient {

    @Getter
    @Setter
    private JedisPool jedisPool = initJedisPool();

    /**
     * 初始化 JedisPool 默认配置
     *
     * @return JedisPool
     */
    private JedisPool initJedisPool() {
        Properties prop = Props.getProp("conf/redis.properties", CharsetUtil.CHARSET_UTF_8);
        String host = prop.getProperty("redis.host");
        int port = Integer.parseInt(prop.getProperty("redis.port"));
        long maxWaitMillis = Long.parseLong(prop.getProperty("redis.pool.max-wait"));
        int minIdle = Integer.parseInt(prop.getProperty("redis.pool.min-idle"));
        int maxIdle = Integer.parseInt(prop.getProperty("redis.pool.max-idle"));
        int maxTotal = Integer.parseInt(prop.getProperty("redis.pool.max-active"));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        log.info("Redis 单机: {}", host + ":" + port);
        return new JedisPool(config, host, port);
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    @Override
    public Long del(String... keys) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(keys);
        jedis.close();
        return result;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, seconds);
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.keys(pattern);
        jedis.close();
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public Boolean hExists(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.hexists(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hDel(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, fields);
        jedis.close();
        return result;
    }

    @Override
    public String hGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hSet(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> result = jedis.hgetAll(key);
        jedis.close();
        return result;
    }

    @Override
    public Set<String> hKeys(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.hkeys(key);
        jedis.close();
        return result;
    }

    @Override
    public List<String> hVals(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> result = jedis.hvals(key);
        jedis.close();
        return result;
    }

}
