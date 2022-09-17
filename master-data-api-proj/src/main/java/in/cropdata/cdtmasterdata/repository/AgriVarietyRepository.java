package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyInfDto;
import in.cropdata.cdtmasterdata.model.AgriVariety;

public interface AgriVarietyRepository extends JpaRepository<AgriVariety, Integer> {

	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,AV.CreatedAt,AV.UpdatedAt,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode FROM agri_variety AV  \n"
			+ "LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID) order by AV.Name", nativeQuery = true)
	List<AgriVarietyInfDto> getAgriVariety();

	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage\n"
			+ "FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText", countQuery = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
					+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage  \n"
					+ "FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
					+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.Name like :searchText\n"
					+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
					+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
					+ "OR AV.InternationalRestrictions like :searchText", nativeQuery = true)
	Page<AgriVarietyInfDto> getAgriVariety(Pageable pageable, String searchText);
	
	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode  \n"
			+ "FROM agri_variety_missing AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText", countQuery = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
					+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode  \n"
					+ "FROM agri_variety_missing AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
					+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.Name like :searchText\n"
					+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
					+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
					+ "OR AV.InternationalRestrictions like :searchText", nativeQuery = true)
	Page<AgriVarietyInfDto> getAgriVarietyMissing(Pageable pageable, String searchText);

	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,AV.CreatedAt,AV.UpdatedAt,"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions,\n" + "AC.Name as Commodity,AHS.HSCode as HsCode\n"
			+ "FROM agri_variety AV \n" + "LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID) where AV.CommodityID = ?1 order by AV.Name", nativeQuery = true)
	List<AgriVarietyInfDto> findAllByCommodityId(int commodityId);

	@Query(value = "SELECT DISTINCT rv.VarietyID as ID, av.Name FROM regional_variety rv \n"
			+ "inner join agri_variety av on av.ID = rv.VarietyID and av.Status = 'Active' \n"
			+ "where rv.StateCode = ?1 and rv.CommodityID = ?2 ORDER BY av.Name ", nativeQuery = true)
	List<AgriVarietyInfDto> getAllAgriVarietyByStateAndCommodity(int stateCode, int commodityId);
	
	@Query(value = "select AV.ID,AV.Name from agri_variety AV\n" + 
			"Inner join regional_variety RV ON (AV.ID = RV.VarietyID and AV.CommodityID = RV.CommodityID)\n" + 
			"Inner join agri_commodity AC ON (AV.CommodityID = AC.ID and RV.CommodityID = AC.ID)\n" + 
			"Inner join agri_season agri_season ON (RV.SeasonID = agri_season.ID)\n" + 
			"Inner join geo_state GS ON (RV.StateCode = GS.StateCode)\n" + 
			"Inner join geo_district GD ON (GS.StateCode = GD.StateCode)\n" + 
			"where RV.StateCode = ?1 and GD.DistrictCode = ?2 and RV.SeasonID = ?3 and AV.CommodityID = ?4\n" + 
			"order by AV.Name ASC", nativeQuery = true)
	List<AgriVarietyInfDto> getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(int stateCode, int districtCode,
			 int seasonId, int commodityId);

	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage \n"
			+ "FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText)",
			countQuery = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage \n"
			+ "FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText)", nativeQuery = true)
	Page<AgriVarietyInfDto> getAgriVarietyInvalidated(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage\n"
			+ "FROM agri_variety_missing AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText)",
			countQuery = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n"
			+ "AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.ErrorMessage \n"
			+ "FROM agri_variety_missing AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID)\n" + "where AV.IsValid = 0 and (AV.Name like :searchText\n"
			+ "OR AC.Name like :searchText\n" + "OR AV.VarietyCode like :searchText\n"
			+ "OR AHS.HSCode like :searchText\n" + "OR AV.DomesticRestrictions like :searchText\n"
			+ "OR AV.InternationalRestrictions like :searchText)", nativeQuery = true)
	Page<AgriVarietyInfDto> getAgriVarietyMissingInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n" + 
			"		AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage\n" + 
			"		FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n" + 
			"		LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID) where AV.CommodityID = :commodityId OR AV.HsCodeID = :hsCodeId OR AV.DomesticRestrictions = :domesticRestrictions OR AV.InternationalRestrictions = :internationalRestrictions", countQuery = "SELECT AV.ID,AV.Name,AV.CommodityID,AV.HsCodeId,AV.VarietyCode,AV.Status,\n" + 
					"		AV.DomesticRestrictions,AV.InternationalRestrictions, AC.Name as Commodity,AHS.HSCode as HsCode, AV.IsValid, AV.ErrorMessage\n" + 
					"		FROM agri_variety AV  LEFT JOIN agri_commodity AC ON ( AV.CommodityID = AC.ID)\n" + 
					"		LEFT JOIN agri_hs_code AHS ON (AV.HsCodeId = AHS.ID) where AV.CommodityID = :commodityId OR AV.HsCodeID = :hsCodeId OR AV.DomesticRestrictions = :domesticRestrictions OR AV.InternationalRestrictions = :internationalRestrictions", nativeQuery = true)
	Page<AgriVarietyInfDto> getAgriVarietyByMultiSearchFilter(Pageable pageable,String commodityId,String hsCodeId,String domesticRestrictions,String internationalRestrictions);
}
