package com.zyb.toc.service.controller;

import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class TestCtrl {

    @Autowired
    private RDelayedQueue<String> delayedQueue;

    @GetMapping(value = "/test/create")
    public String testCreate(){
        final var id = String.valueOf(System.currentTimeMillis());
        final var timeout = new Random().nextInt(20) + 1L;
        delayedQueue.offer(id, timeout, TimeUnit.SECONDS);
        return id + " : " + timeout + " seconds";
    }
}
