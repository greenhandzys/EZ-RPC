package com.zys.example.common.service;

import com.zys.example.common.model.User;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.common.service
 * @Author: ZYS
 * @CreateTime: 2024-07-18  09:38
 */
public interface UserService {
    /**
     * 通过用户名获取用户
     *
     * @param userName 用户名
     * @return 用户
     */
    User getUserByName(String userName);


    default int getNumberTestMock(){
        return 1;
    }
}
