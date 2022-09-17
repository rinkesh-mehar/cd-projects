package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ApplicantApplicationDetails;
import in.cropdata.cdtmasterdata.website.model.ApplicantContactDetails;


@Repository
public interface ApplicantContactDetailsRepository extends JpaRepository<ApplicantContactDetails, Integer> {
	
	ApplicantContactDetails findByApplicantIDAndApplicationAplicantID(Integer applicantID,Integer applicationAplicantID);

}
