package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.model.GeoPanchayat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.farmers.app.masters.repository
 * @date 05/02/21
 * @time 11:50 AM
 */
@Repository
public interface GeoPanchayatRepository extends JpaRepository<GeoPanchayat, Integer>
{
    @Query(value = "select tehsilCode, panchayatCode, regionID, name, ID from cdt_master_data.geo_panchayat where status = 'Active' and" +
            " tehsilCode= ?1 and name != '0' order by name", nativeQuery = true)
    List<GeoPanchayat> findGeoPanchayatByTehsilCode(Integer tehsilCode);
}
