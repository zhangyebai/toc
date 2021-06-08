package com.zyb.toc.service.function;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * javadoc TransactionMixer
 * <p>
 *     事务混合器
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 4:54 PM
 * @version 1.0.0
 **/
@Component
public class TransactionMixer extends FunctionProxy{

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void actionProxy(ActionFunction af, ActionFunction... afs) {
        super.actionProxy(af, afs);
    }
}
