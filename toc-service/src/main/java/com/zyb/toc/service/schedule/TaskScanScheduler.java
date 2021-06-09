package com.zyb.toc.service.schedule;

import com.zyb.toc.service.converter.TaskConverter;
import com.zyb.toc.service.domain.entity.TaskEntity;
import com.zyb.toc.service.domain.po.DelayTaskPo;
import com.zyb.toc.service.domain.po.TaskQueryPo;
import com.zyb.toc.service.function.TransactionMixer;
import com.zyb.toc.service.processor.ITaskProcessor;
import com.zyb.toc.service.service.ICallbackService;
import com.zyb.toc.service.utils.JsonUtil;
import com.zyb.toc.spi.domain.CallbackBo;
import com.zyb.toc.spi.domain.enums.LocationEnum;
import com.zyb.toc.spi.domain.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * javadoc TaskScanScheduler
 * <p>
 *     定时扫描任务
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:39 PM
 * @version 1.0.0
 **/
@Component
@Slf4j
public class TaskScanScheduler {

    private ITaskProcessor taskProcessor;
    @Autowired
    public TaskScanScheduler setTaskProcessor(ITaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
        return this;
    }

    private Sinks.Many<DelayTaskPo> redisStreamProducerConsumer;
    @Autowired
    @Qualifier(value = "redisStreamProducerConsumer")
    public TaskScanScheduler setRedisStreamProducerConsumer(Sinks.Many<DelayTaskPo> redisStreamProducerConsumer) {
        this.redisStreamProducerConsumer = redisStreamProducerConsumer;
        return this;
    }

    private TransactionMixer transactionMixer;
    @Autowired
    public TaskScanScheduler setTransactionMixer(TransactionMixer transactionMixer) {
        this.transactionMixer = transactionMixer;
        return this;
    }

    private ICallbackService callbackService;
    @Autowired
    public TaskScanScheduler setCallbackService(ICallbackService callbackService) {
        this.callbackService = callbackService;
        return this;
    }

    @Scheduled(fixedDelay = 300000)
    public void scan(){
        final var now = LocalDateTime.now();
        final var target = now.plusMinutes(5);
        if(log.isInfoEnabled()){
            log.info("TaskScanScheduler.scan() start run at : {}", now);
        }
        taskProcessor.streamTasksByQuery(
                new TaskQueryPo()
                        .setLocation(LocationEnum.DB.getLocation())
                        .setStatus(StatusEnum.NEW.getStatus())
                        .setTts(target)
        ).forEach(this::transform);
        if(log.isInfoEnabled()){
            log.info("TaskScanScheduler.scan() end run at : {}", LocalDateTime.now());
        }
    }

    @SuppressWarnings(value = "all")
    public void transform(TaskEntity entity){
        final var now = LocalDateTime.now();
        final long delay = ChronoUnit.SECONDS.between(now, entity.getTts());
        if(delay <= 1){
            entity.setStatus(StatusEnum.TIMEOUT.getStatus()).setLocation(LocationEnum.RECORD.getLocation());
            final var recordEntity = TaskConverter.entity2record(entity);
            transactionMixer.actionProxy(() -> taskProcessor.delete(entity), () -> taskProcessor.save(recordEntity));
            callbackService.callbackAsync(recordEntity, JsonUtil.parseObject(entity.getCallback(), CallbackBo.class));
        }else{
            redisStreamProducerConsumer.tryEmitNext(new DelayTaskPo().setTaskId(entity.getTaskId()).setDelay(delay).setTimestamp(System.currentTimeMillis()));
            entity.setLocation(LocationEnum.REDIS.getLocation()).setStatus(StatusEnum.REDIS.getStatus()).setUts(now);
            taskProcessor.update(entity);
        }
    }
}
