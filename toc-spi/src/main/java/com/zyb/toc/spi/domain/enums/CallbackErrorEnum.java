package com.zyb.toc.spi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * javadoc CallbackErrorEnum
 * <p>
 *     回调error code
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 9:28 AM
 * @version 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum CallbackErrorEnum {


    /**
     * 回调成功
     **/
    CALLBACK_OK(0),

    /**
     * 不支持的投递方式
     **/
    NOT_SUPPORTED_METHOD(1),

    /**
     * 处理过程发生异常
     **/
    EXCEPTION(2),

    /**
     * http 回调失败
     **/
    HTTP_ERROR(3),

    /**
     * http 回调接口返回值不正确
     **/
    HTTP_RESPONSE_ERROR(4),

    /**
     * 队列回调失败
     **/
    QUEUE_ERROR(5),

    /**
     * 主动取消
     **/
    CANCEL(6),


    ;

    private final int error;

}
