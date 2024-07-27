package com.zys.ezrpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.model
 * @Author: ZYS
 * @CreateTime: 2024-07-18  10:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcRequest implements Serializable {

    //服务名
    private String serviceName;

    //方法名
    private String methodName;

    //参数类型
    private Class<?>[] parameterTypes;

    //参数
    private Object[] parameters;


}
