package com.zyb.toc.spi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * javadoc StatusEnum
 * <p>
 *     任务状态枚举
 * <p>
 * @author zhang yebai
 * @date 2021/6/3 9:59 PM
 * @version 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 创建
     **/
    NEW(1),

    /**
     * 转移到REDIS预处理
     **/
    REDIS(2),

    /**
     * 超时触发
     **/
    TIMEOUT(3),

    /**
     * 回调完成
     **/
    CALLBACK(4),

    /**
     * 取消
     **/
    CANCEL(5),
    ;

    private final int status;

}
