package com.tang.newcloud.service.sms.service;

import com.aliyuncs.exceptions.ClientException;

public interface SmsService {
    void send(String mobile, String checkCode) ;
}
