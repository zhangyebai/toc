package com.zyb.toc.service.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * javadoc AsyncTaskExecutor
 * <p>
 *     异步任务执行器
 * <p>
 * @author zhang yebai
 * @date 2020/12/28 7:16 PM
 * @version 1.0.0
 **/
@Configuration
@Slf4j
public class AsyncTaskExecutorConfig {

    @Bean(destroyMethod = "shutdown")
    @Qualifier(value = "asyncTaskExecutor")
    public ThreadPoolExecutor asyncTaskExecutor() {
        final var asyncTaskExecutor =
                new ThreadPoolExecutor(
                        40,
                        60,
                        60,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(10000),
                        new ThreadFactoryBuilder().setNameFormat("async-task-executor-%d").setDaemon(true).build()
                );
        asyncTaskExecutor.setRejectedExecutionHandler((runnable, executor) -> {
            if (!executor.isShutdown()) {
                try {
                    executor.getQueue().put(runnable);
                } catch (InterruptedException ex) {
                    log.error("asyncTaskExecutor exception occurs:", ex);
                    Thread.currentThread().interrupt();
                }
            }
        });
        return asyncTaskExecutor;
    }

}
