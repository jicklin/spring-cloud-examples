package com.marry.feignconsumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mal
 * @date 2022-03-10 16:30
 */
@Controller
public class Hello2Controller {

    @RequestMapping("/hello7")
    private String hello7() {
        return "redirect:/hello";
    }
}
