package cn.e3mall.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * resid 客户端：</br>
 * 
 * 采用策略模式，封装了redis的两种配置方式（单机，集群），可以更方便的切换。
 *
 * @author colg
 */
public interface JedisClient {

    /// ----------------------------------------------------------------------------------------------------

    /// Redis 键(key) 操作

    /**
     * 
     * 检查给定 key 是否存在。
     * 
     * @param key 键
     * @return 如果 key 存在返回 true ，否则返回 false 。
     * @author colg
     */
    Boolean exists(String key);

    /**
     * 删除已存在的 key 。不存在的 key 会被忽略。
     *
     * @param keys 键
     * @return 被删除 key 的数量。
     * @author colg
     */
    Long del(String... keys);

    /**
     * 设置 key 的过期时间。key 过期后将不再可用。服务器自动删除 key 。
     * 
     * @param key 键
     * @param seconds 单位：秒
     * @return 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时返回 0 。
     * @author colg
     */
    Long expire(String key, int seconds);

    /**
     * 以秒为单位返回 key 的剩余过期时间。
     * 
     * @param key 键
     * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key 的剩余生存时间。
     * @author colg
     */
    Long ttl(String key);

    /**
     * 查找所有符合给定模式( pattern )的 key 。
     *
     * @param pattern 给定模式
     * @return 符合给定模式的 key 列表 (Array)。
     * @author colg
     */
    Set<String> keys(String pattern);

    /// ----------------------------------------------------------------------------------------------------

    /// Redis 字符串(String) 操作

    /**
     * 获取指定 key 的值。
     * 
     * @param key 键
     * @return 如果 key 不存在，返回 null 。如果key 储存的值不是字符串类型，返回一个错误。
     * @author colg
     */
    String get(String key);

    /**
     * 设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型。
     * 
     * @param key 键
     * @param value
     * @return 在设置操作成功完成时，返回 "OK" 。
     * @author colg
     */
    String set(String key, String value);

    /**
     * 将 key 中储存的数字值增一。本操作的值限制在 64 位(bit)有符号数字表示之内。
     * 
     * @param key 键
     * @return 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * @author colg
     */
    Long incr(String key);

    /// ----------------------------------------------------------------------------------------------------

    /// Redis 哈希(Hash) 操作

    /**
     * 查看哈希表的指定字段是否存在。
     * 
     * @param key 键
     * @param field
     * @return 如果哈希表含有给定字段，返回 1 。 如果哈希表不含有给定字段，或 key 不存在，返回 0 。
     * @author colg
     */
    Boolean hexists(String key, String field);

    /**
     * 删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
     * 
     * @param key 键
     * @param fields
     * @return 被成功删除字段的数量，不包括被忽略的字段。
     * @author colg
     */
    Long hdel(String key, String... fields);

    /**
     * 获取哈希表中指定字段的值。
     * 
     * @param key 键
     * @param field
     * @return 返回给定字段的值。如果给定的字段或 key 不存在时，返回 null 。
     * @author colg
     */
    String hget(String key, String field);

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。如果字段已经存在于哈希表中，旧值将被覆盖。
     * 
     * @param key 键
     * @param field
     * @param value
     * @return 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0 。
     * @author colg
     */
    Long hset(String key, String field, String value);

    /**
     * 获取在哈希表中指定 key 的所有字段和值。
     *
     * @param key 键
     * @return 以Map形式返回哈希表的字段及字段值。 若 key 不存在，返回空列表。
     * @author colg
     */
    Map<String, String> hgetAll(String key);

    /**
     * 获取在哈希表中指定 key 的所有字段。
     *
     * @param key 键
     * @return
     * @author colg
     */
    Set<String> hkeys(String key);

    /**
     * 获取在哈希表中指定 key 的所有值。
     * 
     * @param key 键
     * @return 一个包含哈希表中所有值的表。 当 key 不存在时，返回一个空集合。
     * @author colg
     */
    List<String> hvals(String key);
}