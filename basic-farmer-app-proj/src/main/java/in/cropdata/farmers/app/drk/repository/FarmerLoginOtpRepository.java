/**
 * 
 */
package in.cropdata.farmers.app.drk.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.FarmerLoginOtpDTO;
import in.cropdata.farmers.app.drk.model.FarmerLoginOtp;


/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface FarmerLoginOtpRepository extends JpaRepository<FarmerLoginOtp, Integer> {
	
	Optional<FarmerLoginOtp> findAllByMobileNo(String mobileNo);

    @Query(value = "select LO.ID from drkrishi_ts.farmer_login_otp LO WHERE LO.PrimaryMobileNumber = ?1 AND LO.Otp = ?2 AND ?3 between timestamp(LO.CreatedAt) and timestamp(LO.Timeout)", nativeQuery = true)
    FarmerLoginOtpDTO verifyOtp(String mobileNumber, String otp, Timestamp timestamp);
    
}
