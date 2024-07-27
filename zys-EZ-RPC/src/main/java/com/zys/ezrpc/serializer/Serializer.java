package com.zys.ezrpc.serializer;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.serializer
 * @Author: ZYS
 * @CreateTime: 2024-07-18  10:28
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> byte[] serialize(T obj) throws Exception;

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws Exception;
}
