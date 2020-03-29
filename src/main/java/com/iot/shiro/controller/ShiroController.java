package com.iot.shiro.controller;

import com.iot.shiro.service.imp.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 16:08
 */
@Controller
public class ShiroController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/noAuth")
    public String noAuth(){
        return "noAuth";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    
    @RequestMapping("/admin")
    public String helloAdmin() {
    	userService.helloAdmin();
    	return "redirect:/admin.html";
    }
    
    
    /**
     * 登录逻辑处理
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password, Model model,
    		HttpServletResponse response,HttpServletRequest request){
    	
    	String path = request.getContextPath();
    	Cookie c = new Cookie("username", username);
    	c.setMaxAge(60*60);
    	c.setPath(path+"/toLogin");
    	response.addCookie(c);
    	
    	
        /*
                       使用shiro编写认证操作
         */
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        	
        //开启记住我
        token.setRememberMe(true);
        
        //3.执行登录方法
        try {
            subject.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            model.addAttribute("error","用户名不存在!");
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("error","密码错误！");
            return "login";
        }
    }

}
