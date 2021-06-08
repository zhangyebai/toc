package com.zyb.toc.service.service.impl;

import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import com.zyb.toc.service.domain.po.MessageWrapper;
import com.zyb.toc.service.processor.ITaskProcessor;
import com.zyb.toc.service.service.ICallbackService;
import com.zyb.toc.spi.domain.CallbackBo;
import com.zyb.toc.spi.domain.enums.CallbackErrorEnum;
import com.zyb.toc.spi.domain.enums.MethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * javadoc CallbackServiceImpl
 * <p>
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 9:18 AM
 * @version 1.0.0
 **/
@Service
@Slf4j
public class CallbackServiceImpl implements ICallbackService {

    private RestTemplate restTemplate;
    @Autowired
    public CallbackServiceImpl setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    private ITaskProcessor taskProcessor;
    @Autowired
    public CallbackServiceImpl setTaskProcessor(ITaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
        return this;
    }

    @Override
    public int callback(String message, CallbackBo bo) {
        try{
            if(MethodEnum.HTTP.getMethod() == bo.getMethod()){
                return http(message, bo.getCallback());
            }else if (MethodEnum.KAFKA.getMethod() == bo.getMethod()){
                return kafka(message, bo.getCallback());
            }else{
                return CallbackErrorEnum.NOT_SUPPORTED_METHOD.getError();
            }
        }catch (Exception ex){
            log.error("callback message[{}] on [{}] exception: ", message, bo, ex);
        }
        return CallbackErrorEnum.EXCEPTION.getError();
    }

    @Override
    @Async(value = "asyncTaskExecutor")
    public void callbackAsync(TaskRecordEntity entity, CallbackBo bo) {
        int code = callback(entity.getMessage(), bo);
        entity.setStatus(code);
        taskProcessor.update(entity);
    }

    @Override
    public int http(String message, String url) {
        try{
            final Integer code = restTemplate.postForObject(url, new MessageWrapper().setMessage(message), Integer.class);
            return Objects.nonNull(code) && code >= 0 ? CallbackErrorEnum.CALLBACK_OK.getError() : CallbackErrorEnum.HTTP_RESPONSE_ERROR.getError();
        }catch (Exception ex){
            log.error("handle call back[{}] within message[{}] exception: ", url, message, ex);
        }
        return CallbackErrorEnum.HTTP_ERROR.getError();
    }

    @Override
    public int kafka(String message, String topic) {
        // todo: 发送kafka回调消息
        return CallbackErrorEnum.EXCEPTION.getError();
    }
}
