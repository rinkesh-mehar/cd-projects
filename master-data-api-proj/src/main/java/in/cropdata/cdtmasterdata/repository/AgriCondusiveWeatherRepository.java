package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriCondusiveWeatherInfDto;
import in.cropdata.cdtmasterdata.model.AgriCondusiveWeather;

public interface AgriCondusiveWeatherRepository extends JpaRepository<AgriCondusiveWeather, Integer> {

	@Query(value = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" + 
			"								ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" + 
			"								ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" + 
			"								ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" + 
			"								AC.Name as Commodity, AST.Name as StressType,ACW.Status, agri_stress.Name as Stress, ACW.IsValid, ACW.ErrorMessage\n" + 
			"								FROM agri_conducive_weather ACW\n" + 
			"					           LEFT join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" + 
			"					           LEFT join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" + 
			"								LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" + 
			"                                left Join agri_stress agri_stress on(agri_stress.ID = ACS.StressID and agri_stress.StressTypeID = AST.ID) \n" + 
			"								LEFT join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" + 
			"								LEFT join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" + 
			"								where agri_stress.Name like :searchText\n" + 
			"								OR GWPP.Name like :searchText\n" + 
			"								OR GWPS.Name like :searchText\n" + 
			"								OR AC.Name like :searchText\n" + 
			"								OR AST.Name like :searchText\n" + 
			"								OR ACW.PrimarySpecificationAverage like :searchText\n" + 
			"								OR ACW.PrimarySpecificationLower like :searchText\n" + 
			"								OR ACW.PrimarySpecificationUpper like :searchText\n" + 
			"								OR ACW.PrimaryStressDurationPast like :searchText\n" + 
			"								OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
			"								OR ACW.SecondarySpecificationAverage like :searchText\n" + 
			"								OR ACW.SecondarySpecificationLower like :searchText\n" + 
			"								OR ACW.SecondarySpecificationUpper like :searchText\n" + 
			"								OR ACW.SecondaryStressDurationPast like :searchText\n" + 
			"								OR ACW.SecondaryStressDurationFuture like :searchText", 
			countQuery = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" + 
					"								ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" + 
					"								ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" + 
					"								ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" + 
					"								AC.Name as Commodity, AST.Name as StressType,ACW.Status, agri_stress.Name as Stress, ACW.IsValid, ACW.ErrorMessage\n" + 
					"								FROM agri_conducive_weather ACW\n" + 
					"					           LEFT join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" + 
					"					           LEFT join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" + 
					"								LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" + 
					"                                left Join agri_stress agri_stress on(agri_stress.ID = ACS.StressID and agri_stress.StressTypeID = AST.ID) \n" + 
					"								LEFT join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" + 
					"								LEFT join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" + 
					"								where agri_stress.Name like :searchText\n" + 
					"								OR GWPP.Name like :searchText\n" + 
					"								OR GWPS.Name like :searchText\n" + 
					"								OR AC.Name like :searchText\n" + 
					"								OR AST.Name like :searchText\n" + 
					"								OR ACW.PrimarySpecificationAverage like :searchText\n" + 
					"								OR ACW.PrimarySpecificationLower like :searchText\n" + 
					"								OR ACW.PrimarySpecificationUpper like :searchText\n" + 
					"								OR ACW.PrimaryStressDurationPast like :searchText\n" + 
					"								OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
					"								OR ACW.SecondarySpecificationAverage like :searchText\n" + 
					"								OR ACW.SecondarySpecificationLower like :searchText\n" + 
					"								OR ACW.SecondarySpecificationUpper like :searchText\n" + 
					"								OR ACW.SecondaryStressDurationPast like :searchText\n" + 
					"								OR ACW.SecondaryStressDurationFuture like :searchText", nativeQuery = true)
	Page<AgriCondusiveWeatherInfDto> getAgriCondusiveWeather(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" + 
			"								ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" + 
			"								ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" + 
			"								ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" + 
			"								AC.Name as Commodity, AST.Name as StressType,ACW.Status, agri_stress.Name as Stress, ACW.ErrorMessage\n" + 
			"								FROM agri_conducive_weather ACW\n" + 
			"								left join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" + 
			"								left join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" + 
			"								LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" + 
			"                                left Join agri_stress agri_stress on(agri_stress.ID = ACS.StressID and agri_stress.StressTypeID = AST.ID)\n" + 
			"								left join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" + 
			"								left join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" + 
			"								where ACW.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
			"								OR GWPP.Name like :searchText\n" + 
			"								OR GWPS.Name like :searchText\n" + 
			"								OR AC.Name like :searchText\n" + 
			"								OR AST.Name like :searchText\n" + 
			"								OR ACW.PrimarySpecificationAverage like :searchText\n" + 
			"								OR ACW.PrimarySpecificationLower like :searchText\n" + 
			"								OR ACW.PrimarySpecificationUpper like :searchText\n" + 
			"								OR ACW.PrimaryStressDurationPast like :searchText\n" + 
			"								OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
			"								OR ACW.SecondarySpecificationAverage like :searchText\n" + 
			"								OR ACW.SecondarySpecificationLower like :searchText\n" + 
			"								OR ACW.SecondarySpecificationUpper like :searchText\n" + 
			"								OR ACW.SecondaryStressDurationPast like :searchText\n" + 
			"								OR ACW.SecondaryStressDurationFuture like :searchText)",
			countQuery = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" + 
					"								ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" + 
					"								ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" + 
					"								ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" + 
					"								AC.Name as Commodity, AST.Name as StressType,ACW.Status, agri_stress.Name as Stress, ACW.ErrorMessage\n" + 
					"								FROM agri_conducive_weather ACW\n" + 
					"								left join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" + 
					"								left join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" + 
					"								LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" + 
					"                                left Join agri_stress agri_stress on(agri_stress.ID = ACS.StressID and agri_stress.StressTypeID = AST.ID)\n" + 
					"								left join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" + 
					"								left join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" + 
					"								where ACW.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
					"								OR GWPP.Name like :searchText\n" + 
					"								OR GWPS.Name like :searchText\n" + 
					"								OR AC.Name like :searchText\n" + 
					"								OR AST.Name like :searchText\n" + 
					"								OR ACW.PrimarySpecificationAverage like :searchText\n" + 
					"								OR ACW.PrimarySpecificationLower like :searchText\n" + 
					"								OR ACW.PrimarySpecificationUpper like :searchText\n" + 
					"								OR ACW.PrimaryStressDurationPast like :searchText\n" + 
					"								OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
					"								OR ACW.SecondarySpecificationAverage like :searchText\n" + 
					"								OR ACW.SecondarySpecificationLower like :searchText\n" + 
					"								OR ACW.SecondarySpecificationUpper like :searchText\n" + 
					"								OR ACW.SecondaryStressDurationPast like :searchText\n" + 
					"								OR ACW.SecondaryStressDurationFuture like :searchText)", nativeQuery = true)
	Page<AgriCondusiveWeatherInfDto> getAgriCondusiveWeatherInvalidated(Pageable sortedByIdDesc, String searchText);

	
	
	@Query(value = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" +
			"			ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" +
			"			ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" +
			"			ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" +
			"			AC.Name as Commodity, AST.Name as StressType,ACW.Status, ACS.Name as Stress \n" +
			"			FROM agri_conducive_weather_missing ACW \n" +
			"			left join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" +
			"			left join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" +
			"			LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" +
			"			left join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" +
			"			left join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" +
			"			where ACW.IsValid = 0 and (ACS.Name like :searchText\n" +
			"			OR GWPP.Name like :searchText\n" +
			"			OR GWPS.Name like :searchText\n" +
			"			OR AC.Name like :searchText\n" +
			"			OR AST.Name like :searchText\n" +
			"			OR ACS.Name like :searchText\n" +
			"			OR ACW.PrimarySpecificationAverage like :searchText\n" +
			"			OR ACW.PrimarySpecificationLower like :searchText\n" +
			"			OR ACW.PrimarySpecificationUpper like :searchText\n" +
			"			OR ACW.PrimaryStressDurationPast like :searchText\n" +
			"			OR ACW.PrimaryStressDurationFuture like :searchText\n" +
			"			OR ACW.SecondarySpecificationAverage like :searchText\n" +
			"			OR ACW.SecondarySpecificationLower like :searchText\n" +
			"			OR ACW.SecondarySpecificationUpper like :searchText\n" +
			"			OR ACW.SecondaryStressDurationPast like :searchText\n" +
			"			OR ACW.SecondaryStressDurationFuture like :searchText)",
			countQuery = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" +
					"			ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" +
					"			ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" +
					"			ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" +
					"			AC.Name as Commodity, AST.Name as StressType,ACW.Status, ACS.Name as Stress \n" +
					"			FROM agri_conducive_weather_missing ACW \n" +
					"			left join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" +
					"			left join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" +
					"			LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" +
					"			left join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" +
					"			left join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" +
					"			where ACW.IsValid = 0 and (ACS.Name like :searchText\n" +
					"			OR GWPP.Name like :searchText\n" +
					"			OR GWPS.Name like :searchText\n" +
					"			OR AC.Name like :searchText\n" +
					"			OR AST.Name like :searchText\n" +
					"			OR ACS.Name like :searchText\n" +
					"			OR ACW.PrimarySpecificationAverage like :searchText\n" +
					"			OR ACW.PrimarySpecificationLower like :searchText\n" +
					"			OR ACW.PrimarySpecificationUpper like :searchText\n" +
					"			OR ACW.PrimaryStressDurationPast like :searchText\n" +
					"			OR ACW.PrimaryStressDurationFuture like :searchText\n" +
					"			OR ACW.SecondarySpecificationAverage like :searchText\n" +
					"			OR ACW.SecondarySpecificationLower like :searchText\n" +
					"			OR ACW.SecondarySpecificationUpper like :searchText\n" +
					"			OR ACW.SecondaryStressDurationPast like :searchText\n" +
					"			OR ACW.SecondaryStressDurationFuture like :searchText)", nativeQuery = true)
	Page<AgriCondusiveWeatherInfDto> getAgriCondusiveWeatherMissingListInvalidated(Pageable sortedByIdDesc,
			String searchText);

	@Query(value = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" + 
			"			ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" + 
			"			ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" + 
			"			ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" + 
			"			AC.Name as Commodity, AST.Name as StressType,ACW.Status, ACS.Name as Stress \n" +
			"			FROM agri_conducive_weather_missing ACW \n" + 
			"			left join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" + 
			"			left join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" + 
			"			LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" +
			"			left join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" + 
			"			left join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)\n" + 
			"			where ACS.Name like :searchText\n" + 
			"			OR GWPP.Name like :searchText\n" + 
			"			OR GWPS.Name like :searchText\n" + 
			"			OR AC.Name like :searchText\n" + 
			"			OR AST.Name like :searchText\n" + 
			"			OR ACS.Name like :searchText\n" + 
			"			OR ACW.PrimarySpecificationAverage like :searchText\n" + 
			"			OR ACW.PrimarySpecificationLower like :searchText\n" + 
			"			OR ACW.PrimarySpecificationUpper like :searchText\n" + 
			"			OR ACW.PrimaryStressDurationPast like :searchText\n" + 
			"			OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
			"			OR ACW.SecondarySpecificationAverage like :searchText\n" + 
			"			OR ACW.SecondarySpecificationLower like :searchText\n" + 
			"			OR ACW.SecondarySpecificationUpper like :searchText\n" + 
			"			OR ACW.SecondaryStressDurationPast like :searchText\n" + 
			"			OR ACW.SecondaryStressDurationFuture like :searchText", 
			countQuery = "SELECT ACW.ID,ACW.CommodityID,ACW.StressTypeID,ACW.StressID,ACW.PrimaryWeatherParameterID,ACW.PrimarySpecificationAverage,\n" +
					"			ACW.PrimarySpecificationLower,ACW.PrimarySpecificationUpper,ACW.PrimaryStressDurationPast,ACW.PrimaryStressDurationFuture,\n" +
					"			ACW.SecondaryWeatherParameterID,ACW.SecondarySpecificationAverage,ACW.SecondarySpecificationLower,ACW.SecondarySpecificationUpper,\n" +
					"			ACW.SecondaryStressDurationPast,ACW.SecondaryStressDurationFuture,GWPP.Name as PrimaryWeatherParameter,GWPS.Name as SecondaryWeatherParameter,\n" +
					"			AC.Name as Commodity, AST.Name as StressType,ACW.Status, ACS.Name as Stress \n" +
					"			FROM agri_conducive_weather_missing ACW\n" +
					"           LEFT join agri_commodity AC on(ACW.CommodityID = AC.ID)\n" +
					"           LEFT join agri_stress_type AST on(ACW.StressTypeID = AST.ID)\n" +
					"			LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ACW.StressID)\n" +
					"			LEFT join general_weather_params GWPP on(ACW.PrimaryWeatherParameterID = GWPP.ID)\n" +
					"			LEFT join general_weather_params GWPS on(ACW.SecondaryWeatherParameterID = GWPS.ID)" +
					"			where ACS.Name like :searchText\n" + 
					"			OR GWPP.Name like :searchText\n" + 
					"			OR GWPS.Name like :searchText\n" + 
					"			OR AC.Name like :searchText\n" + 
					"			OR AST.Name like :searchText\n" + 
					"			OR ACS.Name like :searchText\n" + 
					"			OR ACW.PrimarySpecificationAverage like :searchText\n" + 
					"			OR ACW.PrimarySpecificationLower like :searchText\n" + 
					"			OR ACW.PrimarySpecificationUpper like :searchText\n" + 
					"			OR ACW.PrimaryStressDurationPast like :searchText\n" + 
					"			OR ACW.PrimaryStressDurationFuture like :searchText\n" + 
					"			OR ACW.SecondarySpecificationAverage like :searchText\n" + 
					"			OR ACW.SecondarySpecificationLower like :searchText\n" + 
					"			OR ACW.SecondarySpecificationUpper like :searchText\n" + 
					"			OR ACW.SecondaryStressDurationPast like :searchText\n" + 
					"			OR ACW.SecondaryStressDurationFuture like :searchText", nativeQuery = true)
	Page<AgriCondusiveWeatherInfDto> getAgriCondusiveWeatherMissingList(Pageable sortedByIdDesc, String searchText);

}
