package in.cropdata.aefc.repository;

import in.cropdata.aefc.DTO.BayerLoginOtpDTO;
import in.cropdata.aefc.entity.ApplicantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.repository
 * @date 05/12/21
 * @time 5:37 PM
 */

@Repository
public interface ApplicantDetailsRepository extends JpaRepository<ApplicantDetails, Integer>
{
    ApplicantDetails findByMobileNumberAndEmailAddress(String mobileNumber, String emailAddress);

    Optional<ApplicantDetails> findAllByMobileNumber(String mobileNo);

    Optional<ApplicantDetails> findByEmailVerificationLink(String code);

    @Query(value = "select ad.ID, ad.IsEmailVerified from alpplicant_details ad WHERE ad.mobileNumber = ?1 AND ad.Otp = ?2 AND current_time() between timestamp(date_sub(ad.OtpGenerationTime, interval ?3 minute)) and timestamp(ad.OtpGenerationTime)", nativeQuery = true)
    BayerLoginOtpDTO verifyOtp(String mobileNumber, String otp, Integer expireTime);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cdt_website.alpplicant_details ad SET IsEmailVerified = 'Yes', Status = 'Working' WHERE EmailAddress = ?1", nativeQuery = true)
    Integer updateEmailStatus(String emailAddress);


    Optional<ApplicantDetails> findApplicantDetailsByAuthToken(String authToken);
}
