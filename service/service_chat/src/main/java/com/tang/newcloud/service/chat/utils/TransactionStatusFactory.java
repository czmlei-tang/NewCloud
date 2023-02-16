package com.tang.newcloud.service.chat.utils;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * @program: NewCloud
 * @description: 手动事务工具类
 * @author: tanglei
 * @create: 2023-02-16 14:03
 **/

public class TransactionStatusFactory{

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     *
     * @param transactionName 事务名称
     * @param i 事务隔离级别 TransactionDefinition.PROPAGATION_REQUIRED
     * @return
     */
    public TransactionStatus getTransactionDefinition(String transactionName, int i){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(transactionName);
        def.setPropagationBehavior(i);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
        return status;
    }

}
