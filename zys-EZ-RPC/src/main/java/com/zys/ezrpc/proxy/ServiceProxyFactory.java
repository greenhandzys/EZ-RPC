package com.zys.ezrpc.proxy;


import java.lang.reflect.Proxy;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.proxy
 * @Author: ZYS
 * @CreateTime: 2024-07-18  11:47
 */
public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy());
    }
}
