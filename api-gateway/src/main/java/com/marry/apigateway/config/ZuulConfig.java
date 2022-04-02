package com.marry.apigateway.config;

import com.marry.apigateway.filter.PermisFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mal
 * @date 2022-03-10 15:24
 */
//@Configuration
public class ZuulConfig {

    @Bean
    PermisFilter permisFilter() {
        return new PermisFilter();
    }
}
