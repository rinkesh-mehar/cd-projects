package in.cropdata.cdtmasterdata.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriQualityCommodityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityCommodityParameterVO;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;
import in.cropdata.cdtmasterdata.repository.AgriQualityCommodityParameterRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriQualityCommodityParameterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriQualityCommodityParameterService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriQualityCommodityParameterRepository agriQualityCommodityParameterRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public Page<AgriQualityCommodityParameterVO> getQualityCommodityParameterPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all quality commodity parameter info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("commodityId").descending());

			return agriQualityCommodityParameterRepository.getQualityCommodityParameterPagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Quality Commodity Parameter Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addQualityCommodityParameter(AgriQualityCommodityParameter agriQualityCommodityParameter) {
		LOGGER.info("Adding Quality Commodity Parameter...");
		try {
			
			List<AgriQualityCommodityParameter> agriQualityCommodityParameterList = new ArrayList<>();
			
			Integer[] qualityParameterIds = agriQualityCommodityParameter.getQualityParameterIds();
			
			LOGGER.info("qualityParameterIds...{} " + qualityParameterIds);
			
			for(Integer qualityParameterId : qualityParameterIds) {
				
				AgriQualityCommodityParameter addAgriQualityCommodityParameter = new AgriQualityCommodityParameter();
				
				addAgriQualityCommodityParameter.setCommodityId(agriQualityCommodityParameter.getCommodityId());
				addAgriQualityCommodityParameter.setParameterId(qualityParameterId);
				
				agriQualityCommodityParameterList.add(addAgriQualityCommodityParameter);
				
			}
			
			agriQualityCommodityParameterRepository.saveAll(agriQualityCommodityParameterList);
			
			return responseMessageUtil.sendResponse(true, "Quality Commodity Parameter " + APIConstants.RESPONSE_ADD_SUCCESS, "");
			
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateQualityCommodityParameter(AgriQualityCommodityParameter agriQualityCommodityParameter, Integer commodityId) {
		LOGGER.info("Updation Quality Commodity Parameter...");
		try {
			
			Integer[] existingQualityParameterIds = agriQualityCommodityParameterRepository.findParameterIdsByCommodityId(commodityId);
			
			LOGGER.info(existingQualityParameterIds.length + " : " + commodityId); 
			
			if(existingQualityParameterIds.length > 0) {
				
				Integer[] qualityParameterIds = agriQualityCommodityParameter.getQualityParameterIds();
				
				for(Integer id : qualityParameterIds) {
					LOGGER.info("id : " + id); 
				}
				
				// deleting the qualityParameterId which exist in DB but de-selected while editing the form. 
				 
				for (int i = 0; i < existingQualityParameterIds.length; i++) 
		        { 
		            int j; 
		              
		            for (j = 0; j < qualityParameterIds.length; j++) { 
		                if (existingQualityParameterIds[i] == qualityParameterIds[j]) { 
		                    break; 
		                }
		            }
		                
		            if (j == qualityParameterIds.length) {
		            	LOGGER.info(existingQualityParameterIds[i] + " * "); 	
		            	agriQualityCommodityParameterRepository.deleteByCommodityIdAndParameterId(commodityId,existingQualityParameterIds[i]);
		            }
		        } 
				
				for(Integer qualityParameterId : qualityParameterIds) {
					AgriQualityCommodityParameter qualityCommodityParameterUpdate = agriQualityCommodityParameterRepository.findByCommodityIDAndParameterID(commodityId,qualityParameterId);
					// Do nothing if already exist else create new record
					if(qualityCommodityParameterUpdate == null) {
						LOGGER.info("Insert quality commodity parameter...{} " + commodityId + ":" + qualityParameterId); 
						AgriQualityCommodityParameter qualityCommodityParameterInsert = new AgriQualityCommodityParameter();
						qualityCommodityParameterInsert.setCommodityId(commodityId);
						qualityCommodityParameterInsert.setParameterId(qualityParameterId);
						agriQualityCommodityParameterRepository.save(qualityCommodityParameterInsert);
					}
					
				}
				
			}else {
				return responseMessageUtil.sendResponse(false,
						"Quality Commodity Parameter Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + commodityId, "");
			}
			return responseMessageUtil.sendResponse(true, "Quality Commodity Parameter" + APIConstants.RESPONSE_UPDATE_SUCCESS + commodityId, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public List<AgriQualityParameterVo> getParameterListByCommodityId(Integer commodityId) {
		try {
			LOGGER.info("getting quality commodity parameter by id...");
			return agriQualityCommodityParameterRepository.getParameterListByCommodityId(commodityId);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Quality Parameter Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + commodityId);
		}
	}
		
}
