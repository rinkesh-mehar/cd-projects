package in.cropdata.portal.repository;

import in.cropdata.portal.dto.ApplicantDetailsInf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ApplicantDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface ApplicantDetailsRepository extends JpaRepository<ApplicantDetails, Integer> {

//    @Query(value = "SELECT ID FROM cdt_website.alpplicant_details where AuthToken =?1",nativeQuery = true)
//    public Integer findByAuthToken(String authToken);

    Optional<ApplicantDetails> findByAuthToken(String authToken);

    Optional<ApplicantDetails> findByMobileNumber(String mobileNumber);

    @Query(value = "SELECT AD.MobileNumber as mobileNo,AD.EmailAddress as email,AD.StepsCompleted as step,AAD.companyName, AD.countryCode, AD.IsdCode, AAD.ParentReference FROM cdt_website.alpplicant_details AD \n" +
            "inner join applicant_application_details AAD ON AAD.ApplicantID=AD.ID\n" +
            "where AuthToken=?1",nativeQuery = true)
    ApplicantDetailsInf getApplicantData(String token);

    @Modifying
    @Transactional
    @Query(value = "update cdt_website.alpplicant_details set  TAndCID=(SELECT ID FROM cdt_website.terms_and_conditions where PlatformID=3 and Status='Active') where ID=?1",nativeQuery = true)
    public Integer isApplicantActive(Integer id);



}
