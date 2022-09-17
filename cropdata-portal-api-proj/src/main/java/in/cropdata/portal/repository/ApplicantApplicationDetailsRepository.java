package in.cropdata.portal.repository;

import in.cropdata.portal.model.ApplicantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ApplicantApplicationDetails;

import java.util.Optional;

@Repository
public interface ApplicantApplicationDetailsRepository extends JpaRepository<ApplicantApplicationDetails, Integer>{

    Optional<ApplicantApplicationDetails> findByApplicantID(Integer applicantID);

    Optional<ApplicantApplicationDetails> findByBusinessAddressID(Integer id);

    Optional<ApplicantApplicationDetails> findApplicantApplicationDetailsByReference(String referenceId);

}
