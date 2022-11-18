package com.tang.newcloud.service.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.trade.entity.PayLog;
import com.tang.newcloud.service.trade.service.PayLogService;
import com.tang.newcloud.service.trade.mapper.PayLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 29878
* @description 针对表【trade_pay_log(支付日志表)】的数据库操作Service实现
* @createDate 2022-11-16 10:29:53
*/
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog>
    implements PayLogService{

}




