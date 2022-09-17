package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import in.cropdata.cdtmasterdata.dto.interfaces.GeneralWeatherParameterDto;
import in.cropdata.cdtmasterdata.model.WeatherParams;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherParamsRepository extends JpaRepository<WeatherParams, Integer>
{
    Optional<WeatherParams> findByNameAndLabel(final String weatherParameterName, final String weatherLabel);
    
	@Query(value="SELECT gwp.ID,gwp.Name,gwp.Label,gwp.Status,gu.Name as Unit FROM cdt_master_data.general_weather_params gwp \n" + 
			"Inner join general_uom gu on(gu.ID=gwp.UnitID)\n" + 
			"where gwp.ID like  :searchText OR gwp.Name like :searchText OR  gwp.Label like :searchText OR gwp.Status like  :searchText OR  gu.Name like :searchText",countQuery = "SELECT gwp.ID,gwp.Name,gwp.Label,gwp.Status,gu.Name as Unit FROM cdt_master_data.general_weather_params gwp \n" + 
					"Inner join general_uom gu on(gu.ID=gwp.UnitID)\n" + 
					"where gwp.ID like  :searchText OR gwp.Name like :searchText OR  gwp.Label like :searchText OR gwp.Status like  :searchText OR  gu.Name like :searchText",nativeQuery = true)
	Page<GeneralWeatherParameterDto> getWeatherParamListByPagenation(Pageable sortedByIdDesc, String searchText);
}
