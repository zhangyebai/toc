package com.zyb.toc.spi.api;

import com.zyb.toc.spi.domain.TaskBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * javadoc TocTaskSpi
 * <p>
 *     toc task spi
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 2:31 PM
 * @version 1.0.0
 **/
@FeignClient(value = "toc-service")
public interface TocTaskSpi {

    /**
     * javadoc create
     * @apiNote 超时任务创建
     *
     * @param bo 任务信息
     * @return com.galaxy.toc.spi.domain.TaskBo
     * @author zhang yebai
     * @date 2021/6/4 2:32 PM
     **/
    @PostMapping(value = "/toc-service/timeout/task")
    TaskBo create(@RequestBody TaskBo bo);

    /**
     * javadoc cancel
     * @apiNote 取消超时任务
     *
     * @param taskId 任务id
     * @return com.galaxy.toc.spi.domain.TaskBo
     * @author zhang yebai
     * @date 2021/6/7 2:10 PM
     **/
    @PutMapping(value = "/toc-service/timeout/task/{taskId}")
    TaskBo cancel(@PathVariable(value = "taskId") String taskId);
}
