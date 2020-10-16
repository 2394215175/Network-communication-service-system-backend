package com.htphy.wx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket支持
 * ServerEndpointExporter 作用
 * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
 * @return
 */
    @Configuration
    public class WebSocketConfig {

        @Bean
        public ServerEndpointExporter serverEndpointExporter() {
            return new ServerEndpointExporter();
        }

    }

