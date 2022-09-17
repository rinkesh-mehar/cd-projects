package in.cropdata.portal.repository;

import in.cropdata.portal.dto.DirectorSignatoryDetailsINF;
import in.cropdata.portal.model.ApplicationSignatoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Vivek Gajbhiye
 */
public interface ApplicationSignatoryDetailsRepository extends JpaRepository<ApplicationSignatoryDetails,Integer> {

    Optional<ApplicationSignatoryDetails> findByApplicantID(Integer id);

    @Query(value = "SELECT CompanyAuthSignDesignationID as directorsProprietorPartnerDesignation,CompanyAuthSignFirstName AS directorsProprietorPartnerFirstName,CompanyAuthSignMiddleName AS directorsProprietorPartnerMiddleName,CompanyAuthSignLastName AS directorsProprietorPartnerLastName,     CompanyAuthSignEmail as directorsProprietorPartnerEmail,     CompanyTelephoneNumber as directorsProprietorPartnerTelephoneNo,     CompanyMobileNumber as directorsProprietorPartnerMobileNo,     OtherDesignation as directorsProprietorPartnerOtherDesignation,     TelephoneIsdCode as directorsProprietorPartnerTelephoneNoPrefix,     IsdCode as directorsProprietorPartnerMobileNoPrefix FROM     application_signatory_details WHERE     ApplicantID = ?1",nativeQuery = true)
    List<DirectorSignatoryDetailsINF> getByApplicationID(Integer id);

    @Modifying
    @Query(value="delete from cdt_website.application_signatory_details where ApplicantID=?1 limit 100",nativeQuery = true)
    Integer deleteSig(Integer id);



}
