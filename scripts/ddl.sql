CREATE TABLE `task`
(
    `id`       bigint unsigned                         NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `task_id`  varchar(40) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '任务id',
    `message`  varchar(512) COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
    `callback` text COLLATE utf8mb4_general_ci COMMENT '回调参数',
    `location` tinyint unsigned                        NOT NULL COMMENT '位置',
    `status`   tinyint unsigned                                 DEFAULT NULL COMMENT '处理状态',
    `cts`      timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `uts`      timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `tts`      timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '触发时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_t` (`task_id`) USING BTREE,
    KEY `idx_tts` (`tts`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT '延迟任务表';

CREATE TABLE `task_record`
(
    `id`       bigint unsigned                                               NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `task_id`  varchar(40) COLLATE utf8mb4_general_ci                        NOT NULL COMMENT '任务id',
    `message`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
    `callback` text COLLATE utf8mb4_general_ci COMMENT '回调参数',
    `location` tinyint unsigned                                              NOT NULL COMMENT '位置',
    `status`   tinyint unsigned                                                       DEFAULT NULL COMMENT '处理状态',
    `cts`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `uts`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `tts`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '触发时间',
    `mts`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '归档时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT '延迟任务完成记录表';