package com.zyb.toc.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import reactor.core.publisher.Sinks;

/**
 * javadoc RedisListener
 * <p>
 *     redis listener
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 3:33 PM
 * @version 1.0.0
 **/
@Slf4j
public class RedisListener{

    private final RBlockingQueue<String> blockingQueue;

    private final Sinks.Many<String> processor;

    public RedisListener(RBlockingQueue<String> blockingQueue, Sinks.Many<String> processor) {
        this.blockingQueue = blockingQueue;
        this.processor = processor;
    }

    @SuppressWarnings(value =  "InfiniteLoopStatement")
    public void listen(){
        for(;;){
            try {
                final var delay = blockingQueue.take();
                processor.tryEmitNext(delay);
            }catch (InterruptedException ex){
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                log.error("RedisListener.listen() exception: ", ex);
            }
        }
    }
}
