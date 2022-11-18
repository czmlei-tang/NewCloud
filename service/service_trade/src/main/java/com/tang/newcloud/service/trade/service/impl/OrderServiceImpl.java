package com.tang.newcloud.service.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.base.dto.CourseDto;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.trade.entity.Order;
import com.tang.newcloud.service.trade.entity.PayLog;
import com.tang.newcloud.service.trade.feign.EduCourseService;
import com.tang.newcloud.service.trade.feign.UcenterMemberService;
import com.tang.newcloud.service.trade.mapper.PayLogMapper;
import com.tang.newcloud.service.trade.service.OrderService;
import com.tang.newcloud.service.trade.mapper.OrderMapper;
import com.tang.newcloud.service.trade.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
* @author 29878
* @description 针对表【trade_order(订单)】的数据库操作Service实现
* @createDate 2022-11-16 10:29:53
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PayLogMapper payLogMapper;

    @Override
    public String saveOrder(String courseId, String memberId) {

        //查询当前用户是否已有当前课程的订单
        String orderId=orderMapper.selectOrderByCourseIdAndMenberId(courseId,memberId);
        if(orderId!=null){
            //如果订单已存在，则直接返回订单id
            return orderId;
        }
        //查询课程信息
        CourseDto courseDto = eduCourseService.getCourseDtoById(courseId);
        if (courseDto == null) {
            throw new NewCloudException(ResultCodeEnum.PARAM_ERROR);
        }
        //查询用户信息
        MemberDto memberDto = ucenterMemberService.getMemberDtoByMemberId(memberId);
        if (memberDto == null) {
            throw new NewCloudException(ResultCodeEnum.PARAM_ERROR);
        }
        //创建订单
        Order order = new Order();
        String defaultSnowFlakeId = String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId());
        order.setId(defaultSnowFlakeId);
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice().multiply(new BigDecimal(100)));//分
        order.setMemberId(memberId);
        order.setMobile(memberDto.getMobile());
        order.setNickname(memberDto.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//微信支付

        orderMapper.insert(order);
        return order.getId();
    }


    @Override
    public Order getByOrderId(String orderId, String memberId) {

        return orderMapper.selectByOrderIdAndMemberId(orderId,memberId);
    }

    @Override
    public Boolean isBuyByCourseId(String courseId, String memberId) {
        Integer integer = orderMapper.selectStatus(courseId, memberId);
        return  integer==1?true:false;
    }

    @Override
    public List<Order> selectByMemberId(String memberId) {
        return orderMapper.selectOrderListByMemberId(memberId);
    }

    @Override
    public boolean removeById(String orderId, String memberId) {
        return orderMapper.deleteByOrderIdAndMemberId(orderId,memberId);
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Map<String, String> map) {
        //更新订单状态
        String orderNo = map.get("out_trade_no");
        Order order = this.getOrderByOrderNo(orderNo);
        order.setStatus(1);//支付成功
        baseMapper.updateById(order);

        //记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(Long.parseLong(map.get("total_fee")));//总金额(分)
        payLog.setTradeState(map.get("result_code"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(new Gson().toJson(map));
        payLogMapper.insert(payLog);

        //更新课程销量：有问题直接熔断
        eduCourseService.updateBuyCountById(order.getCourseId());
    }

    @Override
    public boolean queryPayStatus(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = baseMapper.selectOne(queryWrapper);
        return order.getStatus() == 1;
    }
}




