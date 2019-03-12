package cn.e3mall.common.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * JedisClient 集群
 *
 * @author colg
 */
@Slf4j
public class JedisClusterCache implements JedisClient {

    @Getter
    @Setter
    private JedisCluster jedisCluster = initJedisCluster();

    /**
     * 初始化 JedisCluster, 默认配置
     *
     * @return
     */
    private JedisCluster initJedisCluster() {
        Properties prop = Props.getProp("conf/redis.properties");
        String clusterNodes = prop.getProperty("redis.cluster.nodes");
        Set<HostAndPort> nodes = new HashSet<>(9);
        StrUtil.split(clusterNodes, ',').forEach(clusterNode -> {
            List<String> node = StrUtil.split(clusterNode, ':');
            nodes.add(new HostAndPort(node.get(0), Integer.parseInt(node.get(1))));
        });
        log.info("redis 集群: {}", clusterNodes);
        return new JedisCluster(nodes);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long del(String... keys) {
        Long delCount = 0L;
        for (String key : keys) {
            delCount += jedisCluster.del(key);
        }
        return delCount;
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        // JedisCluster 没有提供对 keys 命令的封装
        Set<String> result = new HashSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String key : clusterNodes.keySet()) {
            JedisPool jedisPool = clusterNodes.get(key);
            Jedis jedis = jedisPool.getResource();
            result.addAll(jedis.keys(pattern));
            jedis.close();
        }
        return result;
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Boolean hexists(String key, String field) {
        return jedisCluster.hexists(key, field);
    }

    @Override
    public Long hdel(String key, String... fields) {
        return jedisCluster.hdel(key, fields);
    }

    @Override
    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    @Override
    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key, field, value);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return jedisCluster.hgetAll(key);
    }

    @Override
    public Set<String> hkeys(String key) {
        return jedisCluster.hkeys(key);
    }

    @Override
    public List<String> hvals(String key) {
        return jedisCluster.hvals(key);
    }

}
