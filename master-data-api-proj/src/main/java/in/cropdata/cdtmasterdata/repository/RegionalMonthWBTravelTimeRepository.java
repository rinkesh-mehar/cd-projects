package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.model.RegionalMonthWBTravelTime;
import in.cropdata.cdtmasterdata.model.vo.RegionalMonthWBTravelTimeVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 09/11/20
 * @time 11:54 AM
 */
@Repository
public interface RegionalMonthWBTravelTimeRepository extends JpaRepository<RegionalMonthWBTravelTime, Integer>,
        PagingAndSortingRepository<RegionalMonthWBTravelTime, Integer>
{
    @Query(value = "select rmwbt.ID,rmwbt.RegionID,rmwbt.UnitType,rmwbt.Month,concat(wntt.Name,'(' ,wntt.MinPerKm, ')') as unitName,gr.Name as regionName, rmwbt.IsValid, rmwbt.ErrorMessage from regional_monthly_weather_based_travel_time rmwbt\n" +
            "inner join weather_based_travel_time wntt on wntt.ID=rmwbt.UnitType\n" +
            "inner join geo_region gr on gr.RegionID=rmwbt.RegionID where\n" +
            "rmwbt.Month like :searchText \n" +
            "OR wntt.Name like :searchText \n" +
            "OR gr.Name like :searchText",
            countQuery = "select rmwbt.ID,rmwbt.RegionID,rmwbt.UnitType,rmwbt.Month,concat(wntt.Name,'(' ,wntt.MinPerKm, ')') as unitName,gr.Name as regionName, rmwbt.IsValid, rmwbt.ErrorMessage from regional_monthly_weather_based_travel_time rmwbt\n" +
                    "inner join weather_based_travel_time wntt on wntt.ID=rmwbt.UnitType\n" +
                    "inner join geo_region gr on gr.RegionID=rmwbt.RegionID where\n" +
                    "rmwbt.Month like :searchText \n" +
                    "OR wntt.Name like :searchText \n" +
                    "OR gr.Name like :searchText", nativeQuery = true)
    Page<RegionalMonthWBTravelTimeVO> getRegionalMonthWBTravelTime(Pageable pageable, String searchText);

    @Query(value = "select rmwbt.ID,rmwbt.RegionID,rmwbt.UnitType,rmwbt.Month,concat(wntt.Name,'(' ,wntt.MinPerKm, ')') as unitName,gr.Name as regionName, rmwbt.IsValid, rmwbt.ErrorMessage from regional_monthly_weather_based_travel_time rmwbt\n" +
            "inner join weather_based_travel_time wntt on wntt.ID=rmwbt.UnitType\n" +
            "inner join geo_region gr on gr.RegionID=rmwbt.RegionID where rmwbt.IsValid = 0 and(\n" +
            "rmwbt.Month like :searchText \n" +
            "OR wntt.Name like :searchText \n" +
            "OR gr.Name like :searchText)",
            countQuery = "select rmwbt.ID,rmwbt.RegionID,rmwbt.UnitType,rmwbt.Month,concat(wntt.Name,'(' ,wntt.MinPerKm, ')') as unitName,gr.Name as regionName, rmwbt.IsValid, rmwbt.ErrorMessage from regional_monthly_weather_based_travel_time rmwbt\n" +
                    "inner join weather_based_travel_time wntt on wntt.ID=rmwbt.UnitType\n" +
                    "inner join geo_region gr on gr.RegionID=rmwbt.RegionID where rmwbt.IsValid = 0 and(\n" +
                    "rmwbt.Month like :searchText \n" +
                    "OR wntt.Name like :searchText \n" +
                    "OR gr.Name like :searchText)", nativeQuery = true)
    Page<RegionalMonthWBTravelTimeVO> getRegionalMonthWBTravelTimeInvalidated(Pageable pageable, String searchText);
}
