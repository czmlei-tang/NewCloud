package com.tang.newcloud.service.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author zhangna
 */
@Configuration
@EnableAsync    //开启对异步任务的支持
public class ThreadPoolConfig {

    /**
     * 配置了一个线程池，通过spring给我们提供的ThreadPoolTaskExecutor就可以使用线程池。
     * 即把ThreadPoolTaskExecutor当作bean交给IOC管理，然后要使用线程池的时候，从IOC那拿ThreadPoolTaskExecutor即可使用线程池
     *
     * @return
     */
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(5);
        //设置最大线程数
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        //设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        //设置默认线程名前缀
        executor.setThreadNamePrefix("newcloud_threadPool");
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}
