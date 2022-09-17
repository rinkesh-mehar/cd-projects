package in.cropdata.toolsuite.drk.repository.tileassignment;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.toolsuite.drk.model.tileassignment.GeoRegion;

public interface RegionMasterRepository extends JpaRepository<GeoRegion, Integer> {

}
