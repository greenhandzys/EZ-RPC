package com.zys.ezrpc.serializer;


import com.zys.ezrpc.spi.SpiLoader;

/**
 * 序列化工厂，用于获取单例序列化器对象
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }
    // 默认使用jdk序列化
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    // 获取序列化器
    public static Serializer getInstance(String serializerName) {
        if (serializerName == null || serializerName.length() == 0) {
            return DEFAULT_SERIALIZER;
        }
        return SpiLoader.getInstance(Serializer.class,serializerName);
    }

}
