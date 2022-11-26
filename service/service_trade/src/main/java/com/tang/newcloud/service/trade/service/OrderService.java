package com.tang.newcloud.service.trade.service;

import com.tang.newcloud.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author 29878
* @description 针对表【trade_order(订单)】的数据库操作Service
* @createDate 2022-11-16 10:29:53
*/
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String id) throws Exception;

    Order getByOrderId(String orderId, String memberId);

    Boolean isBuyByCourseId(String courseId, String memberId);

    List<Order> selectByMemberId(String memberId);

    boolean removeById(String orderId, String memberId);

    Order getOrderByOrderNo(String orderNo);

    void updateOrderStatus(Map<String, String> notifyMap);

    boolean queryPayStatus(String orderNo);
}
