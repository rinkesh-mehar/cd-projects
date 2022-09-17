package com.drkrishi.usermanagement.config;

import com.drkrishi.usermanagement.properties.AppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

/**
 * @author Rinkesh KM
 * @Date 08/07/21
 */

@Configuration
public class BeanConfig {


    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Bean
    public AppProperties appProperties(){
        return new AppProperties();
    }

    @Bean
//   @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
