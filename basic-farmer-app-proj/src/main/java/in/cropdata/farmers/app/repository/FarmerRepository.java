package in.cropdata.farmers.app.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.FarmerProfileDTO;
import in.cropdata.farmers.app.model.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, String> {
	
	@Query(value = "select concat('1000000',UNIX_TIMESTAMP()) as farmerId from dual",nativeQuery = true)
	String getFarmerId();
	
	
	Optional<Farmer> findAllByPrimaryMobNumber(String mobileNo);
	
	@Query(value = "Select fab.DistrictCode, fab.StateCode from drkrishi_ts.farmer_address_book fab\n" +
			"inner join drkrishi_ts.farmer f on f.address_id = fab.ID where f.ID = ?1",nativeQuery = true)
	Map<String,Object> getFarmerStateAndDistrictCode(String farmerId);
	
	Optional<Farmer> findByAuthToken(String authToken);
	
	@Query(value= "  select F.FarmerName as farmerName,F.ID as farmerId,any_value(F.PrimaryMobNumber) as farmerPrimaryMobileNo, any_value(GV.VillageCode) as villageCode,    \n" + 
			"any_value(GV.Name) as villageName,any_value(GP.PanchayatCode) as panchayatCode, any_value(GP.Name) as panchayatName,    \n" + 
			"any_value(GT.TehsilCode) as tehsilCode, any_value(GT.Name) as tehsilName,any_value(GD.DistrictCode) as districtCode,any_value(GD.Name) as districtName,    \n" + 
			"any_value(GS.StateCode) as stateCode,any_value(GS.Name) as stateName, group_concat(FK.DocTypeID) as docTypeId, group_concat(ms.ImageUrl) as docURL,   \n" + 
			"group_concat(FIP.name) as docTypeName, any_value(GC.cityCode) as cityCode, any_value(GC.Name) as cityName, FAB.Pincode as pinCode, FAB.AddressLine1 as addressLine1, \n" + 
			"FAB.AddressLine2 as addressLine2 from farmers_app.farmer F    \n" + 
			"left join farmers_app.farmer_kyc FK on FK.FarmerID = F.ID  \n" + 
			"left join farmers_app.farmer_address_book FAB on FAB.ID = F.AddressID  \n" + 
			"left join farmers_app.image_store ms on ms.MetaID = 2 and  ms.SourceID = FK.ID  \n" + 
			"left join cdt_master_data.farmer_id_proof FIP on FIP.ID = FK.DocTypeID \n" + 
			"left join cdt_master_data.geo_district GD on GD.DistrictCode = FAB.DistrictCode  and GD.Name != \"0\" \n" + 
			"left join cdt_master_data.geo_state GS on GS.StateCode = FAB.StateCode      \n" + 
			"left join cdt_master_data.geo_village GV on GV.VillageCode = F.VillageCode  and GV.Name != \"0\"   \n" + 
			"left join cdt_master_data.geo_panchayat GP on GP.PanchayatCode = GV.PanchayatCode  and  GP.Name != \"0\"     \n" + 
			"left join cdt_master_data.geo_tehsil GT on GT.TehsilCode = GV.TehsilCode  and GT.Name != \"0\"      \n" + 
			"left join cdt_master_data.geo_city GC on GC.CityCode = FAB.CityCode and GC.Name != \"0\" \n" + 
			"where F.ID = ?1  group by F.ID   ",nativeQuery = true)
	Optional<FarmerProfileDTO> getFarmerProfileInfoFarmerApp(String farmerId);
	
	
   
}
