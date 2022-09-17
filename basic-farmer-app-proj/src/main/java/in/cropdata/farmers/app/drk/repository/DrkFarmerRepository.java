/**
 * 
 */
package in.cropdata.farmers.app.drk.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.FarmerProfileDTO;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface DrkFarmerRepository extends JpaRepository<DrkFarmer, String> {
	
	Optional<DrkFarmer> findAllByPrimaryMobNumber(String mobileNo);
	
	Optional<DrkFarmer> findById(String id);
	
	Optional<DrkFarmer> findByAuthToken(String authToken);

	@Query(value ="select F.farmer_name as farmerName,F.ID as farmerId,any_value(F.primary_mob_number) as farmerPrimaryMobileNo, any_value(GV.VillageCode) as villageCode,       \n" + 
			"						any_value(GV.Name) as villageName,any_value(GP.PanchayatCode) as panchayatCode, any_value(GP.Name) as panchayatName,       \n" + 
			"						any_value(GT.TehsilCode) as tehsilCode, any_value(GT.Name) as tehsilName,any_value(GD.DistrictCode) as districtCode,any_value(GD.Name) as districtName,       \n" + 
			"						any_value(GS.StateCode) as stateCode,any_value(GS.Name) as stateName, any_value(ms.ImageUrl) as docURL,       \n" + 
			"						0 as cityCode, '' as cityName, v.Pin as pinCode, FK.permanent_address as addressLine1, '' as addressLine2, F.farm_size as farmSize from drkrishi_ts.farmer F       \n" + 
			"						left join drkrishi_ts.farmer_kyc FK on FK.farmer_id = F.ID  \n" + 
			"						left join drkrishi_ts.image_store ms on ms.MetaID = 2 and  ms.SourceID = FK.ID    \n" + 
			"						left join cdt_master_data.geo_village v on v.ID = F.village_id    \n" + 
			"						left join cdt_master_data.geo_village GV on GV.VillageCode = F.village_id  and GV.Name != \"0\"         \n" + 
			"						left join cdt_master_data.geo_panchayat GP on GP.PanchayatCode = GV.PanchayatCode   and  GP.Name != \"0\"          \n" + 
			"						left join cdt_master_data.geo_tehsil GT on GT.TehsilCode = GV.TehsilCode  and GT.Name != \"0\"          \n" + 
			"						left join cdt_master_data.geo_district GD on GD.DistrictCode = GV.DistrictCode and GD.Name != \"0\"          \n" + 
			"						left join cdt_master_data.geo_state GS on GS.StateCode = GV.StateCode     \n" + 
			"						 where F.ID = ?1", nativeQuery = true)
	Optional<FarmerProfileDTO> getFarmerProfileInfoDrkrishi(String farmerId);
	
	
	@Query(value= " select F.farmer_name as farmerName,F.ID as farmerId,any_value(F.primary_mob_number ) as farmerPrimaryMobileNo, any_value(GV.VillageCode) as villageCode,      \n" + 
			"any_value(GV.Name) as villageName,any_value(GP.PanchayatCode) as panchayatCode, any_value(GP.Name) as panchayatName,      \n" + 
			"any_value(GT.TehsilCode) as tehsilCode, any_value(GT.Name) as tehsilName,any_value(GD.DistrictCode) as districtCode,any_value(GD.Name) as districtName,      \n" + 
			"any_value(GS.StateCode) as stateCode,any_value(GS.Name) as stateName, \n" + 
			" ms.ImageUrl as docURL,     \n" + 
			"any_value(GC.cityCode) as cityCode, any_value(GC.Name) as cityName, FAB.Pincode as pinCode, FAB.AddressLine1 as addressLine1,   \n" + 
			"FAB.AddressLine2 as addressLine2,F.farm_size as farmSize from drkrishi_ts.farmer F      \n" + 
			"left join drkrishi_ts.farmer_kyc FK on FK.farmer_id = F.ID    \n" + 
			"left join drkrishi_ts.farmer_address_book FAB on FAB.ID = F.address_id    \n" + 
			"left join drkrishi_ts.image_store ms on ms.MetaID = 2 and  ms.SourceID = FK.ID    \n" + 
			"left join cdt_master_data.geo_district GD on GD.DistrictCode = FAB.DistrictCode  and GD.Name != \"0\"   \n" + 
			"left join cdt_master_data.geo_state GS on GS.StateCode = FAB.StateCode        \n" + 
			"left join cdt_master_data.geo_village GV on GV.VillageCode = FAB.VillageCode  and GV.Name != \"0\"     \n" + 
			"left join cdt_master_data.geo_panchayat GP on GP.PanchayatCode = GV.PanchayatCode  and  GP.Name != \"0\"       \n" + 
			"left join cdt_master_data.geo_tehsil GT on GT.TehsilCode = GV.TehsilCode  and GT.Name != \"0\"        \n" + 
			"left join cdt_master_data.geo_city GC on GC.CityCode = FAB.CityCode and GC.Name != \"0\"   \n" + 
			"where F.ID = ?1 ",nativeQuery = true)
	Optional<FarmerProfileDTO> getFarmerProfileInfoFarmerApp(String farmerId);
	
	@Query(value = "select  v.stateCode from cdt_master_data.geo_village v where v.VillageCode = ?1",nativeQuery = true)
	Integer getDrkFarmerStateCode(int villageId);
	
	@Query(value = "Select fab.DistrictCode, fab.StateCode from drkrishi_ts.farmer_address_book fab\n" +
			"inner join drkrishi_ts.farmer f on f.address_id = fab.ID where f.ID = ?1",nativeQuery = true)
	Map<String,Object> getFarmerStateAndDistrictCodeForfarmerApp(String farmerId);
	
	@Query(value = "Select v.StateCode, v.DistrictCode from cdt_master_data.geo_village v where v.VillageCode =?1  ",nativeQuery = true)
	Map<String,Object> getFarmerStateAndDistrictCodeForDrkrishi(Integer villageCode);

	@Modifying
	@Transactional
	@Query(value = "update drkrishi_ts.farmer f set f.latitude=?1, f.longitude=?2 where f.id =?3", nativeQuery = true)
	Integer updateFarmerLocationByFarmerID(Double latitude, Double longitude, String farmerID);

	@Query(value = "select distinct f.id from drkrishi_ts.farmer f where f.primary_mob_number = ?1 and f.regionId =?2 limit 1", nativeQuery = true)
	String checkFarmerIdByFarmerPrimaryMobileNumber(String primaryMobileNumber, Integer regionId);
	
}
