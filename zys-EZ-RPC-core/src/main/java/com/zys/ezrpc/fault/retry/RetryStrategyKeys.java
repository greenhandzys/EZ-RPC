package com.zys.ezrpc.fault.retry;

/**
 * 重试策略键名常量
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.fault.retry
 * @Author: ZYS
 * @CreateTime: 2024-07-25  20:12
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
