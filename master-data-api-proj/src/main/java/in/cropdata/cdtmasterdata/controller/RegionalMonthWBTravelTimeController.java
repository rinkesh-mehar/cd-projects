package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.RegionalMonthWBTravelTime;
import in.cropdata.cdtmasterdata.model.vo.RegionalMonthWBTravelTimeVO;
import in.cropdata.cdtmasterdata.service.RegionalMonthWBTravelTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.controller
 * @date 09/11/20
 * @time 11:30 AM
 */
@RestController
@RequestMapping("/regional/monthly-wb-travel-time")
public class RegionalMonthWBTravelTimeController
{
    @Autowired
    private RegionalMonthWBTravelTimeService regionalMonthWBTravelTimeService;

    @GetMapping("/list")
       public List<RegionalMonthWBTravelTime> getRegionalMonthWBTravelTime(){

            return regionalMonthWBTravelTimeService.getListOfRegionalMonthWBTravelTime();
        }

    @GetMapping()
    public Page<RegionalMonthWBTravelTimeVO> getRegionalMonthWBTravelTimeWithPage(@RequestParam("page") int page,
                                                                                  @RequestParam("size") int size,
                                                                                  @RequestParam("isValid") int isValid,
                                                                                  @RequestParam(required = false, defaultValue = "") String searchText)
    {
        return regionalMonthWBTravelTimeService.getListOfRegionalMonthWBTravelTimeWithPage(page,size,searchText, isValid);
    }

    @PostMapping("/add")
    public ResponseMessage storeRegionalMonthlyTravelTime(@RequestBody RegionalMonthWBTravelTime regionalMonthWBTravelTime){
        if (Objects.isNull(regionalMonthWBTravelTime)){
            return null;
        } else
        {
            return regionalMonthWBTravelTimeService.saveRegionalMonthlyTravelTime(regionalMonthWBTravelTime);
        }
    }

    @GetMapping("/{id}")
    public RegionalMonthWBTravelTime findWeatherParamsById(@PathVariable Integer id) {
        return regionalMonthWBTravelTimeService.getRegionalMonthlyTravelTime(id);
    }//findWeatherParamsById

    @PutMapping("/{id}/update")
    public ResponseMessage updateRegionVarietyById(@PathVariable int id, @RequestBody RegionalMonthWBTravelTime regionalMonthWBTravelTime) {
        return regionalMonthWBTravelTimeService.updateRegionVarietyById(id, regionalMonthWBTravelTime);
    }// updateRegionVarietyById
}
