package in.cropdata.portal.repository;

import in.cropdata.portal.model.TermsAndConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.repository
 * @date 15/12/21
 * @time 12:38 PM
 */
@Repository
public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions, Integer>
{
    @Query(value = "select Id, PlatformID, TAndCUrl,PrivacyPolicyUrl,Version, status from terms_and_conditions where PlatformID= ?1 and status='Active' limit 1", nativeQuery = true)
    List<TermsAndConditions> getTermsConditionUrl(Integer platformId);
}
