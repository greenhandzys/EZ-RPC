package com.zys.ezrpc.config;

import com.zys.ezrpc.fault.retry.RetryStrategyKeys;
import com.zys.ezrpc.loadbalancer.LoadBalancerKeys;
import com.zys.ezrpc.serializer.SerializerKeys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.config
 * @Author: ZYS
 * @CreateTime: 2024-07-18  15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcConfig {

    private String name="EZ-RPC";

    private String version="1.0.0";

    private String serverHost="localhost";

    private Integer serverPort=8080;

    private Boolean mock=false;

    private String serialize= SerializerKeys.JDK;

    private RegistryConfig registryConfig = new RegistryConfig();

    private String loadBalance= LoadBalancerKeys.ROUND;

    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;

}
