package com.gdd.ylz.client.service.impl;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/26 0026 14:29
 * @description：客户端逻辑业务实现类
 * @modified By：`
 * @version: 1.0
 */

import com.phimait.lsinformatization.webscoket.client.config.JavaWebSocketClient;
import com.phimait.lsinformatization.webscoket.client.service.WebSocketClientService;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
@Slf4j
public class WebSocketClientServiceImpl implements WebSocketClientService {

    @Autowired
    private JavaWebSocketClient javaClient;

    @Override
    public void getRealTimeInformation() {

        String url = "ws://127.0.0.1:9090/myUrl?token=4568";
        WebSocketClient client = this.javaClient.getClient(url);
    }

}
