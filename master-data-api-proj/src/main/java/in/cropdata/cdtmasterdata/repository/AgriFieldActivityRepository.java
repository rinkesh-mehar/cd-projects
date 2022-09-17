package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.model.AgriFieldActivity;

public interface AgriFieldActivityRepository extends JpaRepository<AgriFieldActivity, Integer> {

	String queryForGetAgriFieldActivityList = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name,\n"
			+ " AFA.Description,AFA.UpdatedAt,AFA.CreatedAt,AFA.Status,AGS.Name as Season,\n"
			+ " AC.Name as Commodity,AP.Name as Phenophase\n" + " FROM agri_field_activity AFA\n"
			+ " LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )   \n"
			+ " LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID)\n"
			+ " LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID)";

	@Query(value = queryForGetAgriFieldActivityList, nativeQuery = true)
	List<AgriFieldActivityInfDto> getAgriFieldActivityList();

	
	@Query(value = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" + 
			"						AFA.Description,AFA.Status,AGS.Name as Season, \n" + 
			"						AC.Name as Commodity,AP.Name as Phenophase, AFA.IsValid, AFA.ErrorMessage, AFA.ImageUrl FROM agri_field_activity AFA \n" +
			"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" + 
			"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" + 
			"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" + 
			"			            where AFA.Name like :searchText\n" + 
			"			            OR AC.Name like :searchText\n" + 
			"			            OR AP.Name like :searchText\n" + 
			"			            OR AGS.Name like :searchText\n" + 
			"			            OR AFA.Description like :searchText", 
			countQuery = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" + 
					"						AFA.Description,AFA.Status,AGS.Name as Season, \n" + 
					"						AC.Name as Commodity,AP.Name as Phenophase, AFA.IsValid, AFA.ErrorMessage, AFA.ImageUrl FROM agri_field_activity AFA \n" +
					"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" + 
					"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" + 
					"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" + 
					"			            where AFA.Name like :searchText\n" + 
					"			            OR AC.Name like :searchText\n" + 
					"			            OR AP.Name like :searchText\n" + 
					"			            OR AGS.Name like :searchText\n" + 
					"			            OR AFA.Description like :searchText", nativeQuery = true)
	Page<AgriFieldActivityInfDto> getAgriFieldActivity(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" + 
			"						AFA.Description,AFA.Status,AGS.Name as Season, \n" + 
			"						AC.Name as Commodity,AP.Name as Phenophase, AFA.ImageUrl FROM agri_field_activity_missing AFA \n" +
			"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" + 
			"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" + 
			"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" + 
			"			            where AFA.Name like :searchText\n" + 
			"			            OR AC.Name like :searchText\n" + 
			"			            OR AP.Name like :searchText\n" + 
			"			            OR AGS.Name like :searchText\n" + 
			"			            OR AFA.Description like :searchText", 
			countQuery = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" + 
					"						AFA.Description,AFA.Status,AGS.Name as Season, \n" + 
					"						AC.Name as Commodity,AP.Name as Phenophase, AFA.IsValid, AFA.ImageUrl FROM agri_field_activity_missing AFA \n" +
					"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" + 
					"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" + 
					"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" + 
					"			            where AFA.Name like :searchText\n" + 
					"			            OR AC.Name like :searchText\n" + 
					"			            OR AP.Name like :searchText\n" + 
					"			            OR AGS.Name like :searchText\n" + 
					"			            OR AFA.Description like :searchText", nativeQuery = true)
	Page<AgriFieldActivityInfDto> getAgriFieldActivityMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" +
			"						AFA.Description,AFA.Status,AGS.Name as Season, \n" +
			"						AC.Name as Commodity,AP.Name as Phenophase, AFA.ErrorMessage,AFA.ImageUrl FROM agri_field_activity AFA \n" +
			"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" +
			"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" +
			"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" +
			"			            where AFA.IsValid = 0 and (AFA.Name like :searchText\n" +
			"			            OR AC.Name like :searchText\n" +
			"			            OR AP.Name like :searchText\n" +
			"			            OR AGS.Name like :searchText\n" +
			"			            OR AFA.Description like :searchText)",
			countQuery = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" +
					"						AFA.Description,AFA.Status,AGS.Name as Season, \n" +
					"						AC.Name as Commodity,AP.Name as Phenophase, AFA.ErrorMessage,AFA.ImageUrl FROM agri_field_activity AFA \n" +
					"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" +
					"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" +
					"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" +
					"			            where AFA.IsValid = 0 and (AFA.Name like :searchText\n" +
					"			            OR AC.Name like :searchText\n" +
					"			            OR AP.Name like :searchText\n" +
					"			            OR AGS.Name like :searchText\n" +
					"			            OR AFA.Description like :searchText)", nativeQuery = true)
	Page<AgriFieldActivityInfDto> getAgriFieldActivityInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" +
			"						AFA.Description,AFA.Status,AGS.Name as Season, \n" +
			"						AC.Name as Commodity,AP.Name as Phenophase, AFA.ImageUrl FROM agri_field_activity_missing AFA \n" +
			"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" +
			"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" +
			"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" +
			"			            where AFA.IsValid = 0 and (AFA.Name like :searchText\n" +
			"			            OR AC.Name like :searchText\n" +
			"			            OR AP.Name like :searchText\n" +
			"			            OR AGS.Name like :searchText\n" +
			"			            OR AFA.Description like :searchText)",
			countQuery = "SELECT AFA.ID,AFA.CommodityID,AFA.PhenophaseID,AFA.SeasonID,AFA.Name, \n" +
					"						AFA.Description,AFA.Status,AGS.Name as Season, \n" +
					"						AC.Name as Commodity,AP.Name as Phenophase, AFA.ImageUrl FROM agri_field_activity_missing AFA \n" +
					"						LEFT JOIN agri_season AGS ON ( AFA.SeasonID = AGS.ID )    \n" +
					"						LEFT JOIN agri_commodity AC ON ( AFA.CommodityID = AC.ID) \n" +
					"						LEFT JOIN agri_phenophase AP ON (AFA.PhenophaseID = AP.ID) \n" +
					"			            where AFA.IsValid = 0 and (AFA.Name like :searchText\n" +
					"			            OR AC.Name like :searchText\n" +
					"			            OR AP.Name like :searchText\n" +
					"			            OR AGS.Name like :searchText\n" +
					"			            OR AFA.Description like :searchText)", nativeQuery = true)
	Page<AgriFieldActivityInfDto> getAgriFieldActivityMissingInvalidated(Pageable sortedByIdDesc, String searchText);
}
