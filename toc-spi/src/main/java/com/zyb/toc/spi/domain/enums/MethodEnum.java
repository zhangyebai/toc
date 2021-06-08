package com.zyb.toc.spi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * javadoc MethodEnum
 * <p>
 *     投递方式枚举
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 6:39 PM
 * @version 1.0.0
 **/
@AllArgsConstructor
@Getter
public enum MethodEnum {

    /**
     * 接口方式, 接口声明如下
     * {code}
     * POST http(s)://url/callback/xxx
     * int callback({"message" : "message content");
     * {/code}}
     **/
    HTTP(1),

    /**
     * kafka队列方式, 投递方式为字符串, 内容为:
     * {"message":"message content"}
     *
     **/
    KAFKA(2),
    ;

    private final int method;
}
