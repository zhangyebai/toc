package com.zyb.toc.service.controller;

import com.zyb.toc.service.service.ITaskService;
import com.zyb.toc.spi.api.TocTaskSpi;
import com.zyb.toc.spi.domain.TaskBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * javadoc TocTaskSpiImpl
 * <p>
 *     toc task controller
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 2:34 PM
 * @version 1.0.0
 **/
@RestController
public class TocTaskSpiImpl implements TocTaskSpi {

    private ITaskService taskService;
    @Autowired
    public TocTaskSpiImpl setTaskService(ITaskService taskService) {
        this.taskService = taskService;
        return this;
    }

    @Override
    public TaskBo create(TaskBo bo) {
        return taskService.create(bo);
    }

    @Override
    public TaskBo cancel(String taskId) {
        return taskService.cancel(taskId);
    }
}
