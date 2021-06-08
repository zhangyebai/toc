package com.zyb.toc.service.service.impl;

import com.zyb.toc.service.converter.TaskConverter;
import com.zyb.toc.service.domain.po.DelayTaskPo;
import com.zyb.toc.service.function.TransactionMixer;
import com.zyb.toc.service.processor.ITaskProcessor;
import com.zyb.toc.service.service.ICallbackService;
import com.zyb.toc.service.service.ITaskService;
import com.zyb.toc.spi.domain.TaskBo;
import com.zyb.toc.spi.domain.enums.CallbackErrorEnum;
import com.zyb.toc.spi.domain.enums.LocationEnum;
import com.zyb.toc.spi.domain.enums.StatusEnum;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.concurrent.TimeUnit;

/**
 * javadoc TaskServiceImpl
 * <p>
 *     延时任务service impl
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:59 PM
 * @version 1.0.0
 **/
@Service
public class TaskServiceImpl implements ITaskService {

    private ITaskProcessor processor;
    @Autowired
    public TaskServiceImpl setProcessor(ITaskProcessor processor) {
        this.processor = processor;
        return this;
    }

    private ICallbackService callbackService;
    @Autowired
    public TaskServiceImpl setCallbackService(ICallbackService callbackService) {
        this.callbackService = callbackService;
        return this;
    }

    private Sinks.Many<DelayTaskPo> redisStreamProducerConsumer;
    @Autowired
    @Qualifier(value = "redisStreamProducerConsumer")
    public TaskServiceImpl setRedisStreamProducerConsumer(Sinks.Many<DelayTaskPo> redisStreamProducerConsumer) {
        this.redisStreamProducerConsumer = redisStreamProducerConsumer;
        return this;
    }

    private TransactionMixer transactionMixer;
    @Autowired
    public TaskServiceImpl setTransactionMixer(TransactionMixer transactionMixer) {
        this.transactionMixer = transactionMixer;
        return this;
    }

    private RDelayedQueue<String> delayedQueue;
    @Autowired
    public TaskServiceImpl setDelayedQueue(RDelayedQueue<String> delayedQueue) {
        this.delayedQueue = delayedQueue;
        return this;
    }


    @Override
    public TaskBo create(TaskBo bo) {
        final var task = TaskConverter.bo2entity(bo);
        if(bo.getDelay() <= 1){
            final var backup = TaskConverter.entity2record(task);
            processor.save(backup);
            callbackService.callbackAsync(backup, bo.getCallback());
        }else if(bo.getDelay() <= TimeUnit.MINUTES.toSeconds(5)){
            task.setLocation(LocationEnum.REDIS.getLocation()).setStatus(StatusEnum.REDIS.getStatus());
            processor.save(task);
            redisStreamProducerConsumer.tryEmitNext(new DelayTaskPo().setTaskId(task.getTaskId()).setDelay(bo.getDelay()).setTimestamp(System.currentTimeMillis()));
        }else{
            processor.save(task);
        }
        return bo;
    }

    @Override
    public TaskBo cancel(String taskId) {
        return processor.findByTaskId(taskId)
                .map(t -> {
                    t.setStatus(StatusEnum.CANCEL.getStatus());
                    final var r = TaskConverter.entity2record(t).setStatus(CallbackErrorEnum.CANCEL.getError());
                    final var bo = TaskConverter.entity2bo(t);
                    transactionMixer.actionProxy(() -> processor.delete(t), () -> processor.save(r));
                    if(LocationEnum.REDIS.getLocation() == t.getLocation()){
                        delayedQueue.remove(t.getTaskId());
                    }
                    return bo;
                })
                .orElse(null);
    }
}
