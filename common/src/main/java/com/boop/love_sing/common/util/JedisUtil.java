package com.boop.love_sing.common.util;

import com.boop.love_sing.common.enums.ResultCode;
import com.boop.love_sing.common.exception.ForestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * JedisUtil(推荐存Byte数组，存Json字符串效率更慢)
 * @author dolyw.com
 * @date 2018/9/4 15:45
 */
@Component
public class JedisUtil {

    /**
     * 静态注入JedisPool连接池
     * 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
     * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
     * https://blog.csdn.net/W_Z_W_888/article/details/79979103
     */
    private static JedisPool jedisPool;

    static {
        JedisUtil.jedisPool = new JedisPool("localhost", 6379);
    }

    /**
     * 获取Jedis实例

     * @return redis.clients.jedis.Jedis
     * @author dolyw.com
     * @date 2018/9/4 15:47
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ForestException(ResultCode.RESULE_DATA_NONE);
        }
    }

    /**
     * 释放Jedis资源
     * @author dolyw.com
     * @date 2018/9/5 9:16
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_EXCEED_LOAD);
        }
    }

    /**
     * 获取redis键值-object
     * @return java.lang.Object
     * @author dolyw.com
     * @date 2018/9/4 15:47
     */
    public static Object getObject(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(key.getBytes());
            if (StringUtil.isNotNull(bytes)) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_EXCEED_LOAD);
        }
        return null;
    }

    /**
     * 设置redis键值-object
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 15:49
     */
    public static String setObject(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 设置redis键值-object-expiretime
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 15:50
     */
    public static String setObject(String key, Object value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (result.equals("OK")) {
                jedis.expire(key.getBytes(), expiretime);
            }
            return result;
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 获取redis键值-Json
     * @return java.lang.Object
     * @author dolyw.com
     * @date 2018/9/4 15:47
     */
    public static String getJson(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 设置redis键值-Json
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 15:49
     */
    public static String setJson(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 设置redis键值-Json-expiretime
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 15:50
     */
    public static String setJson(String key, String value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key, value);
            if (result.equals("OK")) {
                jedis.expire(key, expiretime);
            }
            return result;
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 删除key
     * @return java.lang.Long
     * @author Wang926454
     * @date 2018/9/4 15:50
     */
    public static Long delKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * key是否存在
     * @return java.lang.Boolean
     * @author Wang926454
     * @date 2018/9/4 15:51
     */
    public static Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     * @return java.util.Set<java.lang.String>
     * @author Wang926454
     * @date 2018/9/6 9:43
     */
    public static Set<String> keysS(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key);
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     * @return java.util.Set<java.lang.String>
     * @author Wang926454
     * @date 2018/9/6 9:43
     */
    public static Set<byte[]> keysB(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key.getBytes());
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 获取过期剩余时间
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/11 16:26
     */
    public static Long ttl(String key) {
        Long result = -2L;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }
}
