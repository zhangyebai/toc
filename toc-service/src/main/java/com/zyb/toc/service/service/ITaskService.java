package com.zyb.toc.service.service;

import com.zyb.toc.spi.domain.TaskBo;

/**
 * javadoc ITaskService
 * <p>
 *     延时任务service
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:58 PM
 * @version 1.0.0
 **/
public interface ITaskService {

    /**
     * javadoc create
     * @apiNote 创建延时任务
     *
     * @param bo 任务信息
     * @return com.galaxy.toc.spi.domain.TaskBo
     * @author zhang yebai
     * @date 2021/6/4 5:58 PM
     **/
    TaskBo create(TaskBo bo);

    /**
     * javadoc cancel
     * @apiNote 取消超时任务
     *
     * @param taskId 任务id
     * @return com.galaxy.toc.spi.domain.TaskBo
     * @author zhang yebai
     * @date 2021/6/7 2:11 PM
     **/
    TaskBo cancel(String taskId);
}
