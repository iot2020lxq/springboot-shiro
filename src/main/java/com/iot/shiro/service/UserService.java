package com.iot.shiro.service;

import com.iot.shiro.entity.User;

/**
 * @author liuxiangqian
 * @version 2020/3/25 0025 - 14:26
 */
public interface UserService {

    public User queryUserByUserName(String username);

    public User queryUserByUserId(Integer id);

}
