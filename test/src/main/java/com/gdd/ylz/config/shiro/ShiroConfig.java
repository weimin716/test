package com.gdd.ylz.config.shiro;

import com.gdd.ylz.common.config.Property;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @ClassName: ShiroConfig
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private Property property;
    @Bean
    public ShiroCacheManager cacheManager(){
        return new ShiroCacheManager();
    }

    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm=new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(cacheManager());
        return customRealm;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm());
        return defaultWebSecurityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put("token",new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(linkedHashMap);
        LinkedHashMap<String,String> hashMap=new LinkedHashMap<>();
        for(String url:property.getPermissionsModules()) {
            hashMap.put("/"+url+"/**","anon");
        }
        hashMap.put("/static/**", "anon");
        hashMap.put("/upload/**", "anon");
        hashMap.put("/admin/login", "anon");
        hashMap.put("/admin/logout", "logout");
        //手机登录
        hashMap.put("/admin/API/login", "anon");
        // 放验证码
        hashMap.put("/captcha/**", "anon");
        // 释放 druid 监控画面
        hashMap.put("/druid/**", "anon");
        hashMap.put("/sms/**","anon");
        // 释放websocket请求
        hashMap.put("/websocket", "anon");
        // 前端
        hashMap.put("/", "anon");
        hashMap.put("/index", "anon");
        hashMap.put("/doc.html","anon");
        // 任务调度暂时放开
        hashMap.put("/quartz/**", "anon");
        hashMap.put("/admin/register", "anon");

        // 开放APicontroller
        hashMap.put("/ApiController/**", "anon");

        hashMap.put("/oss/**", "anon");
        hashMap.put("/druid/**", "anon");
        hashMap.put("/api/user/login","anon");
        //放开swagger-ui地址
        hashMap.put("/swagger/**", "anon");
        hashMap.put("/v2/api-docs", "anon");
        hashMap.put("/swagger-ui.html", "anon");
        hashMap.put("/swagger-ui/**", "anon");
        hashMap.put("/swagger-resources/**", "anon");
        hashMap.put("/webjars/**", "anon");
        hashMap.put("/favicon.ico", "anon");
        hashMap.put("/captcha.jpg", "anon");
        //druid sql监控配置
        hashMap.put("/druid/**", "anon");
        hashMap.put("/**","token,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param securityManager
     * @return       org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
