package com.zyb.toc.service.service;

/**
 * javadoc IRedisCallbackService
 * <p>
 *     redis 延迟回调信息处理
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:25 PM
 * @version 1.0.0
 **/
public interface IRedisCallbackService {

    /**
     * javadoc delayBackAsync
     * @apiNote 处理回调信息[异步]
     *
     * @param data 回调数据 {taskId}
     * @author zhang yebai
     * @date 2021/6/4 5:26 PM
     **/
    void delayBackAsync(String data);

    /**
     * javadoc delayBack
     * @apiNote 处理回调信息
     *
     * @param data 回调数据 {taskId}
     * @author zhang yebai
     * @date 2021/6/4 5:26 PM
     **/
    void delayBack(String data);
}
