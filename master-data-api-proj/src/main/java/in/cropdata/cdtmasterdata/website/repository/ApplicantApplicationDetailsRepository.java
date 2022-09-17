package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ApplicantApplicationDetails;


@Repository
public interface ApplicantApplicationDetailsRepository extends JpaRepository<ApplicantApplicationDetails, Integer>{

	ApplicantApplicationDetails findByIdAndApplicantID(Integer id,Integer ApplicantId);
	
}
