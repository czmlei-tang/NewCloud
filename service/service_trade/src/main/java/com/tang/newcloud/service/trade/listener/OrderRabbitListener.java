package com.tang.newcloud.service.trade.listener;

import com.tang.newcloud.service.trade.entity.Order;
import com.tang.newcloud.service.trade.mapper.OrderMapper;
import com.tang.newcloud.service.trade.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RabbitListener(queues = {"newcloud_order"})
public class OrderRabbitListener {

    @Resource
    private OrderMapper orderMapper;

    @RabbitHandler
    public void  savaOrder(Message msg) throws Exception {
        if (msg == null) {
            log.error("消息的格式错误");
            return;
        }
        Order order = (Order)RabbitUtil.getObjectFromBytes(msg.getBody());
        orderMapper.insert(order);
    }
}
