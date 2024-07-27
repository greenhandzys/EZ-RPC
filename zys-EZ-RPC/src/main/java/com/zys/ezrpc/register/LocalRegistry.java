package com.zys.ezrpc.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.register
 * @Author: ZYS
 * @CreateTime: 2024-07-18  10:18
 */
public class LocalRegistry {

    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册
     *
     * @param serverName
     * @param clazz
     */
    public static void register(String serverName,Class<?> clazz){
        map.put(serverName,clazz);
    }

    /**
     * 获取服务
     * @param serverName
     * @return
     */
    public static Class<?> get(String serverName){
        return map.get(serverName);
    }

    /**
     * 删除服务
     * @param serverName
     */
    public static void remove(String serverName){
        map.remove(serverName);
    }
}
