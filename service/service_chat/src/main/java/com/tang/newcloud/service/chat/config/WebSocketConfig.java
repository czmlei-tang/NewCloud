package com.tang.newcloud.service.chat.config;

import com.tang.newcloud.service.chat.component.websocket.ShowcaseWsMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.server.ServerTioConfig;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-04-16 13:45
 **/
/**
 * websocket 配置类
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注入消息处理器
     */
    @Autowired
    private ShowcaseWsMsgHandler showcaseWsMsgHandler;

    /**
     * 启动类配置
     *
     * @return
     * @throws IOException
     */
    @Bean
    public WsServerStarter wsServerStarter() throws IOException {
        // 设置处理器
        WsServerStarter wsServerStarter = new WsServerStarter(ShowcaseServerConfig.SERVER_PORT, showcaseWsMsgHandler);
        // 获取到ServerTioConfig
        ServerTioConfig serverTioConfig = wsServerStarter.getServerTioConfig();
        // 设置心跳超时时间，默认：1000 * 120
        serverTioConfig.setHeartbeatTimeout(ShowcaseServerConfig.HEARTBEAT_TIMEOUT);
//        //设置ip监控
//        serverTioConfig.setIpStatListener();
//        //设置ip统计时间段
//        serverTioConfig.ipStats.addDurations(ShowcaseServerConfig.IpStatDuration.IPSTAT_DURATIONS);
        // 启动
        wsServerStarter.start();
        return wsServerStarter;
    }
}
