package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.FarmerKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 01/02/21
 */

@Repository
public interface FarmerKYCRepository extends JpaRepository<FarmerKYC, String> {

    List<FarmerKYC> findAllByFarmerID(String farmerId);

    @Query(value = "select ID, DocTypeID, FarmerID, PermanentAddress, FarmerPhoto, FarmerPhoto from farmers_app.farmer_kyc where FarmerID = ?1", nativeQuery = true)
    List<FarmerKYC> findByFarmerID(String farmerID);

    Optional<FarmerKYC> findByFarmerIDAndDocTypeID(String farmerID, Integer docTypeID);
    
    
    
    @Modifying
    @Transactional
    @Query(value = "update drkrishi_ts.farmer_kyc fk \n" + 
    		"inner join drkrishi_ts.farmer f on f.id = fk.farmer_id\n" + 
    		"set fk.address_id = if(f.address_id is null,null,f.address_id) \n" + 
    		"where f.id = fk.farmer_id",nativeQuery = true)
    void updateFarmerKyc();
    
    
}
