package com.zys.ezrpc.loadbalancer;

/**
 * 载均衡器键名常量
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.loadbalancer
 * @Author: ZYS
 * @CreateTime: 2024-07-24  21:10
 */
public interface LoadBalancerKeys {

    String ROUND = "round";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";
}
