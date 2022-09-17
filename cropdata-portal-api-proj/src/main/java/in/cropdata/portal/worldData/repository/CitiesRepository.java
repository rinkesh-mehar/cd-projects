package in.cropdata.portal.worldData.repository;

import in.cropdata.portal.dto.CitiesDTO;
import in.cropdata.portal.worldData.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.model.repository
 * @date 13/12/21
 * @time 5:19 PM
 */
@Repository
public interface CitiesRepository extends JpaRepository<Cities, Integer>
{
    @Query(value = "select c.ID, c.country, c.region, concat(c.name, ', ', r.name) as name from cities c " +
            "inner join regions r on r.code = c.region and r.country= c.country" +
            " inner join countries ct on ct.code = r.country and ct.code = c.country" +
            " where c.region= ?1 and c.country = ?2 order by c.name", nativeQuery = true)
    List<Cities> getCities(String regionCode, String countryCode);


    @Query(value = "select c.ID, c.country, c.region, concat(c.name, ', ', r.name) as name from cities c " +
            "inner join regions r on r.code = c.region and r.country= c.country" +
            " inner join countries ct on ct.code = r.country and ct.code = c.country" +
            " where c.region= :regionCode and c.country = :countryCode and (c.name LIKE :searchText) order by c.name", nativeQuery = true)
    List<Cities> getSearchCities(String regionCode, String countryCode, String searchText);


    @Query(value = "select ID,country,region,name from cities where name=?1 limit 1",nativeQuery = true)
    CitiesDTO getByNameAndCountry(String name);
}
