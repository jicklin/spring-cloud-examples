package com.marry.feignconsumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.marry.model.Book;

import java.util.Map;

/**
 * @author mal
 * @date 2022-03-02 17:00
 */
@FeignClient("hello-service")
public interface HelloService {

    @GetMapping("/hello")
    String hello();

    @GetMapping(value = "/hello1")
    String hello(@RequestParam("name") String name);

    @GetMapping(value = "/hello2")
    Book hello(@RequestHeader("name") String name, @RequestHeader("author") String author, @RequestHeader("price") Integer price);


    @PostMapping(value = "/hello3")
    String hello(@RequestBody Book book);


    @GetMapping("/hello4/{name}")
    String hello4(@PathVariable("name") String name);


    @PostMapping("/hello5")
    String hello5( String name);

    @PostMapping("/hello5")
    String hello6(@RequestParam Map param);



}
