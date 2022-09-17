package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriFavourableWeatherInfDto;
import in.cropdata.cdtmasterdata.model.AgriFavourableWeather;

public interface AgriFavourableWeatherRepository extends JpaRepository<AgriFavourableWeather, Integer> {

	@Query(value = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper,\n" + 
			" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter, AFW.IsValid, AFW.ErrorMessage from agri_favourable_weather AFW\n" +
			"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" + 
			"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" + 
			"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" + 
			"			where GWP.Name like :searchText\n" + 
			"			OR AC.Name like :searchText\n" + 
			"			OR AP.Name like :searchText\n" + 
			"			OR AFW.SpecificationAverage like :searchText\n" + 
			"			OR AFW.SpecificationLower like :searchText\n" + 
			"			OR AFW.SpecificationUpper like :searchText", 
			countQuery = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper,\n" + 
					" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter, AFW.IsValid, AFW.ErrorMessage from agri_favourable_weather AFW\n" +
					"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" + 
					"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" + 
					"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" + 
					"			where GWP.Name like :searchText\n" + 
					"			OR AC.Name like :searchText\n" + 
					"			OR AP.Name like :searchText\n" + 
					"			OR AFW.SpecificationAverage like :searchText\n" + 
					"			OR AFW.SpecificationLower like :searchText\n" + 
					"			OR AFW.SpecificationUpper like :searchText", nativeQuery = true)
	Page<AgriFavourableWeatherInfDto> getFavourableWeather(Pageable pageable, String searchText);

	@Query(value = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper, AFW.IsValid, AFW.ErrorMessage\n" +
			" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter, AFW.ErrorMessage from agri_favourable_weather AFW\n" +
			"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" +
			"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" +
			"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" +
			"			 where AFW.IsValid = 0 and (GWP.Name like :searchText\n" +
			"			OR AC.Name like :searchText\n" +
			"			OR AP.Name like :searchText\n" +
			"			OR AFW.SpecificationAverage like :searchText\n" +
			"			OR AFW.SpecificationLower like :searchText\n" +
			"			OR AFW.SpecificationUpper like :searchText)",
			countQuery = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper, AFW.IsValid, AFW.ErrorMessage\n" +
					" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter, AFW.ErrorMessage from agri_favourable_weather AFW\n" +
					"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" +
					"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" +
					"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" +
					"			where AFW.IsValid = 0 and (GWP.Name like :searchText\n" +
					"			OR AC.Name like :searchText\n" +
					"			OR AP.Name like :searchText\n" +
					"			OR AFW.SpecificationAverage like :searchText\n" +
					"			OR AFW.SpecificationLower like :searchText\n" +
					"			OR AFW.SpecificationUpper like :searchText)", nativeQuery = true)
	Page<AgriFavourableWeatherInfDto> getFavourableWeatherInvalidated(Pageable pageable, String searchText);

	
	
	@Query(value = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper, AFW.IsValid, AFW.ErrorMessage\n" +
			" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter from agri_favourable_weather_missing AFW\n" +
			"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" +
			"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" +
			"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" +
			"			 where AFW.IsValid = 0 and (GWP.Name like :searchText\n" +
			"			OR AC.Name like :searchText\n" +
			"			OR AP.Name like :searchText\n" +
			"			OR AFW.SpecificationAverage like :searchText\n" +
			"			OR AFW.SpecificationLower like :searchText\n" +
			"			OR AFW.SpecificationUpper like :searchText)",
			countQuery = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper, AFW.IsValid, AFW.ErrorMessage\n" +
					" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter from agri_favourable_weather_missing AFW\n" +
					"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" +
					"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" +
					"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" +
					"			where AFW.IsValid = 0 and (GWP.Name like :searchText\n" +
					"			OR AC.Name like :searchText\n" +
					"			OR AP.Name like :searchText\n" +
					"			OR AFW.SpecificationAverage like :searchText\n" +
					"			OR AFW.SpecificationLower like :searchText\n" +
					"			OR AFW.SpecificationUpper like :searchText)", nativeQuery = true)
	Page<AgriFavourableWeatherInfDto> getFavourableWeatherMissingListInvalidated(Pageable sortedByIdDesc,
			String searchText);

	
	@Query(value = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper,\n" + 
			" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter from agri_favourable_weather_missing AFW\n" +
			"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" + 
			"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" + 
			"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" + 
			"			where GWP.Name like :searchText\n" + 
			"			OR AC.Name like :searchText\n" + 
			"			OR AP.Name like :searchText\n" + 
			"			OR AFW.SpecificationAverage like :searchText\n" + 
			"			OR AFW.SpecificationLower like :searchText\n" + 
			"			OR AFW.SpecificationUpper like :searchText", 
			countQuery = "SELECT AFW.ID,AFW.CommodityID,AFW.PhenophaseID,AFW.WeatherParameterID,AFW.SpecificationAverage,AFW.SpecificationLower,AFW.SpecificationUpper,\n" + 
					" AFW.Status,AFW.CreatedAt,AFW.UpdatedAt, AC.Name as Commodity, AP.Name as Phenophase, GWP.Name as WeatherParameter from agri_favourable_weather_missing AFW\n" +
					"			Left Join agri_commodity AC on (AFW.CommodityID = AC.ID)\n" + 
					"			Left join agri_phenophase AP on (AFW.PhenophaseID = AP.ID)\n" + 
					"			Left join general_weather_params GWP on (AFW.WeatherParameterID = GWP.ID)\n" + 
					"			where GWP.Name like :searchText\n" + 
					"			OR AC.Name like :searchText\n" + 
					"			OR AP.Name like :searchText\n" + 
					"			OR AFW.SpecificationAverage like :searchText\n" + 
					"			OR AFW.SpecificationLower like :searchText\n" + 
					"			OR AFW.SpecificationUpper like :searchText", nativeQuery = true)
	Page<AgriFavourableWeatherInfDto> getFavourableWeatherMissingList(Pageable sortedByIdDesc, String searchText);

}
