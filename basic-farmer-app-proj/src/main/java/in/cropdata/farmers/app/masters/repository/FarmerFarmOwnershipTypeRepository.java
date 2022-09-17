/**
 * 
 */
package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.masters.model.FarmerFarmOwnershipType;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface FarmerFarmOwnershipTypeRepository extends JpaRepository<FarmerFarmOwnershipType, Integer> {

	@Query(value = " select ac.ID as id , ac.Name as name from cdt_master_data.farmer_farm_ownership_type ac where ac.Status in ('Active', 'Approved') order by ac.Name ASC ", nativeQuery = true)
	List<FarmerFarmOwnershipType> getAllActivefarmerFarmOwnershipType();
}
