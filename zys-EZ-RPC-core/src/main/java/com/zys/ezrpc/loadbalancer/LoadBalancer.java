package com.zys.ezrpc.loadbalancer;

import com.zys.ezrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（消费端使用）
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.loadbalancer
 * @Author: ZYS
 * @CreateTime: 2024-07-24  20:58
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
