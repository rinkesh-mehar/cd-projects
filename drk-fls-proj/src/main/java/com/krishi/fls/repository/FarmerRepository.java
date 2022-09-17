package com.krishi.fls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krishi.fls.entity.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, String>
 {
  
  @Query("select f from Farmer as f,Village as v where f.villageId=v.id and v.regionId=:regionId ")
  public List<Farmer> findbyRegion(@Param(value="regionId")Integer regionId);
  
  @Query("select f from Farmer as f where f.drkCust=0 And f.agriotaCust=0 ")
  public List<Farmer> findnewUsers();
  
  @Query("select f from Farmer as f where f.drkCust=1 And f.agriotaCust=0 ")
  public List<Farmer> findrkUsers();
  
	/** get farmer records based on farmerId with farmer_crop_info table- CDT-Ujwal- Start */
  
	/*
	 * @Query(value = "select f.id,\n" + "	alternative_mob_number\n" +
	 * "	,farm_size\n" + "	,farmer_father_husband_name\n" + "	,farmer_name\n"
	 * + "	,has_government_id_proof\n" + "	,has_irrigation_land\n" +
	 * "	,has_own_land\n" + "	,is_vip\n" + "	,land_ownership_images\n" +
	 * "	,mobile_type_id\n" + "	,primary_mob_number\n" + "	,reference_person\n"
	 * + "	,reference_person_mobile_number\n" + "	,relationship_to_reference\n" +
	 * "	,speaking_language_id\n" + "	,village_id\n" +
	 * "	,willingness_for_cdt\n" + "	,has_outstanding_loan\n" +
	 * "	,has_life_insurance\n" + "	,has_health_insurance\n" +
	 * "	,has_crop_insurance\n" + "	,is_drk_cust\n" + "	,is_agriota_cust\n" +
	 * "	,due_amount \n" + ",seller_type \n" +
	 * " , group_concat(distinct fc.commodity_id) as major_crop,crop_area, created_date,created_by,modified_by,modified_date FROM dev.farmer f \n"
	 * +
	 * ", farmer_crop_info as fc, commodity as c where fc.farmer_id = f.id and f.id in (?1)\n"
	 * + "  and c.id = fc.commodity_id\n" + "group by f.id", nativeQuery = true)
	 * public List<Farmer> findByFarmerId(List<String> farmerId);
	 */
  
  /** get farmer records based on farmerId with farmer_crop_info table- CDT-Ujwal- End */
  
  public List<Farmer> findByVillageIdIn(List<Integer> villageIds);
  
	/** check Farmer already exists based on primary mobile number */
  public Optional<Farmer> findByprimaryMobNumber(String primaryMobileNumber);
 
}
