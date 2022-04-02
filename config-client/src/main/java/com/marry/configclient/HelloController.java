package com.marry.configclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mal
 * @date 2022-03-11 16:32
 */
@RestController
@RefreshScope
public class HelloController {

    @Value("${sang1:}")
    String sang;

    @Autowired
    Environment env;

    @RequestMapping("/sang1")
    public String sang() {
        return this.sang;
    }
    @RequestMapping("/sang2")
    public String sang2() {
        return env.getProperty("sang", "默认值");
    }

}
