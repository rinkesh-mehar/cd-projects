package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriPhenophaseDurationInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.CondusiveWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalVarietyInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.WeatherParamLabelInfDto;
import in.cropdata.cdtmasterdata.model.RegionalVariety;

public interface RegionalVarietyRepository extends JpaRepository<RegionalVariety, Integer> {

	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.UpdatedAt,RV.StateCode,\n"
			+ "RV.CreatedAt,RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n"
			+ "GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd\n"
			+ "FROM regional_variety RV\n " + "LEFT JOIN geo_state GS ON (RV.StateCode = GS.StateCode) \n"
			+ "LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n"
			+ "LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n"
			+ "LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n"
			+ "LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)", nativeQuery = true)
	List<RegionalVarietyInfDto> getRegionvarietyList();

	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" + 
			"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" + 
			"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd, RV.IsValid, RV.ErrorMessage\n" +
			"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
			"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
			"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
			"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
			"			where AC.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText\n" + 
			"			OR ASS.Name like :searchText\n" + 
			"			OR AV.Name like :searchText\n" + 
			"			OR RV.SowingWeekStart like :searchText\n" + 
			"			OR RV.SowingWeekEnd like :searchText\n" + 
			"			OR RV.HarvestWeekStart like :searchText\n" + 
			"			OR RV.HarvestWeekEnd like :searchText", 
			countQuery = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" + 
					"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" + 
					"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd, RV.IsValid, RV.ErrorMessage\n" +
					"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
					"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
					"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
					"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
					"			where AC.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText\n" + 
					"			OR ASS.Name like :searchText\n" + 
					"			OR AV.Name like :searchText\n" + 
					"			OR RV.SowingWeekStart like :searchText\n" + 
					"			OR RV.SowingWeekEnd like :searchText\n" + 
					"			OR RV.HarvestWeekStart like :searchText\n" + 
					"			OR RV.HarvestWeekEnd like :searchText", nativeQuery = true)
	Page<RegionalVarietyInfDto> getRegionvarietyList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" + 
			"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" + 
			"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd\n" + 
			"			FROM regional_variety_missing RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
			"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
			"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
			"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
			"			where AC.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText\n" + 
			"			OR ASS.Name like :searchText\n" + 
			"			OR AV.Name like :searchText\n" + 
			"			OR RV.SowingWeekStart like :searchText\n" + 
			"			OR RV.SowingWeekEnd like :searchText\n" + 
			"			OR RV.HarvestWeekStart like :searchText\n" + 
			"			OR RV.HarvestWeekEnd like :searchText", 
			countQuery = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" + 
					"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" + 
					"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd\n" + 
					"			FROM regional_variety_missing RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
					"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
					"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
					"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
					"			where AC.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText\n" + 
					"			OR ASS.Name like :searchText\n" + 
					"			OR AV.Name like :searchText\n" + 
					"			OR RV.SowingWeekStart like :searchText\n" + 
					"			OR RV.SowingWeekEnd like :searchText\n" + 
					"			OR RV.HarvestWeekStart like :searchText\n" + 
					"			OR RV.HarvestWeekEnd like :searchText", nativeQuery = true)
	Page<RegionalVarietyInfDto> getRegionvarietyMissingList(Pageable sortedByIdDesc, String searchText);


	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" +
			"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" +
			"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd, RV.IsValid, RV.ErrorMessage\n" +
			"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" +
			"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" +
			"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" +
			"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" +
			"			where RV.IsValid = 0 and (AC.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR ASS.Name like :searchText\n" +
			"			OR AV.Name like :searchText\n" +
			"			OR RV.SowingWeekStart like :searchText\n" +
			"			OR RV.SowingWeekEnd like :searchText\n" +
			"			OR RV.HarvestWeekStart like :searchText\n" +
			"			OR RV.HarvestWeekEnd like :searchText)",
			countQuery = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" +
					"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" +
					"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd, RV.IsValid, RV.ErrorMessage\n" +
					"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" +
					"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" +
					"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" +
					"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" +
					"			where RV.IsValid = 0 and (AC.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR ASS.Name like :searchText\n" +
					"			OR AV.Name like :searchText\n" +
					"			OR RV.SowingWeekStart like :searchText\n" +
					"			OR RV.SowingWeekEnd like :searchText\n" +
					"			OR RV.HarvestWeekStart like :searchText\n" +
					"			OR RV.HarvestWeekEnd like :searchText)", nativeQuery = true)
	Page<RegionalVarietyInfDto> getRegionvarietyListInvalidated(Pageable sortedByIdDesc, String searchText);
	

	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" +
			"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" +
			"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd\n" +
			"			FROM regional_variety_missing RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" +
			"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" +
			"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" +
			"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" +
			"			where RV.IsValid = 0 and (AC.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR ASS.Name like :searchText\n" +
			"			OR AV.Name like :searchText\n" +
			"			OR RV.SowingWeekStart like :searchText\n" +
			"			OR RV.SowingWeekEnd like :searchText\n" +
			"			OR RV.HarvestWeekStart like :searchText\n" +
			"			OR RV.HarvestWeekEnd like :searchText)",
			countQuery = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" +
					"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" +
					"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd\n" +
					"			FROM regional_variety_missing RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" +
					"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" +
					"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" +
					"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" +
					"			where RV.IsValid = 0 and (AC.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR ASS.Name like :searchText\n" +
					"			OR AV.Name like :searchText\n" +
					"			OR RV.SowingWeekStart like :searchText\n" +
					"			OR RV.SowingWeekEnd like :searchText\n" +
					"			OR RV.HarvestWeekStart like :searchText\n" +
					"			OR RV.HarvestWeekEnd like :searchText)", nativeQuery = true)
	Page<RegionalVarietyInfDto> getRegionvarietyMissingListInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT RV.ID,RV.VarietyID,RV.RegionID,RV.SeasonID,RV.CommodityID,RV.StateCode,\n" + 
			"			RV.Status, GR.Name as Region,ASS.Name as Season,AC.Name as Commodity,AV.Name as Variety,\n" + 
			"			GS.Name as State, RV.SowingWeekStart,RV.SowingWeekEnd,RV.HarvestWeekStart,RV.HarvestWeekEnd, RV.IsValid, RV.ErrorMessage\n" + 
			"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
			"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
			"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
			"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
			"			where RV.StateCode = :stateCode OR RV.SeasonID = :seasonId OR RV.CommodityID = :commodityId OR RV.VarietyID = :varietyId", 
			countQuery = "SELECT count(RV.ID) as Count\n" + 
					"			FROM regional_variety RV LEFT JOIN geo_state GS  ON (RV.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASS ON(RV.SeasonID = ASS.ID)\n" + 
					"			LEFT JOIN agri_variety AV ON (RV.VarietyID = AV.ID)\n" + 
					"			LEFT JOIN agri_commodity AC ON (RV.CommodityID = AC.ID)\n" + 
					"			LEFT JOIN geo_region GR ON (RV.RegionID = GR.RegionID)\n" + 
					"			where RV.StateCode = :stateCode OR RV.SeasonID = :seasonId OR RV.CommodityID = :commodityId OR RV.VarietyID = :varietyId", nativeQuery = true)
	Page<RegionalVarietyInfDto> getRegionvarietyListByMultiFilters(Pageable sortedByIdDesc, String stateCode,String seasonId,String commodityId,String varietyId);

	// ------------------Below Crop Calendar Data Query List ----------

	@Query(value = "SELECT distinct RV.SowingWeekStart,RV.HarvestWeekStart\n" + "            FROM regional_variety RV\n"
			+ "			LEFT JOIN geo_region GR ON (RV.StateCode = GR.StateCode)\n"
			+ " WHERE RV.CommodityID = ?1 and RV.VarietyID = ?2 and RV.StateCode = ?3", nativeQuery = true)
	List<RegionalVarietyInfDto> getSowingAndHarvestStartList(int commodityId, int varietyId, int stateCode);

	@Query(value = "select RW.StateCode,RW.WeekNumber,\n"
			+ "            group_concat(WP.Name separator ',') as WeatherParamName,\n"
			+ "            group_concat(RW.WeatherParamValue separator ',') as WeatherParamValue\n"
			+ "            from regional_weather RW \n"
			+ "            Left JOIN  general_weather_params WP ON (RW.WeatherParamID = WP.ID) where RW.WeekNumber IN (?1) and RW.StateCode = ?2 "
			+ "group by RW.StateCode,RW.WeekNumber", nativeQuery = true)
	List<RegionalWeatherInfDto> getRegionalWeatherList(List<Integer> weekList, int stateCode);

	@Query(value = "select * from regional_weather RW where RW.WeekNumber IN (?1) and RW.StateCode = ?2", nativeQuery = true)
	List<RegionalWeatherInfDto> getRegionalWeatherModifiedList(List<Integer> weekList, int stateCode);

	@Query(value = "SELECT AP.ID,AC.Name as Commodity,ASEA.Name as Season,AV.Name as Variety,GS.Name as State,AP.Name,APD.PhenophaseStart,\n"
			+ "            APD.PhenophaseEnd, AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper,\n"
			+ "            AFW.WeatherParameterID,WP.Name as favourableWeatherParamName,WP.Label as weatherLabel,WP.UnitID\n"
			+ "			FROM agri_phenophase_duration APD \n"
			+ "            LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID)\n"
			+ "            LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "            LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID)\n"
			+ "            LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode)\n"
			+ "            LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n"
			+ "			LEFT JOIN agri_favourable_weather AFW ON (AP.ID = AFW.PhenophaseID)\n"
			+ "			LEFT JOIN general_weather_params WP ON (AFW.WeatherParameterID = WP.ID)\n"
			+ "WHERE APD.CommodityID = ?1 and APD.SeasonID = ?2 and APD.VarietyID = ?3 and APD.StateCode = ?4", nativeQuery = true)
	List<AgriPhenophaseDurationInfDto> getPhenophaseList(int commodityId, int seasonId, int varietyId, int stateCode);

	@Query(value = "SELECT ID,bioticStress, CommodityID,condusiveStartWeek,condusiveEndWeek,\n" + 
			"			group_concat(ifnull(PrimarySpecificationAverage,0) separator ',') as PrimarySpecificationAverage,\n" + 
			"			group_concat(ifnull(PrimarySpecificationLower,0) separator ',') as PrimarySpecificationLower,\n" + 
			"			group_concat(ifnull(PrimarySpecificationUpper,0) separator ',') as PrimarySpecificationUpper,\n" + 
			"			group_concat(ifnull(SecondarySpecificationAverage,0) separator ',') as SecondarySpecificationAverage,\n" + 
			"			group_concat(ifnull(SecondarySpecificationLower,0) separator ',') as SecondarySpecificationLower,\n" + 
			"			group_concat(ifnull(SecondarySpecificationUpper,0) separator ',') as SecondarySpecificationUpper,\n" + 
			"			group_concat(PrimaryWeatherParamName separator ',') AS PrimaryWeatherParamName,\n" + 
			"			group_concat(SecondaryWeatherParamName separator ',') AS SecondaryWeatherParamName FROM \n" + 
			"            (\n" + 
			"				SELECT distinct ABS.ID,ABS.Name as bioticStress, ABS.CommodityID,\n" + 
			"				APDS.PhenophaseStart as condusiveStartWeek,APDE.PhenophaseEnd as condusiveEndWeek,\n" + 
			"						ACW.PrimarySpecificationAverage as PrimarySpecificationAverage,\n" + 
			"						ACW.PrimarySpecificationLower as PrimarySpecificationLower,\n" + 
			"						ACW.PrimarySpecificationUpper as PrimarySpecificationUpper,\n" + 
			"						ACW.SecondarySpecificationAverage as SecondarySpecificationAverage,\n" + 
			"						ACW.SecondarySpecificationLower as SecondarySpecificationLower,\n" + 
			"						ACW.SecondarySpecificationUpper as SecondarySpecificationUpper,				\n" + 
			"						WP_P.Name AS PrimaryWeatherParamName,\n" + 
			"						WP_S.Name  AS SecondaryWeatherParamName \n" + 
			"						FROM agri_commodity_stress ABS\n" +
			"						LEFT JOIN regional_variety RV ON (RV.CommodityID = ABS.CommodityID)\n" + 
			"						LEFT JOIN agri_conducive_weather ACW ON (ABS.ID = ACW.StressID and ABS.StressTypeID = ACW.StressTypeID)\n" + 
			"						LEFT JOIN  general_weather_params WP_P ON (WP_P.ID = ACW.PrimaryWeatherParameterID)   \n" + 
			"						LEFT JOIN  general_weather_params WP_S ON (WP_S.ID = ACW.SecondaryWeatherParameterID)\n" + 
			"						LEFT JOIN agri_phenophase_duration APDS ON (APDS.PhenophaseID = ABS.StartPhenophaseID)\n" + 
			"						LEFT JOIN agri_phenophase_duration APDE ON (APDE.PhenophaseID = ABS.EndPhenophaseID)\n" + 
			"						WHERE ABS.CommodityID = ?1 and RV.CommodityID=?1 and APDS.CommodityID=?1 and APDE.CommodityID=?1\n" + 
			"                        and RV.VarietyID = ?2 and APDS.VarietyID = ?2 and APDE.VarietyID = ?2 \n" + 
			"                        and RV.StateCode = ?3 and APDS.StateCode = ?3 and APDE.StateCode = ?3 \n" + 
			"                        and APDS.SeasonID = ?4 and APDE.SeasonID = ?4 \n" + 
			"                        ) as temp\n" + 
			"						 GROUP BY  ID,bioticStress,CommodityID,condusiveStartWeek,condusiveEndWeek", nativeQuery = true)
	List<CondusiveWeatherInfDto> getCondusiveWeatherList(int commodityId, int varietyId,int stateCode,int seasonId);

	@Query(value = "SELECT AP.ID,AC.Name as Commodity,ASEA.Name as Season,AV.Name as Variety,GS.Name as State,AP.Name as PhenophaseName,APD.PhenophaseStart,\n"
			+ "            APD.PhenophaseEnd,AP.Name as Phenophase,APD.ImageID, APD.FileUrl as ImageURL"
			+ "            FROM agri_phenophase_duration APD \n"
			+ "            LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID)\n"
			+ "            LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "            LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID)\n"
			+ "            LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode)\n"
			+ "            LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n"
			+ "            WHERE APD.CommodityID = ?1 and APD.SeasonID = ?2 and APD.VarietyID = ?3 and APD.StateCode = ?4 "
			+ "            order by APD.PhenophaseStart,APD.PhenophaseEnd", nativeQuery = true)
	List<AgriPhenophaseDurationInfDto> getPhenophaseModifiedList(int commodityId, int seasonId, int varietyId,
			int stateCode);

	@Query(value = "select distinct WP.Name as WeatherParamName, WP.Label as WeatherParamLabel from general_weather_params WP where WP.Name IN (?1)\n", nativeQuery = true)
	List<WeatherParamLabelInfDto> getWeatherParam(Set<String> mergeWeatherParameterSet);

	@Query(value = "SELECT AC.Name as Commodity,ASEA.Name as Season,AV.Name as Variety,GS.Name as State,AP.Name,APD.PhenophaseStart,\n"
			+ "            APD.PhenophaseEnd,\n"
			+ "            group_concat(ifnull(AFW.SpecificationAverage,0) separator ',') as SpecificationAverage,\n"
			+ "			group_concat(ifnull(AFW.SpecificationLower,0) separator ',') as SpecificationLower,\n"
			+ "			group_concat(ifnull(AFW.SpecificationUpper,0) separator ',') as SpecificationUpper,\n"
			+ "			group_concat(WP.Name separator ',') as favourableWeatherParamName\n"
			+ "			FROM agri_phenophase_duration APD \n"
			+ "            LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID)\n"
			+ "            LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "            LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID)\n"
			+ "            LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode)\n"
			+ "            LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n"
			+ "			LEFT JOIN agri_favourable_weather AFW ON (AP.ID = AFW.PhenophaseID)\n"
			+ "			LEFT JOIN general_weather_params WP ON (AFW.WeatherParameterID = WP.ID)\n"
			+ "			WHERE APD.CommodityID = ?1 and APD.SeasonID = ?2 and APD.VarietyID = ?3 and APD.StateCode = ?4\n"
			+ "            group by AC.Name,ASEA.Name,AV.Name,GS.Name,AP.Name,APD.PhenophaseStart,\n"
			+ "            APD.PhenophaseEnd order by APD.PhenophaseStart,\n"
			+ "			            APD.PhenophaseEnd", nativeQuery = true)
	List<AgriPhenophaseDurationInfDto> getFavourableWeatherList(int commodityId, int seasonId, int varietyId,
			int stateId);

	@Query(value = "SELECT distinct StateCode,Name From (\n"
			+ "    SELECT distinct GS.StateCode,GS.Name, ASEA.ID as seasonID,ASEA.Name as Season,\n"
			+ "				AC.ID AS CommodityID,AC.Name as commodity ,\n"
			+ "				AV.ID as varietyID, AV.Name as variety\n"
			+ "				FROM agri_phenophase_duration APD \n"
			+ "				LEFT JOIN regional_variety RV ON (APD.StateCode = RV.StateCode)\n"
			+ "				LEFT JOIN geo_region GR ON (RV.StateCode = GR.StateCode)\n"
			+ "				LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID and RV.CommodityID = AC.ID)\n"
			+ "				LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "				LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID and RV.VarietyID = AV.ID)\n"
			+ "				LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode and RV.StateCode = GS.StateCode)\n"
			+ "				LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID) where GR.RegionID is not null and AC.ID is not null \n"
			+ "				and AV.ID is not null)  as temp\n" + "                order by Name", nativeQuery = true)
	List<GeoStateInfDto> getAvailableStateList();

	@Query(value = "select distinct Id,Name from (\n"
			+ "				SELECT distinct GS.StateCode,GS.Name as State, ASEA.ID as Id,ASEA.Name as Name,\n"
			+ "				AC.ID AS CommodityId,AC.Name as Commodity ,\n"
			+ "				AV.ID as varietyId, AV.Name as Variety\n"
			+ "				FROM agri_phenophase_duration APD \n"
			+ "				LEFT JOIN regional_variety RV ON (APD.StateCode = RV.StateCode)\n"
			+ "				LEFT JOIN geo_region GR ON (RV.StateCode = GR.StateCode)\n"
			+ "				LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID and RV.CommodityID = AC.ID)\n"
			+ "				LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "				LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID and RV.VarietyID = AV.ID)\n"
			+ "				LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode and RV.StateCode = GS.StateCode)\n"
			+ "				LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID) where GR.RegionID is not null and AC.ID is not null \n"
			+ "				and AV.ID is not null and GS.StateCode = ?1 and AC.ID = ?2 and AV.ID = ?3) as temp\n"
			+ "                order by Name", nativeQuery = true)
	List<GeoStateInfDto> getAvailableSeasonList(Integer stateCode, Integer commodityId, Integer varietyId);

	@Query(value = "Select distinct Id,Name from (\n"
			+ "				SELECT distinct GS.StateCode,GS.Name as State, ASEA.ID as seasonID,ASEA.Name as Season,\n"
			+ "				AC.ID AS Id,AC.Name as Name ,\n"
			+ "				AV.ID as varietyID, AV.Name as variety\n"
			+ "				FROM agri_phenophase_duration APD \n"
			+ "				LEFT JOIN regional_variety RV ON (APD.StateCode = RV.StateCode)\n"
			+ "				LEFT JOIN geo_region GR ON (RV.StateCode = GR.StateCode)\n"
			+ "				LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID and RV.CommodityID = AC.ID)\n"
			+ "				LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "				LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID and RV.VarietyID = AV.ID)\n"
			+ "				LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode and RV.StateCode = GS.StateCode)\n"
			+ "				LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID) where GR.RegionID is not null and AC.ID is not null \n"
			+ "				and AV.ID is not null and GS.StateCode = ?1) as temp\n"
			+ "                order by Name", nativeQuery = true)
	List<GeoStateInfDto> getCommodityByState(Integer stateCode);

	@Query(value = "Select distinct Id,Name from (\n"
			+ "				SELECT distinct GS.StateCode,GS.Name as State, ASEA.ID as seasonID,ASEA.Name as Season,\n"
			+ "				AC.ID AS CommodityId,AC.Name as Commodity ,\n"
			+ "				AV.ID as Id, AV.Name as Name\n" + "				FROM agri_phenophase_duration APD \n"
			+ "				LEFT JOIN regional_variety RV ON (APD.StateCode = RV.StateCode)\n"
			+ "				LEFT JOIN geo_region GR ON (RV.StateCode = GR.StateCode)\n"
			+ "				LEFT JOIN agri_commodity AC ON (APD.CommodityID = AC.ID and RV.CommodityID = AC.ID)\n"
			+ "				LEFT JOIN agri_season ASEA ON (APD.SeasonID = ASEA.ID)\n"
			+ "				LEFT JOIN agri_variety AV ON (APD.VarietyID = AV.ID and RV.VarietyID = AV.ID)\n"
			+ "				LEFT JOIN geo_state GS ON (APD.StateCode = GS.StateCode and RV.StateCode = GS.StateCode)\n"
			+ "				LEFT JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID) where GR.RegionID is not null and AC.ID is not null \n"
			+ "				and AV.ID is not null and GS.StateCode = ?1 and AC.ID = ?2) as temp\n"
			+ "                order by Name", nativeQuery = true)
	List<GeoStateInfDto> getVarietyByStateAndCommodity(Integer stateCode, Integer commodityId);

}
