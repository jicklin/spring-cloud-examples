package com.marry.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author mal
 * @date 2022-03-02 16:21
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello() {
        int i = 1 / 0;
        ResponseEntity<String> entity = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class);
        return entity.getBody();
    }

    public String error(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return "报错了";
    }
}
