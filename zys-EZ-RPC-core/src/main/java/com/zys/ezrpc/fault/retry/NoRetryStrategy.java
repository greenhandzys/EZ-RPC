package com.zys.ezrpc.fault.retry;

import com.zys.ezrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试 - 重试策略
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.fault.retry
 * @Author: ZYS
 * @CreateTime: 2024-07-25  20:14
 */
public class NoRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
