package com.zyb.toc.service.processor.repository;

import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * javadoc TaskRecordRepository
 * <p>
 *     task record repository
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:17 PM
 * @version 1.0.0
 **/
@Repository
public interface TaskRecordRepository extends JpaRepository<TaskRecordEntity, Long>, JpaSpecificationExecutor<TaskRecordEntity> {
}
