package com.krishi.controller;

import com.krishi.model.Response;
import com.krishi.service.ScoutService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
//@RequestMapping("/gateway/scheduler")
public class ScoutController {
    private static final Logger LOGGER = LogManager.getLogger(ScoutController.class);

    @Autowired
    private ScoutService scoutService;

    @RequestMapping(path = "/dayEndSync/{userId}", method = RequestMethod.POST, produces = "application/json")
    public Response getDayEndSync(@RequestBody String dayEndSync,@PathVariable("userId") Integer userId) {
        LOGGER.info("API stated, no request parameter");
        Response response = scoutService.saveScoutOnDB(dayEndSync,userId);
        LOGGER.info("API end, no response");
        return response;
    }
}
