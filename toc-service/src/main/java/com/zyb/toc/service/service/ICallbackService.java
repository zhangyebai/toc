package com.zyb.toc.service.service;

import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import com.zyb.toc.spi.domain.CallbackBo;

/**
 * javadoc ICallbackService
 * <p>
 *     超时回调
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 9:17 AM
 * @version 1.0.0
 **/
public interface ICallbackService {

    /**
     * javadoc callback
     * @apiNote 处理回调
     *
     * @param message 消息内容
     * @param bo 回调信息
     * @return int
     * @author zhang yebai
     * @date 2021/6/7 9:18 AM
     **/
    int callback(String message, CallbackBo bo);

    /**
     * javadoc callbackAsync
     * @apiNote 处理回调, 异步调用
     *
     * @param entity 超时任务记录
     * @param bo 回调信息
     * @author zhang yebai
     * @date 2021/6/7 10:50 AM
     **/
    void callbackAsync(TaskRecordEntity entity, CallbackBo bo);

    /**
     * javadoc http
     * @apiNote http 回调
     *
     * @param message 消息内容
     * @param url 回调url
     * @return int
     * @author zhang yebai
     * @date 2021/6/7 9:20 AM
     **/
    int http(String message, String url);

    /**
     * javadoc kafka
     * @apiNote kafka回调
     *
     * @param message 消息内容
     * @param topic 队列主题
     * @return int
     * @author zhang yebai
     * @date 2021/6/7 9:20 AM
     **/
    int kafka(String message, String topic);
}
