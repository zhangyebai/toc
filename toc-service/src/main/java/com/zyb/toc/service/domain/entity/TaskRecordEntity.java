package com.zyb.toc.service.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * javadoc TaskEntity
 * <p>
 *     任务 记录 entity
 * <p>
 * @author zhang yebai
 * @date 2021/6/3 9:14 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
@Table(name = "task_record")
@Entity
public class TaskRecordEntity implements Serializable {
    private static final long serialVersionUID = 598188930207275152L;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务id
     **/
    private String taskId;

    /**
     * 消息内容
     */
    private String message;


    /**
     * 回调参数
     **/
    private String callback;

    /**
     * 位置
     */
    private Integer location;

    /**
     * 处理状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime cts;

    /**
     * 更新时间
     */
    private LocalDateTime uts;

    /**
     * 触发时间
     **/
    private LocalDateTime tts;

    /**
     * 从任务队列移除时间
     * 归档时间
     **/
    private LocalDateTime mts;
}
