package com.psbc.rpa.config;

import com.psbc.rpa.context.TokenContext;
import com.psbc.rpa.handler.WebSocketHandler;
import com.psbc.rpa.ws.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author dahua
 * @time 2022/3/7 15:19
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private TokenContext tokenContext;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //.setAllowedOrigins(allowsOrigins); // 此处与客户端的 URL 相对应
        registry.addHandler(webSocketHandler(), "/yc-rpa/ws").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor(tokenContext));
        //addInterceptors将拦截器添加进来
        registry.addHandler(webSocketHandler(), "/yc-rpa/ws").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor(tokenContext)).withSockJS();
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler(tokenContext);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
//        container.setMaxSessionIdleTimeout((long) -1);
        return container;
    }

}