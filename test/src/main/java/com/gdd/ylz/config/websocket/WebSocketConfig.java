package com.gdd.ylz.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/25 0025 14:33
 * @description：WebSocketConfig配置
 * @modified By：`
 * @version: 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketDecoratorFactory webSocketDecoratorFactory;
    @Autowired
    private PrincipalHandshakeHandler principalHandshakeHandler;
    @Autowired
    private RbacHandshakeInterceptor rbacHandshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * myUrl表示 你前端到时要对应url映射
         */
        registry.addEndpoint("/myUrl")
                .setAllowedOriginPatterns("*")
                .addInterceptors(rbacHandshakeInterceptor)
                .setHandshakeHandler(principalHandshakeHandler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * queue 点对点
         * topic 广播
         * user 点对点前缀
         */
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(webSocketDecoratorFactory);
        super.configureWebSocketTransport(registration);
    }
}

