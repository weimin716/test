package com.gdd.ylz.config.websocket;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.phimait.lsinformatization.common.util.PasswordUtil;
import com.phimait.lsinformatization.domain.request.LoginRequest;
import com.phimait.lsinformatization.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/26 0026 9:02
 * @description：websocket拦截器
 * @modified By：`
 * @version: 1.0
 */
@Component
@Slf4j
public class RbacHandshakeInterceptor implements HandshakeInterceptor {
    @Value("${ls.mock.flag}")
    private Boolean isDevelopModel;

    @Resource
    private UserService userService;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(!StpUtil.isLogin()){
            if(Boolean.TRUE.equals(isDevelopModel)){
                log.info("开发模式，自动执行登录 登录用户-->{}", "mock");
                SaTokenInfo login = userService.login(
                        new LoginRequest("mock", PasswordUtil.encrypt("mock"))
                );
                log.info("开发模式，自动执行登录，登录Token为-->{}", login.getTokenValue());
                return true;
            }else{
                log.info("生产模式未登录,尝试websocket连接的ip地址为{}",request.getRemoteAddress());
                return false;
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
