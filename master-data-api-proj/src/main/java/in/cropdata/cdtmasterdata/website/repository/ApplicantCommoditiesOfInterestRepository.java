package in.cropdata.cdtmasterdata.website.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.ApplicantCommoditiesOfInterest;


@Repository
public interface ApplicantCommoditiesOfInterestRepository extends JpaRepository<ApplicantCommoditiesOfInterest, Integer> {
	
	@Modifying
    @Transactional
	@Query(value="delete from applicant_commodities_of_interest where ApplicantApplicationID=?1 And CommodityID=?2",nativeQuery = true)
	int deleteCommoditiesOfInterest(Integer applicantApplicationId,Integer commodityId);
	
	@Query(value = "SELECT CommodityID FROM cdt_website.applicant_commodities_of_interest where ApplicantApplicationID= ?1", nativeQuery = true)
	Integer[] findCommoditiesIdByApplicantApplicationID(Integer applicantApplicationId);
	
	ApplicantCommoditiesOfInterest findByApplicantApplicationIDAndCommodityID(Integer applicantApplicationId,Integer commodityId);
	
	

}
