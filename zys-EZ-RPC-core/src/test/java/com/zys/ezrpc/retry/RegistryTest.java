package com.zys.ezrpc.retry;

import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.fault.retry.NoRetryStrategy;
import com.zys.ezrpc.fault.retry.RetryStrategy;
import com.zys.ezrpc.fault.retry.RetryStrategyFactory;
import com.zys.ezrpc.fault.retry.RetryStrategyKeys;
import com.zys.ezrpc.model.RpcResponse;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.retry
 * @Author: ZYS
 * @CreateTime: 2024-07-25  20:24
 */
public class RegistryTest {

    @Test
    public void reTryTest() {
        RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(RetryStrategyKeys.FIXED_INTERVAL);
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(new Callable<RpcResponse>() {
                @Override
                public RpcResponse call() {
                    System.out.println("测试重试");
                    throw new RuntimeException("模拟重试结束");
                }
            });

            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            throw new RuntimeException(e);
        }
    }
}
