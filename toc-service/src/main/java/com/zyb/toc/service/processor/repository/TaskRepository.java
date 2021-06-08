package com.zyb.toc.service.processor.repository;

import com.zyb.toc.service.domain.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * javadoc TaskRepository
 * <p>
 *     task repository
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:27 PM
 * @version 1.0.0
 **/
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

    /**
     * javadoc findByTaskId
     * @apiNote
     *
     * @param taskId task id
     * @return java.util.Optional<com.galaxy.toc.service.domain.entity.TaskEntity>
     * @author zhang yebai
     * @date 2021/6/4 5:28 PM
     **/
    Optional<TaskEntity> findByTaskId(String taskId);

}
