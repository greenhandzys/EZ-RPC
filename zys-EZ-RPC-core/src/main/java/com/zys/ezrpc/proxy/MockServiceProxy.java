package com.zys.ezrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.proxy
 * @Author: ZYS
 * @CreateTime: 2024-07-19  09:46
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Class<?> methodRtnType = method.getReturnType();
        log.info("mock service proxy invoke method:{}", method.getName());
        return getDefaultObject(methodRtnType);
    }

    private Object getDefaultObject(Class<?> methodRtnType) {
        if (methodRtnType.isPrimitive()){
            if (methodRtnType == int.class){
                return 0;
            }else if (methodRtnType == long.class){
                return 0L;
            }else if (methodRtnType == double.class){
                return 0.0;
            }else if (methodRtnType == float.class){
                return 0.0f;
            }else if (methodRtnType == boolean.class){
                return false;
            }else if (methodRtnType == char.class){
                return '\u0000';
            }else if (methodRtnType == byte.class){
                return (byte) 0;
            }else if (methodRtnType == short.class){
                return (short) 0;
            } else if (methodRtnType == String.class) {
                return "";
            }
        }
        return null;
    }
}
