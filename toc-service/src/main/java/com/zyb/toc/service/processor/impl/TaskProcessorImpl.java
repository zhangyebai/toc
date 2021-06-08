package com.zyb.toc.service.processor.impl;

import com.zyb.toc.service.domain.entity.TaskEntity;
import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import com.zyb.toc.service.domain.po.TaskQueryPo;
import com.zyb.toc.service.processor.ITaskProcessor;
import com.zyb.toc.service.processor.repository.TaskRecordRepository;
import com.zyb.toc.service.processor.repository.TaskRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * javadoc TaskProcessorImpl
 * <p>
 *     task processor impl
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:28 PM
 * @version 1.0.0
 **/
@Service
public class TaskProcessorImpl implements ITaskProcessor {

    private TaskRepository taskRepository;
    @Autowired
    public TaskProcessorImpl setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    private TaskRecordRepository recordRepository;
    @Autowired
    public TaskProcessorImpl setRecordRepository(TaskRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        return this;
    }

    @Override
    public Stream<TaskEntity> streamTasksByQuery(TaskQueryPo po) {
        return listTasksByQuery(po).stream();
    }

    @Override
    public List<TaskEntity> listTasksByQuery(TaskQueryPo po) {
        return taskRepository.findAll((r, q, cb) -> {
            final List<Predicate> predicates = Lists.newArrayList();
            if(Objects.nonNull(po.getLocation())){
                predicates.add(cb.and(cb.equal(r.get("location"), po.getLocation())));
            }
            if(Objects.nonNull(po.getStatus())){
                predicates.add(cb.and(cb.equal(r.get("status"), po.getStatus())));
            }
            if(Objects.nonNull(po.getTts())){
                predicates.add(cb.and(cb.lessThanOrEqualTo(r.get("tts"), po.getTts())));
            }
            final var ps = new Predicate[predicates.size()];
            predicates.toArray(ps);
            return cb.and(ps);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskEntity update(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskEntity delete(TaskEntity entity) {
        taskRepository.delete(entity);
        return entity;
    }

    @Override
    public Optional<TaskEntity> findByTaskId(String taskId) {
        if(StringUtils.isNotBlank(taskId)){
            return taskRepository.findByTaskId(taskId);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskRecordEntity save(TaskRecordEntity entity) {
        return recordRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskRecordEntity update(TaskRecordEntity entity) {
        return recordRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TaskRecordEntity delete(TaskRecordEntity entity) {
        recordRepository.delete(entity);
        return entity;
    }
}
