package com.marry.feignconsumer.controller;

import com.marry.feignconsumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marry.model.Book;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mal
 * @date 2022-03-02 16:59
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;


    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }


    @RequestMapping("/hello1")
    public String hello1() {
        return helloService.hello("张三");
    }

    @RequestMapping(value = "/hello2")
    public Book hello2() throws UnsupportedEncodingException {
        Book book = helloService.hello("三国演义","罗贯中", 33);
        System.out.println(book);
        return book;
    }

    @RequestMapping("/hello3")
    public String hello3() {
        Book book = new Book();
        book.setName("红楼梦");
        book.setPrice(44);
        book.setAuthor("曹雪芹");
        return helloService.hello(book);
    }

    @RequestMapping("/hello4")
    public String hello4(String name) {
        return helloService.hello4(name);
    }

    @GetMapping("/hello5")
    public String hello5(String name) {
        return helloService.hello5(name);

    }

    @GetMapping("/hello6")
    private String hello6(String name) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "123");
        return helloService.hello6(param);

    }




}
