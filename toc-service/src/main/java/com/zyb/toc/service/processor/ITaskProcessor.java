package com.zyb.toc.service.processor;

import com.zyb.toc.service.domain.entity.TaskEntity;
import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import com.zyb.toc.service.domain.po.TaskQueryPo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * javadoc ITaskProcessor
 * <p>
 *     task processor
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:26 PM
 * @version 1.0.0
 **/
public interface ITaskProcessor {

    /**
     * javadoc streamTasksByQuery
     * @apiNote 按条件查询任务
     *
     * @param po 查询条件
     * @return java.util.stream.Stream<com.galaxy.toc.service.domain.entity.TaskEntity>
     * @author zhang yebai
     * @date 2021/6/4 4:26 PM
     **/
    Stream<TaskEntity> streamTasksByQuery(TaskQueryPo po);

    /**
     * javadoc listTasksByQuery
     * @apiNote 按条件查询任务
     *
     * @param po 查询条件
     * @return java.util.List<com.galaxy.toc.service.domain.entity.TaskEntity>
     * @author zhang yebai
     * @date 2021/6/4 4:26 PM
     **/
    List<TaskEntity> listTasksByQuery(TaskQueryPo po);

    TaskEntity save(TaskEntity entity);

    TaskEntity update(TaskEntity entity);

    TaskEntity delete(TaskEntity entity);

    Optional<TaskEntity> findByTaskId(String taskId);

    TaskRecordEntity save(TaskRecordEntity entity);

    TaskRecordEntity update(TaskRecordEntity entity);

    TaskRecordEntity delete(TaskRecordEntity entity);
}
