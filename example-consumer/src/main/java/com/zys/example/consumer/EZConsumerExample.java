package com.zys.example.consumer;

import com.zys.example.common.model.User;
import com.zys.example.common.service.UserService;
import com.zys.ezrpc.proxy.ServiceProxyFactory;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.consumer
 * @Author: ZYS
 * @CreateTime: 2024-07-18  09:53
 */
public class EZConsumerExample {
    private static UserService userService = ServiceProxyFactory.getProxy(UserService.class);

    public static void main(String[] args) {
        User user = null;
        for (int i = 0; i < 10; i++) {
            user = userService.getUserByName("zys");
            if (user == null) {
                System.out.println("user is null");
                return;
            }
            System.out.println(user);
        }

//        int numberTestMock = userService.getNumberTestMock();
//        System.out.println("numberTestMock = " + numberTestMock);
    }
}
