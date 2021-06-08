package com.zyb.toc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * javadoc TocServiceApplication
 * <p>
 *     toc service application
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 2:41 PM
 * @version 1.0.0
 **/
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class TocServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TocServiceApplication.class, args);
    }
}
