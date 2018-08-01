package com.wxsell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket接收消息，并响应
 * Created By Cx On 2018/7/31 0:25
 */
@Component
@ServerEndpoint(value = "/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    //TODO 注意：这里不能用普通的set，具体原因待查
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        log.info("【webSocket消息】有新的连接，当前连接总数：{}",webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("【webSocket消息】连接断开，当前连接总数：{}",webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【webSocket消息】收到客户端消息：{}",message);
    }

    /**
     * 将消息发送给所有当前连接服务器的客户端
     */
    public void sendMessage(String message){
        for (WebSocket webSocket : webSockets){
            log.info("【webSocket消息】广播消息:{}",message);
            //try-catch捕获异常，防止异常影响正常业务
            try {
                //发送消息
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
