package com.zys.ezrpc.fault.retry;

import com.zys.ezrpc.spi.SpiLoader;

/**
 * 重试策略工厂（用于获取重试器对象）
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.fault.retry
 * @Author: ZYS
 * @CreateTime: 2024-07-25  20:13
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    public static RetryStrategy getInstance(String retryStrategyName) {
        if (retryStrategyName == null || retryStrategyName.isEmpty()) {
            return DEFAULT_RETRY_STRATEGY;
        }
        return SpiLoader.getInstance(RetryStrategy.class, retryStrategyName);
    }

}
