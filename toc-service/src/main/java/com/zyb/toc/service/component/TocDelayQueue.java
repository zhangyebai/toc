package com.zyb.toc.service.component;

import org.redisson.api.RDelayedQueue;

import java.util.concurrent.TimeUnit;

/**
 * javadoc DelayQueue
 * <p>
 *     延迟队列
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 4:28 PM
 * @version 1.0.0
 **/
public class TocDelayQueue<V> {

    private final RDelayedQueue<V> delayedQueue;

    public TocDelayQueue(RDelayedQueue<V> delayedQueue){
        this.delayedQueue = delayedQueue;
    }

    public void offer(V v, long delay, TimeUnit timeUnit){
        this.delayedQueue.offer(v, delay, timeUnit);
    }

    public void remove(V v){
        this.delayedQueue.remove(v);
    }
}
