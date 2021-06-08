package com.zyb.toc.service.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc DelayTaskPo
 * <p>
 *     延迟任务po
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:43 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class DelayTaskPo {

    /**
     * 任务id
     **/
    private String taskId;

    /**
     * 延迟时间 second
     **/
    private Long delay;

    /**
     * 当前时间戳
     **/
    private Long timestamp;
}
