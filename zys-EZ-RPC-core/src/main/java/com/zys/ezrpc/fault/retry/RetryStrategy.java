package com.zys.ezrpc.fault.retry;

import com.zys.ezrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.fault.retry
 * @Author: ZYS
 * @CreateTime: 2024-07-25  20:10
 */
public interface RetryStrategy {

    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;

}
