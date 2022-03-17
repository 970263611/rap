package com.psbc.rpa.ws;

import com.psbc.rpa.consts.Const;
import com.psbc.rpa.context.TokenContext;
import com.psbc.rpa.exception.LoginException;
import com.psbc.rpa.model.RpaUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dahua
 * @time 2022/3/7 15:19
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    private TokenContext tokenContext;

    public WebSocketInterceptor(TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //这里有个坑，如果你前端调用的websocket地址使用的localhost，那么页面的请求地址也要是localhost，否则无法获取到session
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getHeader("Sec-WebSocket-Rpa-User-Token");
            if (StringUtils.isNotEmpty(token)) {
                RpaUser rpaUser = tokenContext.getUser(token);
                if (ObjectUtils.isNotEmpty(rpaUser)) {
                    attributes.put(Const.USER_TOKEN, token);
                } else {
                    throw new LoginException("please login first");
                }
            } else {
                throw new LoginException("please login first");
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}