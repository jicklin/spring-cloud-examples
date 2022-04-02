package com.marry.provider.controller;

import com.marry.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mal
 * @date 2022-03-01 15:27
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/hello")
    public String index() {

        List<ServiceInstance> instances = discoveryClient.getInstances("hello-service");
        for (int i = 0; i < instances.size(); i++) {

            logger.info("/hello host:{},service_id:{}", instances.get(i).getHost(), instances.get(i).getServiceId());
        }
        return "hello service";


    }

    @GetMapping("/sayHello")
    public String sayHello(String name) {
        return "hello " + name;
    }

    @GetMapping("/getBook1")
    public Book book1() {
        return new Book("剑来", 10, "太监", "文艺出版社");
    }

    @PostMapping("/getBook2")
    public Book book2(@RequestBody Book book) {
        System.out.println(book);
        book.setPrice(1000);
        book.setAuthor("谁知道");
        return book;
    }

    @GetMapping("/hello1")
    public String hello1(String name) {
        return "hello:" + name;
    }

    @GetMapping("/hello2")
    public Book hello2(@RequestHeader String name, @RequestHeader String author, @RequestHeader Integer price) {

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        return book;
    }

    @PostMapping("/hello3")
    public String hello3(@RequestBody Book book) {
        return "书名为：" + book.getName() + ";作者为：" + book.getAuthor();

    }


    @GetMapping("/hello4/{name:[a-z]+}")
    public String hello4(@PathVariable("name") String name) {
        return "hello restful:" + name;
    }

    @PostMapping("/hello5")
    public String hello5(String name) {
        return "hello5:" + name;
    }


}
