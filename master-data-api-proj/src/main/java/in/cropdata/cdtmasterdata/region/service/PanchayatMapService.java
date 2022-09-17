package in.cropdata.cdtmasterdata.region.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHsCodeInfDto;
import in.cropdata.cdtmasterdata.region.model.PanchayatMap;
import in.cropdata.cdtmasterdata.region.vo.panchayatMapVO;
import in.cropdata.cdtmasterdata.repository.PanchayatMapRepository;
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

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.service
 * @date 12/09/20
 * @time 11:12 AM
 */
@Service
public class PanchayatMapService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PanchayatMapService.class);

    @Autowired
    private PanchayatMapRepository panchayatMapRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

   public Page<panchayatMapVO> getPanchayatRegion(int page,int size,int isValid,String searchText,String missing){
	   try {
		   searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("regionID").descending());
			Page<panchayatMapVO> panchyatMapList = null;
		   if("0".equals(missing)) {
	       if (isValid == 0) {
	    	   panchyatMapList =  panchayatMapRepository.getPanchayatRegionInvalidated(sortedByIdDesc, searchText);
	       } else {
	    	   panchyatMapList =  panchayatMapRepository.getPanchayatRegion(sortedByIdDesc, searchText);
	       }
		   }else {
			   System.err.println("inside else...");
			   if (isValid == 0) {
		    	   panchyatMapList =  panchayatMapRepository.getPanchayatRegionMissingInvalidated(sortedByIdDesc, searchText);
		       } else {
		    	   panchyatMapList =  panchayatMapRepository.getPanchayatRegionMissing(sortedByIdDesc, searchText);
		       }  
		   }
	       return panchyatMapList;
	} catch (Exception e) {
		throw e;
	}
    }

    public ResponseMessage savePanchayatMap(PanchayatMap panchayatMap){
       try
       {
           LOGGER.info("Start validation.....");
           if (panchayatMap != null){

               panchayatMapRepository.save(panchayatMap);
               return responseMessageUtil.sendResponse(true, "regional_panchayat_map" + APIConstants.RESPONSE_ADD_SUCCESS, "");
           } else {
               return responseMessageUtil.sendResponse(true, "regional_panchayat_map" + APIConstants.RESPONSE_NO_RECORD_FOUND, "");

           }

       }catch (Exception e){
           LOGGER.info("Something wrong..... {}",e.getMessage());
           return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
       }

    }
}
