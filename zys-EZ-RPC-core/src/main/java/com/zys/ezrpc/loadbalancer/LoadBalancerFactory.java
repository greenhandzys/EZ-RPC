package com.zys.ezrpc.loadbalancer;

import com.zys.ezrpc.spi.SpiLoader;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.loadbalancer
 * @Author: ZYS
 * @CreateTime: 2024-07-24  21:11
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundLoadBalancer();

    /**
     * 获取实例
     *
     * @param key 负载均衡器的key
     * @return 负载均衡器
     */
    public static LoadBalancer getInstance(String key) {
        if (key == null || key.length() == 0) {
            return DEFAULT_LOAD_BALANCER;
        }
        Object loadBalancer = SpiLoader.getInstance(LoadBalancer.class, key);
        if (loadBalancer == null) {
            return DEFAULT_LOAD_BALANCER;
        }
        return (LoadBalancer) loadBalancer;
    }

}
