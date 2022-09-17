package in.cropdata.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ApplicantCommoditiesOfInterest;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApplicantCommoditiesOfInterestRepository extends JpaRepository<ApplicantCommoditiesOfInterest, Integer> {


    @Query(value="SELECT CommodityID FROM cdt_website.applicant_commodities_of_interest where ApplicantApplicationID=?1",nativeQuery = true)
    List<Integer> getApplicantApplicationIDAndCommodityID(Integer applicantApplicationID);


//    @Transactional
//    @Modifying
//    @Query(value="update cdt_website.applicant_commodities_of_interest set Status = 'Deleted' where ApplicantApplicationID =?1 and CommodityID=?2",nativeQuery = true)
//    public Integer updateCommoditiesOfInterest(Integer applicantApplicationID,Integer commodityID);

    @Transactional
    @Modifying
    @Query(value="delete from cdt_website.applicant_commodities_of_interest where ApplicantApplicationID =?1 and CommodityID=?2 limit 100",nativeQuery = true)
    public Integer updateCommoditiesOfInterest(Integer applicantApplicationID,Integer commodityID);

}
