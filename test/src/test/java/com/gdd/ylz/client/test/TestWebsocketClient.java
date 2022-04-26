package com.gdd.ylz.client.test;

import com.phimait.lsinformatization.webscoket.client.service.WebSocketClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/26 0026 14:36
 * @description：测试websocket客户端
 * @modified By：`
 * @version: 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWebsocketClient {
    @Autowired
    private WebSocketClientService webSocketClientService;


    @Test
    public void testGetRealInformation() throws InterruptedException {
        webSocketClientService.getRealTimeInformation();
    }
}
