package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPlantPartInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPart;

public interface AgriCommodityPlantPartRepository extends JpaRepository<AgriCommodityPlantPart, Integer> {

	@Query(value = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n"
			+ "ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,AP.Name as Phenophase \n"
			+ "FROM agri_commodity_plant_part ACPP \n" + "LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n"
			+ "LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n"
			+ "LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID)", nativeQuery = true)
	List<AgriCommodityPlantPartInfDto> getCommodityPlantPartList();

	@Query(value = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" + 
			"			 ACPP.FileUrl as ImageURL, \n" + 
			"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" + 
			"			 AP.Name as Phenophase, ACPP.IsValid, ACPP.ErrorMessage  FROM agri_commodity_plant_part ACPP \n" +
			"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" + 
			"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" + 
			"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" + 
			"			 where APP.Name like :searchText\n" + 
			"             OR AC.Name like :searchText\n" + 
			"             OR AP.Name like :searchText", 
			countQuery = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" + 
					"			 ACPP.FileUrl as ImageURL, \n" + 
					"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" + 
					"			 AP.Name as Phenophase, ACPP.IsValid, ACPP.ErrorMessage  FROM agri_commodity_plant_part ACPP \n" +
					"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" + 
					"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" + 
					"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" + 
					"			 where APP.Name like :searchText\n" + 
					"             OR AC.Name like :searchText\n" + 
					"             OR AP.Name like :searchText", nativeQuery = true)
	Page<AgriCommodityPlantPartInfDto> getCommodityPlantPartList(Pageable pageable, String searchText);
	
	@Query(value = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" + 
			"			 ACPP.FileUrl as ImageURL, \n" + 
			"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" + 
			"			 AP.Name as Phenophase, ACPP.IsValid  FROM agri_commodity_plant_part_missing ACPP \n" +
			"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" + 
			"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" + 
			"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" + 
			"			 where APP.Name like :searchText\n" + 
			"             OR AC.Name like :searchText\n" + 
			"             OR AP.Name like :searchText", 
			countQuery = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" + 
					"			 ACPP.FileUrl as ImageURL, \n" + 
					"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" + 
					"			 AP.Name as Phenophase, ACPP.IsValid  FROM agri_commodity_plant_part_missing ACPP \n" +
					"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" + 
					"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" + 
					"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" + 
					"			 where APP.Name like :searchText\n" + 
					"             OR AC.Name like :searchText\n" + 
					"             OR AP.Name like :searchText", nativeQuery = true)
	Page<AgriCommodityPlantPartInfDto> getCommodityPlantPartMissingList(Pageable pageable, String searchText);

	@Query(value = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" +
			"			 ACPP.FileUrl as ImageURL, \n" +
			"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" +
			"			 AP.Name as Phenophase, ACPP.IsValid, ACPP.ErrorMessage FROM agri_commodity_plant_part ACPP \n" +
			"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" +
			"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" +
			"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" +
			"			 where ACPP.IsValid = 0 and (APP.Name like :searchText\n" +
			"             OR AC.Name like :searchText\n" +
			"             OR AP.Name like :searchText)",
			countQuery = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" +
					"			 ACPP.FileUrl as ImageURL, \n" +
					"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" +
					"			 AP.Name as Phenophase, ACPP.IsValid, ACPP.ErrorMessage  FROM agri_commodity_plant_part ACPP \n" +
					"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" +
					"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" +
					"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" +
					"			 where ACPP.IsValid = 0 and (APP.Name like :searchText\n" +
					"             OR AC.Name like :searchText\n" +
					"             OR AP.Name like :searchText)", nativeQuery = true)
	Page<AgriCommodityPlantPartInfDto> getCommodityPlantPartListInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" +
			"			 ACPP.FileUrl as ImageURL, \n" +
			"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" +
			"			 AP.Name as Phenophase, ACPP.IsValid FROM agri_commodity_plant_part_missing ACPP \n" +
			"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" +
			"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" +
			"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" +
			"			 where ACPP.IsValid = 0 and (APP.Name like :searchText\n" +
			"             OR AC.Name like :searchText\n" +
			"             OR AP.Name like :searchText)",
			countQuery = "SELECT ACPP.ID,ACPP.CommodityID,ACPP.PhenophaseID,ACPP.PlantPartID,\n" +
					"			 ACPP.FileUrl as ImageURL, \n" +
					"			ACPP.CreatedAt,ACPP.UpdatedAt,ACPP.Status,AC.Name as Commodity,APP.Name as PlantPart,\n" +
					"			 AP.Name as Phenophase, ACPP.IsValid  FROM agri_commodity_plant_part_missing ACPP \n" +
					"			LEFT JOIN agri_commodity AC ON (AC.ID = ACPP.CommodityID) \n" +
					"			LEFT JOIN agri_phenophase AP ON (AP.ID = ACPP.PhenophaseID) \n" +
					"			LEFT JOIN agri_plant_part APP ON (APP.ID = ACPP.PlantPartID) \n" +
					"			 where ACPP.IsValid = 0 and (APP.Name like :searchText\n" +
					"             OR AC.Name like :searchText\n" +
					"             OR AP.Name like :searchText)", nativeQuery = true)
	Page<AgriCommodityPlantPartInfDto> getCommodityPlantPartMissingListInvalidated(Pageable pageable, String searchText);

}
