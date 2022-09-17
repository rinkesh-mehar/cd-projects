package com.drkrishi.rlt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;


/**
 * @author RinkeshKM
 * @Date 05/08/21
 */

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        return new RestTemplate(requestFactory);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return mapper;
    }
}
