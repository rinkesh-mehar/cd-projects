package in.cropdata.toolsuite.drk.dao.pr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.dto.gt.VillageInfoInfDto;
import in.cropdata.toolsuite.drk.model.pr.PrVillageCropSeasonSowingHarvestInfo;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInfo;

public interface PrVillageInfoRepository extends JpaRepository<PrVillageInfo, Integer> {

	@Query(value = "Select SubRegionID,group_concat(ifnull(VillageCode,0)) VillageGroup,group_concat(ifnull(TotalFarmers,0)) as RegisteredFarmerGroup from\n"
			+ "(Select SubRegionID,VillageCode,count(FarmerID) as TotalFarmers from gstm_transitory.case_details\n"
			+ "group by SubRegionID,VillageCode)  as temp group by SubRegionID;", nativeQuery = true)
	List<VillageInfoInfDto> getFarmersRegistererdCountList();

	@Query(value = "SELECT * FROM gstm_transitory.village_information where SubRegionID =?1 and VillageCode =?2", nativeQuery = true)
	Optional<PrVillageInfo> getBySubRegionIdAndVillageCode(Integer subRegionID, int villageCode);

	@Query(value = "select * from village_crop_season_sowing_harvest", nativeQuery = true)
	List<PrVillageCropSeasonSowingHarvestInfo> getAllCropSeasonSowingHarvestInfo();

}
