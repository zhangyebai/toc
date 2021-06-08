package com.zyb.toc.spi.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc TaskBo
 * <p>
 *     task bo
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 2:27 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class TaskBo {

    private String taskId;

    /**
     * 创建时间戳
     **/
    private Long cts;

    /**
     * 目标时间戳
     **/
    private Long tts;

    /**
     * 消息内容
     * 500 字符以内
     **/
    private String message;

    /**
     * seconds since now
     **/
    private Long delay;

    /**
     * 回调参数
     **/
    private CallbackBo callback;
}
