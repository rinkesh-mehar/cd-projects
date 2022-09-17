package com.krishi.controller;

import com.krishi.service.SmsServiceImpl;
import com.krishi.service.TaskGeneratorImpl;
import com.krishi.service.TaskPendingImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskGeneratorImpl taskGenerator;

    @Autowired
    private TaskPendingImpl taskPending;

    @Autowired
    private SmsServiceImpl smsService;

    @RequestMapping(path = "/generator", method = RequestMethod.GET, produces = "application/json")
    public boolean generateTask() throws Exception {
       return taskGenerator.generateTask();
    }

    @RequestMapping(path = "/pending", method = RequestMethod.GET, produces = "application/json")
    public boolean pendingTask() throws Exception {
       return taskPending.updateExistingTask();
    }

    @RequestMapping(path = "/sms", method = RequestMethod.GET, produces = "application/json")
    public boolean smsService(){
       return smsService.prepareSms();
    }
}
