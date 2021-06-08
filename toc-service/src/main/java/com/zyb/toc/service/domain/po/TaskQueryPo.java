package com.zyb.toc.service.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * javadoc TaskQueryPo
 * <p>
 *     task查询po
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:23 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class TaskQueryPo {

    /**
     * 位置
     **/
    private Integer location;

    /**
     * 状态
     **/
    private Integer status;

    /**
     * target time stamp
     **/
    private LocalDateTime tts;
}
