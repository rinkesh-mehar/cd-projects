package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriSeedTreatmentInfDto;
import in.cropdata.cdtmasterdata.model.AgriSeedTreatment;

public interface AgriSeedTreatmentRepository extends JpaRepository<AgriSeedTreatment, Integer> {

	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name,AST.UpdatedAt,\n"
			+ " AST.CreatedAt,AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom\n"
			+ " FROM agri_seed_treatment AST \n" + " LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID)\n"
			+ " LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID)\n"
			+ " LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID)", nativeQuery = true)
	List<AgriSeedTreatmentInfDto> getAgriSeedTreatmentList();
	
	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" + 
			"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom, AST.IsValid, AST.ErrorMessage\n" +
			"						 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" + 
			"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" + 
			"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" + 
			"			            where AST.Name like :searchText \n" + 
			"                        OR AC.Name like :searchText \n" + 
			"                        OR AV.Name like :searchText \n" + 
			"                        OR GUOM.Name like :searchText \n" + 
			"                        OR AST.dose like :searchText ",
			countQuery = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" + 
					"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom, AST.IsValid, AST.ErrorMessage \n" +
					"						 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" + 
					"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" + 
					"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" + 
					"			            where AST.Name like :searchText \n" + 
					"                        OR AC.Name like :searchText \n" + 
					"                        OR AV.Name like :searchText \n" + 
					"                        OR GUOM.Name like :searchText \n" + 
					"                        OR AST.dose like :searchText ", nativeQuery = true )
	Page<AgriSeedTreatmentInfDto> getAgriSeedTreatment(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" + 
			"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom \n" +
			"						 FROM agri_seed_treatment_missing AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" + 
			"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" + 
			"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" + 
			"			            where AST.Name like :searchText \n" + 
			"                        OR AC.Name like :searchText \n" + 
			"                        OR AV.Name like :searchText \n" + 
			"                        OR GUOM.Name like :searchText \n" + 
			"                        OR AST.dose like :searchText ",
			countQuery = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" + 
					"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom \n" +
					"						 FROM agri_seed_treatment_missing AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" + 
					"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" + 
					"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" + 
					"			            where AST.Name like :searchText \n" + 
					"                        OR AC.Name like :searchText \n" + 
					"                        OR AV.Name like :searchText \n" + 
					"                        OR GUOM.Name like :searchText \n" + 
					"                        OR AST.dose like :searchText ", nativeQuery = true )
	Page<AgriSeedTreatmentInfDto> getAgriSeedTreatmentMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" +
			"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom, AST.ErrorMessage \n" +
			"						 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" +
			"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" +
			"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" +
			"			            where AST.IsValid = 0 and (AST.Name like :searchText \n" +
			"                        OR AC.Name like :searchText \n" +
			"                        OR AV.Name like :searchText \n" +
			"                        OR GUOM.Name like :searchText \n" +
			"                        OR AST.dose like :searchText) ",
			countQuery = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" +
					"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom, AST.ErrorMessage \n" +
					"						 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" +
					"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" +
					"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" +
					"			             where AST.IsValid = 0 and (AST.Name like :searchText \n" +
					"                        OR AC.Name like :searchText \n" +
					"                        OR AV.Name like :searchText \n" +
					"                        OR GUOM.Name like :searchText \n" +
					"                        OR AST.dose like :searchText) ", nativeQuery = true )
	Page<AgriSeedTreatmentInfDto> getAgriSeedTreatmentInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" +
			"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom \n" +
			"						 FROM agri_seed_treatment_missing AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" +
			"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" +
			"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" +
			"			            where AST.IsValid = 0 and (AST.Name like :searchText \n" +
			"                        OR AC.Name like :searchText \n" +
			"                        OR AV.Name like :searchText \n" +
			"                        OR GUOM.Name like :searchText \n" +
			"                        OR AST.dose like :searchText) ",
			countQuery = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name, \n" +
					"						 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom \n" +
					"						 FROM agri_seed_treatment_missing AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID) \n" +
					"						 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID) \n" +
					"						 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID) \n" +
					"			             where AST.IsValid = 0 and (AST.Name like :searchText \n" +
					"                        OR AC.Name like :searchText \n" +
					"                        OR AV.Name like :searchText \n" +
					"                        OR GUOM.Name like :searchText \n" +
					"                        OR AST.dose like :searchText) ", nativeQuery = true )
	Page<AgriSeedTreatmentInfDto> getAgriSeedTreatmentMissingInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AST.ID,AST.CommodityID,AST.VarietyID,AST.UomID,AST.Name,\n" + 
			"									 AST.Status,AST.dose,AC.Name as Commodity,AV.Name as Variety,GUOM.Name as Uom, AST.IsValid, AST.ErrorMessage\n" + 
			"									 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID)\n" + 
			"									 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID)\n" + 
			"									 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID)\n" + 
			"						            where AST.CommodityID = :commodityId OR AST.VarietyID = :varietyId OR AST.Name = :name",
			countQuery = "SELECT count(AST.ID) as Count\n" + 
					"									 FROM agri_seed_treatment AST   LEFT JOIN agri_commodity AC ON (AST.CommodityID = AC.ID)\n" + 
					"									 LEFT JOIN agri_variety AV ON (AST.VarietyID = AV.ID)\n" + 
					"									 LEFT JOIN general_uom GUOM ON (AST.UomID = GUOM.ID)\n" + 
					"						            where AST.CommodityID = :commodityId OR AST.VarietyID = :varietyId OR AST.Name = :name", nativeQuery = true )
	Page<AgriSeedTreatmentInfDto> getAgriSeedTreatmentByMultiSearchFilters(Pageable sortedByIdDesc,String commodityId,String varietyId,String name);

}
