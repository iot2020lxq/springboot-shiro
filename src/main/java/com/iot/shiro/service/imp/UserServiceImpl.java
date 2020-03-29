package com.iot.shiro.service.imp;

import com.iot.shiro.dao.UserMapper;
import com.iot.shiro.entity.User;
import com.iot.shiro.service.UserService;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxiangqian
 * @version 2020/3/25 0025 - 14:27
 */
@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByUserName(String username) {
        User user = userMapper.queryUserByUserName(username);
        return user;
    }

    @Override
    public User queryUserByUserId(Integer id) {
        User user = userMapper.queryUserByUserId(id);
        return user;
    }
    
    @RequiresRoles({"admin"})
    public void helloAdmin() {
    	System.out.println("hello管理员！");
    }
}
