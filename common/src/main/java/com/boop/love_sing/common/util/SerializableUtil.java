package com.boop.love_sing.common.util;

import com.boop.love_sing.common.enums.ResultCode;
import com.boop.love_sing.common.exception.ForestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Serializable工具(JDK)(也可以使用Protobuf自行百度)
 * @author dolyw.com
 * @date 2018/9/4 15:13
 */
public class SerializableUtil {

    private SerializableUtil() {}

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * 序列化
     * @return byte[]
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("SerializableUtil工具类序列化出现IOException异常:{}", e.getMessage());
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                logger.error("SerializableUtil工具类反序列化出现IOException异常:{}", e.getMessage());
            }
        }
    }

    /**
     * 反序列化
     * @return java.lang.Object
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            logger.error("SerializableUtil工具类反序列化出现ClassNotFoundException异常:{}", e.getMessage());
            throw new ForestException(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (IOException e) {
            logger.error("SerializableUtil工具类反序列化出现IOException异常:{}", e.getMessage());
            throw new ForestException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                logger.error("SerializableUtil工具类反序列化出现IOException异常:{}", e.getMessage());
            }
        }
    }

}