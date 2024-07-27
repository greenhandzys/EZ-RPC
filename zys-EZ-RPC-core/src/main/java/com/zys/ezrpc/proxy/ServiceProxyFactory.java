package com.zys.ezrpc.proxy;


import com.zys.ezrpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.proxy
 * @Author: ZYS
 * @CreateTime: 2024-07-18  11:47
 */
public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> clazz) {
        if (RpcApplication.getRpcConfig().getMock()) {
            return getMockedProxy(clazz);
        }
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy());
    }

    //mock
    public static <T> T getMockedProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new MockServiceProxy());
    }
}
