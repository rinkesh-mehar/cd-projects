package in.cropdata.toolsuite.drk.services.fl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.exceptions.DataInsertionException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.model.cases.Farm;
import in.cropdata.toolsuite.drk.repository.fl.FarmRepository;

@Service
public class FarmService {

	@Autowired
	private FarmRepository farmRepository;
	
	@Autowired
	private ResourceDao resourceDao;

	public Map<String, Object> saveFarm(List<Farm> farmList) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		if (farmList != null && farmList.size() > 0) {
			
			for (Farm farm : farmList) {
				
			int regionId = this.resourceDao.getRegionIdByVillageCode(farm.getVillageCode());
			
			int subRegionId = this.resourceDao.getSubRegionIDByVillageCode(farm.getVillageCode());
			
			farm.setRegionId(regionId);
			
			farm.setSubRegionId(subRegionId);
			}

			try {
				farmRepository.saveAll(farmList);
				resMap.put("success", true);
				resMap.put("message", farmList.size() + " farm data has been processed.");
				return resMap;
			} catch (Exception e) {
//				e.printStackTrace();
				throw new DataInsertionException("Database Insertion Exception: " + e.getMessage());
			}
		} else {

			throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
		}
	}

}
