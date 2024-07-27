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
 * @CreateTime: 2024-07-18  10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse implements Serializable {

    //响应数据
    private Object data;

    //响应数据类型
    private Class<?> dataType;

    //响应消息
    private String message;

    //响应异常
    private Exception exception;
}
