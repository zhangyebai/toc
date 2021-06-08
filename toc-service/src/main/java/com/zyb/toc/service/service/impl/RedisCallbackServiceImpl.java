package com.zyb.toc.service.service.impl;

import com.zyb.toc.service.converter.TaskConverter;
import com.zyb.toc.service.function.TransactionMixer;
import com.zyb.toc.service.processor.ITaskProcessor;
import com.zyb.toc.service.service.ICallbackService;
import com.zyb.toc.service.service.IRedisCallbackService;
import com.zyb.toc.service.utils.JsonUtil;
import com.zyb.toc.spi.domain.CallbackBo;
import com.zyb.toc.spi.domain.enums.LocationEnum;
import com.zyb.toc.spi.domain.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * javadoc RedisCallbackServiceImpl
 * <p>
 *     redis 延迟回调信息处理 impl
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:26 PM
 * @version 1.0.0
 **/
@Service
@Slf4j
public class RedisCallbackServiceImpl implements IRedisCallbackService {

    private ITaskProcessor taskProcessor;
    @Autowired
    public RedisCallbackServiceImpl setTaskProcessor(ITaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
        return this;
    }

    private TransactionMixer transactionMixer;
    @Autowired
    public RedisCallbackServiceImpl setTransactionMixer(TransactionMixer transactionMixer) {
        this.transactionMixer = transactionMixer;
        return this;
    }

    private ICallbackService callbackService;
    @Autowired
    public RedisCallbackServiceImpl setCallbackService(ICallbackService callbackService) {
        this.callbackService = callbackService;
        return this;
    }

    @Override
    @Async(value = "asyncTaskExecutor")
    public void delayBackAsync(String data) {
        this.delayBack(data);
    }

    @Override
    public void delayBack(String data) {
        if(log.isInfoEnabled()){
            log.info("delay task [{}] timeout ", data);
        }
        taskProcessor.findByTaskId(data).ifPresentOrElse(
                t -> {
                    t.setStatus(StatusEnum.TIMEOUT.getStatus()).setLocation(LocationEnum.RECORD.getLocation());
                    final var backup = TaskConverter.entity2record(t);
                    transactionMixer.actionProxy(()-> taskProcessor.delete(t), () -> taskProcessor.save(backup));
                    callbackService.callbackAsync(backup, JsonUtil.parseObject(t.getCallback(), CallbackBo.class));
                },
                () -> log.error("cant find task by task id[{}]", data)
        );
    }
}
