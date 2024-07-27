package com.zys.example.provider.service;

import com.zys.example.common.model.User;
import com.zys.example.common.service.UserService;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.provider.service
 * @Author: ZYS
 * @CreateTime: 2024-07-18  09:45
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByName(String userName) {
        return new User("ZYS", "man", 18);
    }
}
