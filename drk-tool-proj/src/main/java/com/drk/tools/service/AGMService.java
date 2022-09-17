package com.drk.tools.service;

import com.drk.tools.config.AppConfig;
import com.drk.tools.exceptions.ResponseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

@Service
public class AGMService {

    private static final Logger logger = LoggerFactory.getLogger(AGMService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

    public ResponseEntity<String> addUpdateRight(Integer count) {
        ResponseEntity<String> responseEntity = null;
        try {
            URI uri = null;
            if(count == null){
                uri = new URI(appConfig.addUpdateRightURL);
            } else {
                uri = new URI(appConfig.addUpdateRightURL + "/"+ count);
            }

            responseEntity = restTemplate.getForEntity(uri, String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.info("exception in API call Add Update Right");
        }
        return responseEntity;
    }

    public ResponseEntity<String> addUpdateRight(Map<String, Object> data) {
        ResponseEntity<String> responseEntity = null;

        logger.info("map {}", data);
        try {
            URI uri = null;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
                uri = new URI(appConfig.addUpdateRightURL);
            HttpEntity<Map<String, Object>> requestEntity = null;
            requestEntity = new HttpEntity<>(data,headers);
            logger.info("Api request -> {}", requestEntity);

            responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);

            if (Objects.requireNonNull(responseEntity.getBody()).isBlank() || responseEntity.getBody().isEmpty()) {
                throw new ResponseNotFoundException("Right Body Not Found");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.info("exception in API call Add Update Right");
        }
        return responseEntity;
    }
}
