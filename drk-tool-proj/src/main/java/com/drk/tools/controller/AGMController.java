package com.drk.tools.controller;

import com.drk.tools.service.AGMService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/rights")
public class AGMController {

    private static final Logger logger = LoggerFactory.getLogger(AGMController.class);

    @Autowired
    private AGMService agmService;

    @GetMapping(value = {"/addUpdateRights","/addUpdateRights/{count}"})
    public ResponseEntity<String> addUpdateRight(@PathVariable (name = "count", required = false) Integer count) {
        return agmService.addUpdateRight(count);
    }

    @PostMapping(value = "/addUpdateRights")
    public ResponseEntity<String> addUpdateRightForPostMapping(HttpServletRequest request) throws IOException {
        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper ob = new ObjectMapper();
        Map<String, Object> map = ob.readValue(collect, Map.class);
        return agmService.addUpdateRight(map);
    }

}
