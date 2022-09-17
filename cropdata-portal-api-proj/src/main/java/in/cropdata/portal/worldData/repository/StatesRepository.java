package in.cropdata.portal.worldData.repository;

import in.cropdata.portal.worldData.model.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.repository
 * @date 14/12/21
 * @time 11:38 AM
 */
@Repository
public interface StatesRepository extends JpaRepository<States, Integer>
{
    @Query(value = "select ID, country, concat(name, ',(', country ,')' ) as name, code  from regions where (name LIKE :searchText) AND country = :countryCode order by name", nativeQuery = true)
    List<States> statesList(String searchText, String countryCode);

    List<States> getAllByCountryOrderByName(String countryCode);



    @Query(value = "SELECT ID,country,code,name FROM world_data.regions where name =?1 limit 1",nativeQuery = true)
    States statename(String name);
}
