package com.tang.newcloud.service.chat.component.websocket;

import com.tang.newcloud.service.chat.config.ShowcaseServerConfig;
import com.tang.newcloud.service.chat.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-04-16 13:47
 **/
/**
 * 消息处理类
 */
@Component
@Slf4j
public class ShowcaseWsMsgHandler implements IWsMsgHandler {


    /**
     * <li>对httpResponse参数进行补充并返回，如果返回null表示不想和对方建立连接，框架会断开连接，如果返回非null，框架会把这个对象发送给对方</li>
     * <li>注：请不要在这个方法中向对方发送任何消息，因为这个时候握手还没完成，发消息会导致协议交互失败。</li>
     * <li>对于大部分业务，该方法只需要一行代码：return httpResponse;</li>
     *
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        // 可以在此做一些业务逻辑，返回null表示不想连接
        String clientip = httpRequest.getClientIp();
        String myname = httpRequest.getParam("name");
        Tio.bindUser(channelContext, myname);
//        channelContext.setUserid(myname);
        log.info("收到来自{}的ws握手包\r\n{}", clientip, httpRequest.toString());
        return httpResponse;
    }

    /**
     * 握手成功后触发该方法
     *
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定到群组，后面会有群发
        Tio.bindGroup(channelContext, Const.GROUP_ID);
        int count = Tio.getAllChannelContexts(channelContext.tioConfig).getObj().size();
        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.tioConfig, Const.GROUP_ID, wsResponse);
    }

    /**
     * <li>当收到Opcode.BINARY消息时，执行该方法。也就是说如何你的ws是基于BINARY传输的，就会走到这个方法</li>
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return 可以是WsResponse、byte[]、ByteBuffer、String或null，如果是null，框架不会回消息
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当收到Opcode.CLOSE时，执行该方法，业务层在该方法中一般不需要写什么逻辑，空着就好
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return 可以是WsResponse、byte[]、ByteBuffer、String或null，如果是null，框架不会回消息
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        // 关闭连接
        Tio.remove(channelContext, "WebSocket Close");
        return null;
    }

    /**
     * <li>当收到Opcode.TEXT消息时，执行该方法。也就是说如何你的ws是基于TEXT传输的，就会走到这个方法</li>
     *
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return 可以是WsResponse、byte[]、ByteBuffer、String或null，如果是null，框架不会回消息
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.get();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequest();//获取websocket握手包
        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }
        log.info("收到ws消息:{}", text);
        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        //channelContext.getToken()
        //String msg = channelContext.getClientNode().toString() + " 说：" + text;
        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.tioConfig, Const.GROUP_ID, wsResponse);
        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }
}

