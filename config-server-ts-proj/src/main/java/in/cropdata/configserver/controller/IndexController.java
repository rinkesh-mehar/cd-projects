package in.cropdata.configserver.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

    @GetMapping(value = {"/"})
    public String index() {
        return "home";
    }
    
    @GetMapping(value = {"/homePage"})
    public String homePage() {
    	log.info("in Index Controller");
        return "home";
    }
    
    

    @GetMapping(value = {"/server_error"})
    public String triggerServerError() {
        "ser".charAt(30);
        return "index";
    }

    @PostMapping(value = {"/general_error"})
    public String triggerGeneralError() {
        return "index";
    }

}