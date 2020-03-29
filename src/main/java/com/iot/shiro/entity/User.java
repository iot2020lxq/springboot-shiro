package com.iot.shiro.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author liuxiangqian
 * @version 2020/3/25 0025 - 14:25
 */
@Component
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String username;
    private String password;
    private String perms;

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", perms='" + perms + '\'' +
                '}';
    }
}
