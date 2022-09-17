package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.FarmerFarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
public interface FarmerFarmRepository extends JpaRepository<FarmerFarm, String> {

    Optional<FarmerFarm> findByFarmerID(String farmerID);
    
    Optional<FarmerFarm> findById(String id);
}
