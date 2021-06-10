package com.zyb.toc.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * javadoc TaskController
 * <p>
 *
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 4:16 PM
 * @version 1.0.0
 **/
@RestController
@Slf4j
public class TaskController {

    @GetMapping(value = "/test")
    public String test() throws InterruptedException {
        log.error("test invoke");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        return "test";
    }
}
