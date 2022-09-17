package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalMonthWBTravelTime;
import in.cropdata.cdtmasterdata.model.vo.RegionalMonthWBTravelTimeVO;
import in.cropdata.cdtmasterdata.repository.RegionalMonthWBTravelTimeRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.service
 * @date 09/11/20
 * @time 11:54 AM
 */
@Service
public class RegionalMonthWBTravelTimeService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionalMonthWBTravelTimeService.class);

    @Autowired
    private RegionalMonthWBTravelTimeRepository regionalMonthWBTravelTimeRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public List<RegionalMonthWBTravelTime> getListOfRegionalMonthWBTravelTime(){
        LOGGER.info("Fetching Regional-Month-WB-Travel-Time records....");
        return regionalMonthWBTravelTimeRepository.findAll();
    }

    public Page<RegionalMonthWBTravelTimeVO> getListOfRegionalMonthWBTravelTimeWithPage(int page, int size, String searchText, int isValid){

        try
        {
            searchText = "%" + searchText + "%";
            Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").descending());
            Page<RegionalMonthWBTravelTimeVO> regionalMonthWBTravelTimeVOS;
            if (isValid == 0) {
             regionalMonthWBTravelTimeVOS = regionalMonthWBTravelTimeRepository.getRegionalMonthWBTravelTimeInvalidated(sortedByIdAsc, searchText);
            } else {
                regionalMonthWBTravelTimeVOS = regionalMonthWBTravelTimeRepository.getRegionalMonthWBTravelTime(sortedByIdAsc, searchText);
            }
            return regionalMonthWBTravelTimeVOS;
        }catch (Exception e){
            LOGGER.error("Something is wrong {}", e.getMessage());
            throw e;
        }
    }

    public ResponseMessage saveRegionalMonthlyTravelTime(RegionalMonthWBTravelTime regionalMonthWBTravelTime){
        try
        {
            if(regionalMonthWBTravelTime != null){
                regionalMonthWBTravelTimeRepository.save(regionalMonthWBTravelTime);
                return responseMessageUtil.sendResponse(true, "Regional Monthly Travel Time" + APIConstants.RESPONSE_ALREADY_EXIST, "");
            } else {
                return responseMessageUtil.sendResponse(false, "", "Regional Monthly Travel Time" + APIConstants.RESPONSE_ALREADY_EXIST);
            }
        }catch (Exception e){
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }
    
    public RegionalMonthWBTravelTime getRegionalMonthlyTravelTime(Integer id){
        Optional<RegionalMonthWBTravelTime> regionalMonthWBTravelTime = null;
        try
        {
            if (id != null){
                regionalMonthWBTravelTime = 
                        regionalMonthWBTravelTimeRepository.findById(id);
                if (regionalMonthWBTravelTime.isEmpty()){
                    throw new DoesNotExistException("Regional-Monthly-Travle-Time" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
                }
            }
        }catch (Exception e){
           throw e;
        }

        return regionalMonthWBTravelTime.get();
    }

    public ResponseMessage updateRegionVarietyById(int id, RegionalMonthWBTravelTime regionalMonthWBTravelTime) {
        try {
            Optional<RegionalMonthWBTravelTime> foundVariety = regionalMonthWBTravelTimeRepository.findById(id);

            if (foundVariety.isPresent()) {

                regionalMonthWBTravelTime.setId(id);
                regionalMonthWBTravelTimeRepository.save(regionalMonthWBTravelTime);

                return responseMessageUtil.sendResponse(true,
                        "Regional-Monthly-Travel-Time" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            } else {
                return responseMessageUtil.sendResponse(false, "",
                        "Regional-Monthly-Travel-Time" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
            }
        } catch (Exception e) {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// updateRegionVarietyById
}
