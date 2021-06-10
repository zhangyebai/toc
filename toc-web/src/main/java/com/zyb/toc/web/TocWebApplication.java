package com.zyb.toc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * javadoc TocWebApplication
 * <p>
 *     toc web
 * <p>
 * @author zhang yebai
 * @date 2021/6/7 2:07 PM
 * @version 1.0.0
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zyb.toc")
@EnableEurekaClient
public class TocWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TocWebApplication.class, args);
    }
}
