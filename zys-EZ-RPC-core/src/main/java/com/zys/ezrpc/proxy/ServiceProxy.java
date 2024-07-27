package com.zys.ezrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.constant.RpcConstant;
import com.zys.ezrpc.fault.retry.RetryStrategy;
import com.zys.ezrpc.fault.retry.RetryStrategyFactory;
import com.zys.ezrpc.loadbalancer.LoadBalancer;
import com.zys.ezrpc.loadbalancer.LoadBalancerFactory;
import com.zys.ezrpc.loadbalancer.LoadBalancerKeys;
import com.zys.ezrpc.model.RpcRequest;
import com.zys.ezrpc.model.RpcResponse;
import com.zys.ezrpc.model.ServiceMetaInfo;
import com.zys.ezrpc.register.Registry;
import com.zys.ezrpc.register.RegistryFactory;
import com.zys.ezrpc.serializer.Serializer;
import com.zys.ezrpc.serializer.SerializerFactory;
import com.zys.ezrpc.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.proxy
 * @Author: ZYS
 * @CreateTime: 2024-07-18  11:30
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        String serviceName = method.getDeclaringClass().getName();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();


        try {
            // 获取真实的url
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(rpcConfig.getVersion());
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalance());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            System.out.println("这次请求的端口是" + selectedServiceMetaInfo.getServicePort());

            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RpcResponse rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
            return rpcResponse.getData();

//            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
//
//            return retryStrategy.doRetry(() -> VertxTcpClient.doRequest(rpcRequest, serviceMetaInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
