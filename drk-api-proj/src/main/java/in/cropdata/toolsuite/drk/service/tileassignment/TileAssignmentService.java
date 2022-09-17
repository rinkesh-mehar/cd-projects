package in.cropdata.toolsuite.drk.service.tileassignment;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.pr.VillageInfoSummaryRepository;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInformationSummary;
import in.cropdata.toolsuite.drk.model.tileassignment.GeoSubRegion;
import in.cropdata.toolsuite.drk.repository.tileassignment.RegionMasterRepository;
import in.cropdata.toolsuite.drk.repository.tileassignment.SubRegionMasterRepository;

@Service
public class TileAssignmentService {

	@Autowired
	private RegionMasterRepository regionMasterRepository;

	@Autowired
	private SubRegionMasterRepository subRegionMasterRepository;

	@Autowired
	private VillageInfoSummaryRepository villageInfoSummaryRepository;

	/***
	 * getAllSubRegionByRegionID - GET All SubRegions By regionID
	 * 
	 * @param regionId
	 * @return List<GeoSubRegion>
	 */
	public List<GeoSubRegion> getAllSubRegionByRegionID(int regionId) {

		try {

			List<GeoSubRegion> subRegionList = subRegionMasterRepository.getAllBySortedData(regionId);

//			for (GeoSubRegion subRegion : subRegionList) {
//
//				PrVillageInformationSummary infoSummary = villageInfoSummaryRepository
//						.getBySubRegion(subRegion.getSubRegionID());
//
//				if (infoSummary != null) {
//
//					subRegion.setTotalTask(infoSummary.getTotalTask());
//					subRegion.setTaskCompleteness(infoSummary.getTaskCompletionPercent());
//
//				} else {
//					subRegion.setTotalTask(0);
//					subRegion.setTaskCompleteness(BigDecimal.ZERO);
//				}
//			}

			return subRegionList;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
