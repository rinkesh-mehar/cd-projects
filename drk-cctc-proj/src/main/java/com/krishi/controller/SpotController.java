package com.krishi.controller;

import com.krishi.model.ResponseMessage;
import com.krishi.service.SpotListService;
import com.krishi.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class SpotController {

    @Autowired
    SpotService spotService;

    @Autowired
    SpotListService spotListService;


    @RequestMapping(path="/leadCallingSpotDetail/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMessage getLeadCallingDetail(@PathVariable("taskId") String taskId) {
        return spotService.getLeadCallingDetail(taskId);
    }

    @RequestMapping(path="/spot/getLeadCallingVillageListByDistrictId/{districtId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMessage getLeadCallingVillageList(@PathVariable("districtId") Integer districtId) {
        return spotListService.getLeadCallingVillageList(districtId);
    }

    @RequestMapping(path="/getSpotCommodityList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMessage getLeadCallingCommodityList() {
        return spotService.getLeadCallingCommodityList();
    }

}
