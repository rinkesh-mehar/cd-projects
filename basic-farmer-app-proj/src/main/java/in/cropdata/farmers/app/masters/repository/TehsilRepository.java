/**
 * 
 */
package in.cropdata.farmers.app.masters.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.masters.model.Tehsil;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface TehsilRepository extends JpaRepository<Tehsil, Integer> {
	
	@Query(value = "SELECT * FROM cdt_master_data.geo_tehsil t where t.DistrictCode = ?1  AND t.Name != '0'",nativeQuery = true)
	List<Tehsil> findAllByDistrictCode(int districtCode);
	
	
	@Query(value = "select c.ID as code, c.Name as name from cdt_master_data.geo_city c where c.DistrictCode = ?1 and c.Name != '0'",nativeQuery = true)
	List<Map<String,Object>> findGeoCityByDistrictCode(int districtCode);
	

}
