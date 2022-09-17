package in.cropdata.toolsuite.drk.services.fl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.exceptions.DataInsertionException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.model.cases.Farmer;
import in.cropdata.toolsuite.drk.repository.fl.FarmerRepository;

@Service
public class FarmerService {

	@Autowired
	private FarmerRepository farmerRepository;
	
	@Autowired
	private ResourceDao resourceDao;
	
	
	public Map<String, Object> saveFarmer(List<Farmer> farmerList) {

		
		Map<String, Object> resMap = new HashMap<String, Object>();

		if (farmerList != null && farmerList.size() > 0) {
			
			for (Farmer farmer : farmerList) {
				
				int subRegionId = this.resourceDao.getSubRegionIDByVillageCode(farmer.getVillageCode());

				int regionId = this.resourceDao.getRegionIdByVillageCode(farmer.getVillageCode());
				
				farmer.setRegionId(regionId);
				
				farmer.setSubRegionId(subRegionId);
			}

			try {
				farmerRepository.saveAll(farmerList);
				resMap.put("success", true);
				resMap.put("message", farmerList.size() + " farmer data has been processed.");
				return resMap;
			} catch (Exception e) {
				throw new DataInsertionException("Database Insertion Exception: " + e.getMessage());
			}
		} else {

			throw new InvalidDataException("Invalid Data. Input Data can not be empty.");
		}
	}
}
