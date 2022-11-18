package com.tang.newcloud.service.trade.mapper;

import com.tang.newcloud.service.trade.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 29878
* @description 针对表【trade_order(订单)】的数据库操作Mapper
* @createDate 2022-11-16 10:29:53
* @Entity com.tang.newcloud.service.trade.entity.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    String selectOrderByCourseIdAndMenberId(String courseId, String memberId);

    Order selectByOrderIdAndMemberId(String orderId, String memberId);

    Integer selectStatus(String courseId, String memberId);

    List<Order> selectOrderListByMemberId(String memberId);

    boolean deleteByOrderIdAndMemberId(String orderId, String memberId);
}




