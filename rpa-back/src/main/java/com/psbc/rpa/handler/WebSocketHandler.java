package com.psbc.rpa.handler;

import com.psbc.rpa.consts.Const;
import com.psbc.rpa.context.TokenContext;
import com.psbc.rpa.handler.impl.CaseHandler;
import com.psbc.rpa.handler.impl.SystemHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dahua
 * @time 2022/3/14 10:57
 */
public class WebSocketHandler extends TextWebSocketHandler {

    public static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private TokenContext tokenContext;
    private Map<String, RpaHandler> handlerMap = new HashMap();

    public WebSocketHandler(TokenContext tokenContext) {
        this.tokenContext = tokenContext;
        handlerMap.put("case", new CaseHandler());
        handlerMap.put("system", new SystemHandler());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String body = message.getPayload();
        if (StringUtils.isNotEmpty(body)) {
            RpaHandler rpaHandler = handlerMap.get(body.split(":")[0]);
            String js = rpaHandler.handler(body);
            try {
                session.sendMessage(new TextMessage(js));
            } catch (IOException e) {
                logger.error("send js error {}", e);
            }
        }
    }

    //连接建立后处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String token = (String) session.getAttributes().get(Const.USER_TOKEN);
        tokenContext.getUser(token).setSession(session);
        logger.info("用户加入，当前在线用户：" + tokenContext.getSize() + "人");
    }

    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("connection error {}", exception);
    }

    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        String token = (String) session.getAttributes().get(Const.USER_TOKEN);
        tokenContext.removeUser(token);
        logger.info("用户退出，当前在线用户：" + tokenContext.getSize() + "人");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}