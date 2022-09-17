package in.cropdata.portal.repository;

import in.cropdata.portal.model.ApplicantBankDetails;
import in.cropdata.portal.model.ApplicantContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Vivek Gajbhiye
 */
public interface ApplicantBankDetailsRepository extends JpaRepository<ApplicantBankDetails,Integer> {

    public Optional<ApplicantBankDetails> findByApplicantID(Integer applicantID);
}
