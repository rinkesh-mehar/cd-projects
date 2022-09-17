package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 29/01/21
 */

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    List<District> findAllByStateCode(Integer stateCode);
    
    @Query(value=" select concat(gv.NeighbourDistricts,\",\",gv.DistrictCode) as neighbourDistricts from cdt_master_data.geo_district gv where gv.DistrictCode =?1 ",nativeQuery = true)
    String getNeighbourDistrictsByDistrictCode(Integer districtCode);
    
    
}
