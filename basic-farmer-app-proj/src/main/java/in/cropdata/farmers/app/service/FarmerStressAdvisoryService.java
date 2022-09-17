/**
 * 
 */
package in.cropdata.farmers.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.farmers.app.DTO.FarmerNotifyDTO;
import in.cropdata.farmers.app.constants.APIConstants;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.FarmerDeviceToken;
import in.cropdata.farmers.app.drk.repository.DrkFarmCaseRepository;
import in.cropdata.farmers.app.drk.repository.FarmerDeviceTokenRepository;
import in.cropdata.farmers.app.repository.DAO.FarmerAppDAO;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Service
public class FarmerStressAdvisoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerStressAdvisoryService.class);

	@Autowired
	private FarmerAppDAO farmerAppDAO;

	@Autowired
	private FarmerDeviceTokenRepository farmerDetailsRepository;

	@Autowired
	private DrkFarmCaseRepository drkFarmCaseRepository;

	@Autowired
	private NotificationService notificationService;

	public Map<String,Object> notifyStress(String deviceToken) {

		Map<String, Object> responseMap = new HashMap<>();
		Optional<FarmerDeviceToken> farmer = farmerDetailsRepository.findByDeviceToken(deviceToken);
		Map<String, Object> dataMap = null;
        List<Object> dataMapList = new ArrayList<>();
        List<String> caseList = new ArrayList<>();
		try {
			if (farmer.isPresent()) {
				List<Map<String, Object>> caseIdList = farmerAppDAO.getCaseByFarmerID(farmer.get().getFarmerID());
				if (caseIdList != null && !caseIdList.isEmpty()) {
					List<FarmerNotifyDTO> dataList = drkFarmCaseRepository.getFarmerNotification(caseIdList);
					if (dataList != null && !dataList.isEmpty()) {
						for (FarmerNotifyDTO dto : dataList) {
							dataMap = new HashMap<>();
							caseList.add(dto.getCaseId());
							dataMap.put("caseID", dto.getCaseId());
							dataMap.put("body", dto.getRunningText());
							dataMap.put("type", APIConstants.ADVISORY_NOTIFICATION);
							dataMap.put("title", APIConstants.COMPANY_NAME);
							dataMap.put("imageURL", dto.getCommodityPhoto());
							dataMap.put("CommodityName", dto.getCommodityName());							
							dataMapList.add(dataMap);
							notificationService.sendNotification(APIConstants.ADVISORY_NOTIFICATION, dto.getDeviceToken(),dataMap);
						}
						farmerAppDAO.updateDccDailyStressCase(caseList);
						responseMap.put("status", true);
						responseMap.put("message", "Stress notification sent successfully!!");
						responseMap.put("data", dataMapList);
					} else {
						responseMap.put("status", false);
						responseMap.put("message", "Notification data not found !!");
						responseMap.put("error", ErrorConstants.NO_DATA_FOUND);
					}

				} else {
					responseMap.put("status", false);
					responseMap.put("message", "CaseId's not found !!");
					responseMap.put("error", ErrorConstants.NO_DATA_FOUND);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
         return responseMap;
		
	}

	

}
