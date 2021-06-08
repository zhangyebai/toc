package com.zyb.toc.service.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * javadoc IFunctionProxy
 * <p>
 *     functional interface proxy
 *     keep different transitions in a same transaction
 * <p>
 * @author zhang yebai
 * @date 2021/4/7 11:34 AM
 * @version 1.0.0
 **/
@SuppressWarnings(value = "unused")
public class FunctionProxy {


    /**
     * javadoc actionProxy
     * @apiNote action proxy
     *
     * @param f action function
     * @author zhang yebai
     * @date 2021/4/14 2:15 PM
     **/
    public void actionProxy(ActionFunction f){
        f.action();
    }

    /**
     * javadoc actionProxy
     * @apiNote actions proxy
     *
     * @param fl action function list
     * @author zhang yebai
     * @date 2021/4/14 2:16 PM
     **/
    public void actionProxy(List<ActionFunction> fl){
        Objects.requireNonNull(fl).forEach(ActionFunction::action);
    }

    /**
     * javadoc actionProxy
     * @apiNote actions proxy
     *
     * @param af action function
     * @param afs action functions
     * @author zhang yebai
     * @date 2021/4/14 2:16 PM
     **/
    public void actionProxy(ActionFunction af, ActionFunction...afs){
        af.action();
        for(ActionFunction f: afs){
            f.action();
        }
    }

    /**
     * javadoc proxy
     * @apiNote action proxy with return value
     *
     * @param s T producer
     * @return T
     * @author zhang yebai
     * @date 2021/4/14 2:17 PM
     **/
    public <T> T proxy(Supplier<T> s){
        return Objects.requireNonNull(s).get();
    }

    /**
     * javadoc proxy
     * @apiNote actions proxy with return value
     *
     * @param s Object Producer
     * @param ss Objects Producer
     * @return java.util.List<java.lang.Object>
     * @author zhang yebai
     * @date 2021/4/14 2:17 PM
     **/
    public List<Object> proxy(Supplier<?> s, Supplier<?> ...ss){
        final List<Object> r = new ArrayList<>();
        r.add(s.get());
        for(Supplier<?> f: ss){
            r.add(f.get());
        }
        return r;
    }

    /**
     * javadoc proxy
     * @apiNote actions proxy with return value
     *
     * @param sl Object Producer List
     * @return java.util.List<java.lang.Object>
     * @author zhang yebai
     * @date 2021/4/14 2:18 PM
     **/
    public List<Object> proxy(List<Supplier<?>> sl){
        return Objects.requireNonNull(sl).stream().map(Supplier::get).collect(Collectors.toList());
    }
}
