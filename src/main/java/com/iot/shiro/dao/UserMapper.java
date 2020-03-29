package com.iot.shiro.dao;

import com.iot.shiro.entity.User;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuxiangqian
 * @version 2020/3/25 0025 - 14:29
 */
@Mapper
public interface UserMapper {

    public User queryUserByUserName(String username);

    public User queryUserByUserId(Integer id);
}
