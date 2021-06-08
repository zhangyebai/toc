package com.zyb.toc.spi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * javadoc LocationEnum
 * <p>
 *     task store location enum
 * <p>
 * @author zhang yebai
 * @date 2021/6/3 9:54 PM
 * @version 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum LocationEnum {
    /**
     * db
     **/
    DB(1),

    /**
     * redis
     **/
    REDIS(2),

    /**
     * record db
     **/
    RECORD(3),
    ;

    private final int location;
}
