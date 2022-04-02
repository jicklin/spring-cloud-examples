package com.marry.consumer.controller;

import com.marry.consumer.service.HelloService;
import com.marry.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author mal
 * @date 2022-03-01 15:29
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HelloService helloService;

    @GetMapping("/gethello")
    public String getHello() {

        ResponseEntity<String> entity = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class);
        String body = entity.getBody();
        HttpStatus statusCode = entity.getStatusCode();
        int statusCodeValue = entity.getStatusCodeValue();
        HttpHeaders headers = entity.getHeaders();
        StringJoiner result = new StringJoiner("<hr>");
        result.add("Body:" + body);
        result.add("StatusCode:" + statusCode);
        result.add("StatusCodeValue:" + statusCodeValue);
        result.add("Headers:" + headers);
        return result.toString();



    }
    @GetMapping("/hystrixHello")
    public String hystrixHello() {
        return helloService.hello();
    }

    @GetMapping("/sayHello")
    public String sayHello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/sayHello?name={1}", String.class, "小黑");
        return responseEntity.getBody();
    }

    @GetMapping("/sayHello2")
    public String sayHello2() {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "小黄");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/sayHello?name={name}", String.class, param);
        return responseEntity.getBody();


    }
    @GetMapping("/sayHello3")
    public String sayHello3() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://HELLO-SERVICE/sayHello?name={name}").build().expand("小白").encode();
        URI uri = uriComponents.toUri();
        System.out.println(uri);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }

    @GetMapping("/getBook1")
    public Book getBook1() {
        ResponseEntity<Book> forEntity = restTemplate.getForEntity("http://HELLO-SERVICE/getBook1", Book.class);
        return forEntity.getBody();
    }

    @GetMapping("/book2")
    public Book getBook2() {
        Book forObject = restTemplate.getForObject("http://HELLO-SERVICE/getBook1", Book.class);
        return forObject;
    }

    @GetMapping("/book3")
    public Book getBook3() {
        Book book = new Book();
        book.setName("无敌兵王");
        ResponseEntity<Book> bookResponseEntity = restTemplate.postForEntity("http://HELLO-SERVICE/getBook2", book, Book.class);
        return bookResponseEntity.getBody();
    }

}
