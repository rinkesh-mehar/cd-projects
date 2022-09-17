package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.DRKBugDto;
import in.cropdata.cdtmasterdata.service.DRKBugFixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RinkeshKM
 * @Date 10/12/20
 */

@RestController
@RequestMapping("/drk")
//@CrossOrigin("http://localhost:4200")
public class DRKBugFixController {

    @Autowired
    DRKBugFixService drkBugFixService;

    @GetMapping("/bug-fix")
    public ResponseEntity<DRKBugDto> fixBug(String tableName) {
        return drkBugFixService.fixBug(tableName);

    }
}
