package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriFertilizerInfDto;
import in.cropdata.cdtmasterdata.model.AgriFertilizer;

public interface AgriFertilizerRepository extends JpaRepository<AgriFertilizer, Integer> {

	String queryForAgriFertilizer = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.RegionID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n"
			+ "AF.Note,AF.Status,AF.Name,AF.UpdatedAt,AF.CreatedAt,GS.Name as State,GR.Name as Region,\n"
			+ "AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom\n"
			+ " FROM agri_fertilizer AF \n" 
			+ "LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n"
			+ " LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n"
			+ " LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n"
			+ " LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n"
			+ " LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n"
			+ " LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)";
//			+ "where AF.Type like : searchText";

	@Query(value = queryForAgriFertilizer, nativeQuery = true)
	List<AgriFertilizerInfDto> getAgriFertilizerList();

	@Query(value = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" + 
			"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" + 
			"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" +
			"			 FROM agri_fertilizer AF  \n" + 
			"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" + 
//			"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" + 
			"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" + 
			"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" + 
			"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" + 
			"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" + 
			"             where AF.Name like :searchText\n" + 
			"             OR GS.Name like :searchText\n" + 
//			"             OR GR.Name like :searchText\n" + 
			"             OR AGS.Name like :searchText\n" + 
			"             OR AC.Name like :searchText\n" + 
			"             OR ADF.Name like :searchText\n" + 
			"             OR GUOM.Name like :searchText\n" + 
			"             OR AF.Dose like :searchText\n" + 
			"             OR AF.Note like :searchText", 
			countQuery = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" + 
					"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" + 
					"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" +
					"			 FROM agri_fertilizer AF  \n" + 
					"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" + 
//					"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" + 
					"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" + 
					"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" + 
					"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" + 
					"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" + 
					"             where AF.Name like :searchText\n" + 
					"             OR GS.Name like :searchText\n" + 
//					"             OR GR.Name like :searchText\n" + 
					"             OR AGS.Name like :searchText\n" + 
					"             OR AC.Name like :searchText\n" + 
					"             OR ADF.Name like :searchText\n" + 
					"             OR GUOM.Name like :searchText\n" + 
					"             OR AF.Dose like :searchText\n" + 
					"             OR AF.Note like :searchText"
			, nativeQuery = true)
	Page<AgriFertilizerInfDto> getAgriFertilizerList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" + 
			"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" + 
			"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom\n" +
			"			 FROM agri_fertilizer_missing AF  \n" + 
			"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" + 
//			"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" + 
			"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" + 
			"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" + 
			"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" + 
			"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" + 
			"             where AF.Name like :searchText\n" + 
			"             OR GS.Name like :searchText\n" + 
//			"             OR GR.Name like :searchText\n" + 
			"             OR AGS.Name like :searchText\n" + 
			"             OR AC.Name like :searchText\n" + 
			"             OR ADF.Name like :searchText\n" + 
			"             OR GUOM.Name like :searchText\n" + 
			"             OR AF.Dose like :searchText\n" + 
			"             OR AF.Note like :searchText", 
			countQuery = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" + 
					"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" + 
					"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom\n" +
					"			 FROM agri_fertilizer_missing AF  \n" + 
					"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" + 
//					"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" + 
					"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" + 
					"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" + 
					"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" + 
					"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" + 
					"             where AF.Name like :searchText\n" + 
					"             OR GS.Name like :searchText\n" + 
//					"             OR GR.Name like :searchText\n" + 
					"             OR AGS.Name like :searchText\n" + 
					"             OR AC.Name like :searchText\n" + 
					"             OR ADF.Name like :searchText\n" + 
					"             OR GUOM.Name like :searchText\n" + 
					"             OR AF.Dose like :searchText\n" + 
					"             OR AF.Note like :searchText"
			, nativeQuery = true)
	Page<AgriFertilizerInfDto> getAgriFertilizerMissingList(Pageable sortedByIdDesc, String searchText);


	@Query(value = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" +
			"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" +
			"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" +
			"			 FROM agri_fertilizer AF  \n" +
			"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" +
//			"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" +
			"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" +
			"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" +
			"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" +
			"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" +
			"             where AF.IsValid = 0 and (AF.Name like :searchText\n" +
			"             OR GS.Name like :searchText\n" +
//			"             OR GR.Name like :searchText\n" +
			"             OR AGS.Name like :searchText\n" +
			"             OR AC.Name like :searchText\n" +
			"             OR ADF.Name like :searchText\n" +
			"             OR GUOM.Name like :searchText\n" +
			"             OR AF.Dose like :searchText\n" +
			"             OR AF.Note like :searchText)",
			countQuery = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" +
					"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" +
					"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" +
					"			 FROM agri_fertilizer AF  \n" +
					"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" +
//					"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" +
					"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" +
					"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" +
					"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" +
					"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" +
					"             where AF.IsValid = 0 and (AF.Name like :searchText\n" +
					"             OR GS.Name like :searchText\n" +
//					"             OR GR.Name like :searchText\n" +
					"             OR AGS.Name like :searchText\n" +
					"             OR AC.Name like :searchText\n" +
					"             OR ADF.Name like :searchText\n" +
					"             OR GUOM.Name like :searchText\n" +
					"             OR AF.Dose like :searchText\n" +
					"             OR AF.Note like :searchText)"
			, nativeQuery = true)
	Page<AgriFertilizerInfDto> getAgriFertilizerListInvalidated(Pageable sortedByIdDesc, String searchText);
	

	@Query(value = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" +
			"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" +
			"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom\n" +
			"			 FROM agri_fertilizer_missing AF  \n" +
			"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" +
//			"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" +
			"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" +
			"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" +
			"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" +
			"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" +
			"             where AF.IsValid = 0 and (AF.Name like :searchText\n" +
			"             OR GS.Name like :searchText\n" +
//			"             OR GR.Name like :searchText\n" +
			"             OR AGS.Name like :searchText\n" +
			"             OR AC.Name like :searchText\n" +
			"             OR ADF.Name like :searchText\n" +
			"             OR GUOM.Name like :searchText\n" +
			"             OR AF.Dose like :searchText\n" +
			"             OR AF.Note like :searchText)",
			countQuery = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose,\n" +
					"			AF.Note,AF.Status,AF.Name,GS.Name as State,\n" +
					"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom\n" +
					"			 FROM agri_fertilizer_missing AF  \n" +
					"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode)\n" +
//					"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID)\n" +
					"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID) \n" +
					"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID)\n" +
					"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID)\n" +
					"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID)\n" +
					"             where AF.IsValid = 0 and (AF.Name like :searchText\n" +
					"             OR GS.Name like :searchText\n" +
//					"             OR GR.Name like :searchText\n" +
					"             OR AGS.Name like :searchText\n" +
					"             OR AC.Name like :searchText\n" +
					"             OR ADF.Name like :searchText\n" +
					"             OR GUOM.Name like :searchText\n" +
					"             OR AF.Dose like :searchText\n" +
					"             OR AF.Note like :searchText)"
			, nativeQuery = true)
	Page<AgriFertilizerInfDto> getAgriFertilizerMissingListInvalidated(Pageable sortedByIdDesc, String searchText);

//	Page<AgriFertilizerInfDto> getAllAgriFertilizer(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose, \n" + 
			"			AF.Note,AF.Status,AF.Name,GS.Name as State, \n" + 
			"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" + 
			"			 FROM agri_fertilizer AF   \n" + 
			"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode) \n" + 
			"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID) \n" + 
			"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID)  \n" + 
			"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID) \n" + 
			"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID) \n" + 
			"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID) \n" + 
			"             where AF.StateCode = :stateCode OR AF.SeasonID = :seasonId OR AF.DoseFactorID = :doseFactorId OR AF.CommodityID = :commodityId OR AF.Name = :name", 
			countQuery = "SELECT AF.ID,AF.CommodityID,AF.DoseFactorID,AF.SeasonID,AF.StateCode,AF.UomID,AF.Dose, \n" + 
					"			AF.Note,AF.Status,AF.Name,GS.Name as State, \n" + 
					"			AGS.Name as Season, AC.Name as Commodity,ADF.Name as DoseFactor, GUOM.Name as Uom, AF.IsValid, AF.ErrorMessage\n" + 
					"			 FROM agri_fertilizer AF   \n" + 
					"			LEFT JOIN geo_state GS ON (AF.StateCode = GS.StateCode) \n" + 
					"			 LEFT JOIN geo_region GR ON (AF.RegionID = GR.RegionID) \n" + 
					"			 LEFT JOIN agri_season AGS ON (AF.SeasonID = AGS.ID)  \n" + 
					"			 LEFT JOIN agri_commodity AC ON (AF.CommodityID = AC.ID) \n" + 
					"			 LEFT JOIN agri_dose_factors ADF ON (AF.DoseFactorID = ADF.ID) \n" + 
					"			 LEFT JOIN general_uom GUOM ON (AF.UomID = GUOM.ID) \n" + 
					"             where AF.StateCode = :stateCode OR AF.SeasonID = :seasonId OR AF.DoseFactorID = :doseFactorId OR AF.CommodityID = :commodityId OR AF.Name = :name"
			, nativeQuery = true)
	Page<AgriFertilizerInfDto> getAgriFertilizerListByMultiSearchFilters(Pageable sortedByIdDesc,String stateCode,String seasonId,String doseFactorId,String commodityId,String name);
	

}
