package in.cropdata.portal.worldData.repository;

import in.cropdata.portal.worldData.model.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.repository
 * @date 13/12/21
 * @time 6:21 PM
 */
@Repository
public interface CountriesRepository extends JpaRepository<Countries, String>
{
    @Query(value = "SELECT  code, concat(name," +
            "' ('," +
            " code" +
            ",')') as name, countrycode FROM countries where " +
            "(name LIKE :searchText) order by name", nativeQuery = true)
    List<Countries> countriesList(String searchText);

    @Query(value = "select code, name, countryCode from countries where code = ?1", nativeQuery = true)
    Countries getCountryCode(String code);
}
