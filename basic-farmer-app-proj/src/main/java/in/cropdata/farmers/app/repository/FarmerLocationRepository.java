/**
 * 
 */
package in.cropdata.farmers.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.model.FarmLocation;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface FarmerLocationRepository extends JpaRepository<FarmLocation, Integer> {
	
	Optional<FarmLocation> findByFarmerId(String farmerId);
}
