package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ApplicantOfficeSpaceDetails;

@Repository
public interface ApplicantOfficeSpaceDetailsRepository extends JpaRepository<ApplicantOfficeSpaceDetails, Integer> {

	ApplicantOfficeSpaceDetails findByApplicantIDAndApplicationAplicantID(Integer applicantID,Integer applicationAplicantID);
	
}
