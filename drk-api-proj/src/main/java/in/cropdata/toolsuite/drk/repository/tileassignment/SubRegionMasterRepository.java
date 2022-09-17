package in.cropdata.toolsuite.drk.repository.tileassignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.model.tileassignment.GeoSubRegion;

public interface SubRegionMasterRepository extends JpaRepository<GeoSubRegion, Long> {
    List<GeoSubRegion> findAllByRegionID(int regionID);
    
    @Query(value = "SELECT * FROM cdt_master_data.geo_subregion where RegionID=?1 order by Latitude, SubRegionID;",nativeQuery = true)
    List<GeoSubRegion> getAllBySortedData(int regionID);
}
