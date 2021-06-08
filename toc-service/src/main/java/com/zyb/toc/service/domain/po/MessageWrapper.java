package com.zyb.toc.service.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc MessageWrapper
 * <p>
 *     message wrapper
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 9:25 AM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class MessageWrapper {

    private String message;
}
