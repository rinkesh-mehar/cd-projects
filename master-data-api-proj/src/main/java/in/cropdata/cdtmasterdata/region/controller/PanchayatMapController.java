package in.cropdata.cdtmasterdata.region.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.region.model.PanchayatMap;
import in.cropdata.cdtmasterdata.region.service.PanchayatMapService;
import in.cropdata.cdtmasterdata.region.vo.panchayatMapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.controller
 * @date 12/09/20
 * @time 10:35 AM
 */
@RestController
public class PanchayatMapController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(PanchayatMapController.class);

    @Autowired
   private PanchayatMapService panchayatMapService;

    @GetMapping("/panchayat-region")
    public Page<panchayatMapVO> getPanchayatRegion(@RequestParam("page") int page,
			  									   @RequestParam("size") int size,
			  									   @RequestParam("isValid") int isValid,
			  									   @RequestParam(required = false, defaultValue = "") String searchText,
			  									   @RequestParam(required = false,defaultValue = "0") String missing){
        LOGGER.info("fetching existing panchayat region......");
        return panchayatMapService.getPanchayatRegion(page,size,isValid,searchText,missing);
    }

    @PostMapping("/add-panchayat")
    public ResponseMessage addPanchayatRegion(@RequestBody PanchayatMap panchayatMap){
        return panchayatMapService.savePanchayatMap(panchayatMap);
    }
}
