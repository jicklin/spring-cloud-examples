package com.marry.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mal
 * @date 2022-03-10 16:16
 */
@RestController
public class HelloController {

    @RequestMapping("/local")
    public String hello() {
        return "hello zuul";
    }
}
