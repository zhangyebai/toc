package com.zyb.toc.service.runner;

import com.zyb.toc.service.listener.RedisListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * javadoc DataSourceInitRunner
 * <p>
 *     数据初始化runner
 * <p>
 * @author zhang yebai
 * @date 2021/5/21 3:26 PM
 * @version 1.0.0
 **/
@Component
public class TaskListenerInitRunner implements ApplicationRunner {

    private RedisListener redisListener;
    @Autowired
    public TaskListenerInitRunner setRedisListener(RedisListener redisListener) {
        this.redisListener = redisListener;
        return this;
    }

    private ThreadPoolExecutor asyncTaskExecutor;
    @Autowired
    public TaskListenerInitRunner setAsyncTaskExecutor(ThreadPoolExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        return this;
    }

    /**
     * javadoc run
     * @apiNote 装配 动态数据源
     *
     * @param args application start args (unused)
     * @author zhang yebai
     * @date 2021/5/27 5:54 PM
     **/
    @Override
    public void run(ApplicationArguments args) {
        asyncTaskExecutor.submit(() -> redisListener.listen());
    }
}
