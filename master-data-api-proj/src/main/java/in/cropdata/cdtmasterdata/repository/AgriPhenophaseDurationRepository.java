package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriPhenophaseDurationInfDto;
import in.cropdata.cdtmasterdata.model.AgriPhenophaseDuration;

public interface AgriPhenophaseDurationRepository extends JpaRepository<AgriPhenophaseDuration, Integer> {

	@Query(value = "SELECT APD.ID, APD.StateCode,APD.CommodityID,APD.VarietyID,APD.SeasonID,APD.PhenophaseID,GS.Name as State,\n"
			+ "APD.FileUrl as ImageURL,\n"
			+ "APD.PhenophaseStart,APD.PhenophaseEnd,APD.Status,APD.CreatedAt,APD.UpdatedAt,AC.Name as Commodity,AV.Name as Variety,ASE.Name as Season,AP.Name as Phenophase\n"
			+ "FROM agri_phenophase_duration APD \n" + "LEFT JOIN agri_commodity AC ON ( APD.CommodityID = AC.ID)\n"
			+ "LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n"
			+ "LEFT JOIN agri_season ASE ON (APD.SeasonID = ASE.ID)\n"
			+ "LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID)", nativeQuery = true)
	List<AgriPhenophaseDurationInfDto> getAgriPhenophaseDuration();
	
	@Query(value = "select APD.ID,APD.StateCode,APD.SeasonID,APD.CommodityID,APD.VarietyID,APD.PhenophaseID,APD.PhenophaseStart,APD.PhenophaseEnd, GS.Name as State,\n" + 
			"ASS.Name as Season, AC.Name Commodity, AV.Name Variety,APD.Status,APD.FileUrl as ImageURL, AP.Name Phenophase, APD.IsValid, APD.ErrorMessage, APD.PhenophaseOrder from agri_phenophase_duration APD \n" +
			"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" + 
			"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" + 
			"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" + 
			"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" + 
			"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" + 
			"WHERE AP.Name LIKE :searchText\n" + 
			"					OR ASS.Name  LIKE :searchText\n" + 
			"					OR AC.Name LIKE :searchText \n" + 
			"					OR AV.Name LIKE :searchText \n" + 
			"					OR GS.Name  LIKE :searchText",
			countQuery = "select count(APD.ID) as Count \n" + 
					" from agri_phenophase_duration APD \n" +
					"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" + 
					"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" + 
					"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" + 
					"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" + 
					"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" + 
					"WHERE AP.Name LIKE :searchText\n" + 
					"					OR ASS.Name  LIKE :searchText\n" + 
					"					OR AC.Name LIKE :searchText \n" + 
					"					OR AV.Name LIKE :searchText \n" + 
					"					OR GS.Name  LIKE :searchText", nativeQuery = true)
	Page<AgriPhenophaseDurationInfDto> getAgriPhenophaseDurationList(Pageable pageable, String searchText);
	
	@Query(value = "select APD.ID,APD.StateCode,APD.SeasonID,APD.CommodityID,APD.VarietyID,APD.PhenophaseID,APD.PhenophaseStart,APD.PhenophaseEnd, GS.Name as State,\n" + 
			"ASS.Name as Season, AC.Name Commodity, AV.Name Variety,APD.Status,APD.FileUrl as ImageURL, AP.Name Phenophase, APD.IsValid, APD.PhenophaseOrder from agri_phenophase_duration_missing APD \n" +
			"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" + 
			"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" + 
			"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" + 
			"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" + 
			"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" + 
			"WHERE AP.Name LIKE :searchText\n" + 
			"					OR ASS.Name  LIKE :searchText\n" + 
			"					OR AC.Name LIKE :searchText \n" + 
			"					OR AV.Name LIKE :searchText \n" + 
			"					OR GS.Name  LIKE :searchText",
			countQuery = "select count(APD.ID) as Count  \n" + 
					" from agri_phenophase_duration_missing APD \n" +
					"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" + 
					"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" + 
					"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" + 
					"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" + 
					"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" + 
					"WHERE AP.Name LIKE :searchText\n" + 
					"					OR ASS.Name  LIKE :searchText\n" + 
					"					OR AC.Name LIKE :searchText \n" + 
					"					OR AV.Name LIKE :searchText \n" + 
					"					OR GS.Name  LIKE :searchText", nativeQuery = true)
	Page<AgriPhenophaseDurationInfDto> getAgriPhenophaseDurationMissingList(Pageable pageable, String searchText);

	@Query(value = "select APD.ID,APD.StateCode,APD.SeasonID,APD.CommodityID,APD.VarietyID,APD.PhenophaseID,APD.PhenophaseStart,APD.PhenophaseEnd, GS.Name as State,\n" +
			"ASS.Name as Season, AC.Name Commodity, AV.Name Variety,APD.Status,APD.FileUrl as ImageURL, AP.Name Phenophase, APD.IsValid, APD.ErrorMessage, APD.PhenophaseOrder from agri_phenophase_duration APD \n" +
			"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" +
			"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" +
			"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" +
			"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" +
			"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" +
			"where APD.IsValid = 0 and (AP.Name LIKE :searchText\n" +
			"					OR ASS.Name  LIKE :searchText\n" +
			"					OR AC.Name LIKE :searchText \n" +
			"					OR AV.Name LIKE :searchText \n" +
			"					OR GS.Name  LIKE :searchText)",
			countQuery = "select count(APD.ID) as Count \n" +
					" from agri_phenophase_duration APD \n" +
					"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" +
					"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" +
					"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" +
					"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" +
					"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" +
					"WHERE APD.IsValid = 0 and (AP.Name LIKE :searchText\n" +
					"					OR ASS.Name  LIKE :searchText\n" +
					"					OR AC.Name LIKE :searchText \n" +
					"					OR AV.Name LIKE :searchText \n" +
					"					OR GS.Name  LIKE :searchText)", nativeQuery = true)
	Page<AgriPhenophaseDurationInfDto> getAgriPhenophaseDurationListInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "select APD.ID,APD.StateCode,APD.SeasonID,APD.CommodityID,APD.VarietyID,APD.PhenophaseID,APD.PhenophaseStart,APD.PhenophaseEnd, GS.Name as State,\n" +
			"ASS.Name as Season, AC.Name Commodity, AV.Name Variety,APD.Status,APD.FileUrl as ImageURL, AP.Name Phenophase, APD.IsValid, APD.PhenophaseOrder from agri_phenophase_duration_missing APD \n" +
			"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" +
			"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" +
			"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" +
			"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" +
			"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" +
			"where APD.IsValid = 0 and (AP.Name LIKE :searchText\n" +
			"					OR ASS.Name  LIKE :searchText\n" +
			"					OR AC.Name LIKE :searchText \n" +
			"					OR AV.Name LIKE :searchText \n" +
			"					OR GS.Name  LIKE :searchText)",
			countQuery = "select count(APD.ID) as Count \n" +
					" from agri_phenophase_duration_missing APD \n" +
					"left join geo_state GS on(APD.StateCode = GS.StateCode)\n" +
					"left join agri_season ASS on(APD.SeasonID = ASS.ID)\n" +
					"left join agri_commodity AC on(APD.CommodityID = AC.ID)\n" +
					"left join agri_variety AV on(APD.VarietyID = AV.ID)\n" +
					"left join agri_phenophase AP on(APD.PhenophaseID = AP.ID)\n" +
					"WHERE APD.IsValid = 0 and (AP.Name LIKE :searchText\n" +
					"					OR ASS.Name  LIKE :searchText\n" +
					"					OR AC.Name LIKE :searchText \n" +
					"					OR AV.Name LIKE :searchText \n" +
					"					OR GS.Name  LIKE :searchText)", nativeQuery = true)
	Page<AgriPhenophaseDurationInfDto> getAgriPhenophaseDurationMissingListInvalidated(Pageable pageable, String searchText);

}
