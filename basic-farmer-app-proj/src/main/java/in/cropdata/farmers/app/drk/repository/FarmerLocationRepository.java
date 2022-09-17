/**
 * 
 */
package in.cropdata.farmers.app.drk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.drk.model.FarmerLocation;

@Repository
public interface FarmerLocationRepository extends JpaRepository<FarmerLocation, Integer> {

	@Query(value ="SELECT * FROM drkrishi_ts.farmer_location fl where fl.FarmerID = ?1", nativeQuery = true)
	Optional<FarmerLocation> findByFarmerID(String farmerId);
}
