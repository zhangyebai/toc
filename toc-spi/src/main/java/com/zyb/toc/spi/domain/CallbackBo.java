package com.zyb.toc.spi.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc CallbackPo
 * <p>
 *     回调信息
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 6:42 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class CallbackBo {

    /**
     * 回调方式
     **/
    private int method;

    /**
     * 回调内容,
     * 如果是http, 则callback为回调url
     * 如果是kafka, 则回调的callback为kafka topic
     **/
    private String callback;
}
