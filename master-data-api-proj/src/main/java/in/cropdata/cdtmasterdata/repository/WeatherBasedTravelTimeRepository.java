package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.model.WeatherBasedTravelTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 09/11/20
 * @time 6:24 PM
 */
@Repository
public interface WeatherBasedTravelTimeRepository extends JpaRepository<WeatherBasedTravelTime, Integer> {

	@Query(value = "select ID,Name, MinPerKm, Status from weather_based_travel_time where Name like :searchText", countQuery = "select ID,Name, MinPerKm, Status from cdt_master_data.weather_based_travel_time  where Name like :searchText", nativeQuery = true)
	Page<WeatherBasedTravelTime> getListOfWeatherBasedTravelTimeWithPage(Pageable sortedByIdAsc, String searchText);
}
