package in.cropdata.toolsuite.drk.repository.tileassignment;

import in.cropdata.toolsuite.drk.model.masterdata.DrkrishSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.dao.lh
 * @date 18/02/21
 * @time 10:39 AM
 */
@Repository
public interface DrkrishSourceRepository extends JpaRepository<DrkrishSource, Integer>
{
    @Query(value = "select group_concat(distinct DistrictCode) as districtCode from cdt_master_data.geo_district  \n" +
            "where RegionID in (select RegionID from cdt_master_data.geo_region where\n" +
            "SourceID = (select id from cdt_master_data.drkrishi_source where ApiKey = ?1))",nativeQuery = true)
    String findByApiKey(String apiKey);
}
