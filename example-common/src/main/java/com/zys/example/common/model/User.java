package com.zys.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.common.model
 * @Author: ZYS
 * @CreateTime: 2024-07-18  09:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String name;
    private String gender;
    private Integer age;

    public User(String name,String gender, int age) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
