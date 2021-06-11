package com.zyb.toc.web.controller;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TestCtrl {

    @GetMapping(value = "/test/callback/**")
    public String testCallback(HttpServletRequest request){

        var path = request.getRequestURI();
        var params = Splitter.on("/").trimResults().omitEmptyStrings().splitToList(path).stream().skip(3L).collect(Collectors.toList());
        log.error("path = [{}]", String.join("-", params));
        final var timeout = new Random().nextInt(20) + 1L;
        return String.join(" ", params) + " : " + timeout + " seconds";
    }
}
