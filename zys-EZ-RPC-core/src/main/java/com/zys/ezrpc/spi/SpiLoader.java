package com.zys.ezrpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.zys.ezrpc.constant.RpcConstant;
import com.zys.ezrpc.loadbalancer.LoadBalancer;
import com.zys.ezrpc.loadbalancer.LoadBalancerKeys;
import com.zys.ezrpc.register.Registry;
import com.zys.ezrpc.register.RegistryKeys;
import com.zys.ezrpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义加载器
 * 实现键值对映射
 */
@Slf4j
public class SpiLoader {
    //已加载的类：接口名 => (key,接口实现类)
    private static final Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    //对象实例缓存（避免重复 new），类路径 => 对象实例，单例模式
    private static final Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    //系统 SPI 目录
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    //用户 SPI 目录
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    //扫描路径
    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    //动态加载的类列表
    private static List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    public static void loadAll() {
        log.info("加载所有 SPI");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }
    public static void loadAll(Class<?> clazz) {
        log.info("加载所有 SPI");
        if (clazz != null){
            LOAD_CLASS_LIST = Arrays.asList(clazz);
        }
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }

    /**
     * 获取某个接口的实例
     *
     * @param tClass
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<?> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
        }
        // 获取到要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);
        // 从实例缓存中加载指定类型的实例
        String implClassName = implClass.getName();
        if (!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            }
        }
        return (T) instanceCache.get(implClassName);
    }

    /**
     * 加载某个类型
     *
     * @param loadClass
     */
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的 SPI 优先级高于系统 SPI
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            // 读取每个资源文件
            for (URL resource : resources) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strArray = line.split("=");
                        if (strArray.length > 1) {
                            String key = strArray[0];
                            String className = strArray[1];
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                } catch (Exception e) {
                    log.error("spi resource load error", e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }

    public static void main(String[] args) {
        loadAll(LoadBalancer.class);
        System.out.println(loaderMap);
        LoadBalancer loadBalancer = getInstance(LoadBalancer.class, LoadBalancerKeys.CONSISTENT_HASH);
        System.out.println(loadBalancer);

    }

}
