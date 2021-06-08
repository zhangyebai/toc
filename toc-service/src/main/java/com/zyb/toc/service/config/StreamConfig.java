package com.zyb.toc.service.config;

import com.zyb.toc.service.domain.po.DelayTaskPo;
import com.zyb.toc.service.service.IRedisCallbackService;
import org.redisson.api.RDelayedQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * javadoc StreamConfig
 * <p>
 *     reactor stream config
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 3:14 PM
 * @version 1.0.0
 **/
@Configuration
public class StreamConfig {

    @Bean(value = "redisStreamProducerProcessor")
    public Sinks.Many<String> redisStreamProducerProcessor(IRedisCallbackService redisCallbackService){
        final Sinks.Many<String> processor =  Sinks.many().unicast().onBackpressureBuffer(new LinkedBlockingDeque<>(100000));
        processor.asFlux().subscribe(redisCallbackService::delayBackAsync);
        return processor;
    }

    @Bean(value = "redisStreamProducerConsumer")
    public Sinks.Many<DelayTaskPo> redisStreamProducerConsumer(RDelayedQueue<String> delayedQueue){
        final Sinks.Many<DelayTaskPo> processor =  Sinks.many().unicast().onBackpressureBuffer(new LinkedBlockingDeque<>(100000));
        processor.asFlux().subscribe(dt -> delayedQueue.offer(dt.getTaskId(), dt.getDelay(), TimeUnit.SECONDS));
        return processor;
    }

}
