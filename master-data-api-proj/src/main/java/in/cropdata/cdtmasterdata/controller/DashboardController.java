package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.Dashboard;
import in.cropdata.cdtmasterdata.dto.DashboardHeader;
import in.cropdata.cdtmasterdata.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 14/09/20
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @PostMapping("/lead-data")
    public List<Dashboard> getLeadData(@RequestBody Dashboard dashboard) {
        return dashboardService.getLeadData(dashboard);
    }
    
    @PostMapping("/over-data")
    public Dashboard getOverallData(@RequestBody Dashboard dashboard) {
        return dashboardService.getCommodityArea(dashboard);
    }

    @PostMapping("/header-data")
    public DashboardHeader getHeaderData(@RequestBody DashboardHeader dashboard){
        return dashboardService.getHeaderData(dashboard);
    }
}
