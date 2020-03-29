package com.iot.shiro.config;

import com.iot.shiro.entity.User;
import com.iot.shiro.service.imp.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 16:19
 */
//public class UserRealm2 extends AuthorizingRealm {
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    private static Logger logger = LoggerFactory.getLogger(UserRealm2.class);
//
//    /*
//        执行授权逻辑
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        logger.info("执行授权逻辑2");
//
//        //给资源进行授权
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//
//        //得到当前登录的用户
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//        User dbUser = userService.queryUserByUserId(user.getId());
//        info.addStringPermission(dbUser.getPerms());
//        return info;
//    }
//
//    /*
//        执行认证逻辑
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        logger.info("执行认证逻辑2");
//
//        //得到token
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String username = token.getUsername();
//
//        //查询用户账号和密码
//        User user = userService.queryUserByUserName(username);
//
//        //盐值加密，解决密码hash值一样的问题，方法的参数值要唯一
//        ByteSource salt = ByteSource.Util.bytes(username);
//
//        //用户密码MD5加密
//        SimpleHash sha1Password = new SimpleHash("SHA1", user.getPassword(), salt, 1024);
//
//        System.out.println("SHA1："+sha1Password);
//
//        //1.判断用户名
//        if( user == null){
//            //用户不存在
//            return null;//shiro会抛出 UnkownAccountException异常
//        }
//
//        System.out.println("RealmName2："+getName());
//
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, sha1Password, salt,getName());
//        return info;
//
//    }
//}
