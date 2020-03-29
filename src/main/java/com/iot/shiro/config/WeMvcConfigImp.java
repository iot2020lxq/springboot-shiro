package com.iot.shiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 17:03
 */
@Configuration
public class WeMvcConfigImp implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/add.html").setViewName("user/add");
        registry.addViewController("/update.html").setViewName("user/update");
        registry.addViewController("/admin.html").setViewName("admin/admin");
        registry.addViewController("/list.html").setViewName("list");
    }

}
