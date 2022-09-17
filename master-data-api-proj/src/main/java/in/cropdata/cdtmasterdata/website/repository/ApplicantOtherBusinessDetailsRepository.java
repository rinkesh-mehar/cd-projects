package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ApplicantOtherBusinessDetails;


@Repository
public interface ApplicantOtherBusinessDetailsRepository extends JpaRepository<ApplicantOtherBusinessDetails, Integer> {
	
	ApplicantOtherBusinessDetails findByApplicantIDAndApplicationAplicantID(Integer applicantID,Integer applicationAplicantID);

}
