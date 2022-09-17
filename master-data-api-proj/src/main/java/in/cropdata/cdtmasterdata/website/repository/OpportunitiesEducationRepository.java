package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.OpportunitiesEducation;

@Repository
public interface OpportunitiesEducationRepository extends JpaRepository<OpportunitiesEducation, Integer>{
	
	@Query(value = "SELECT EducationID FROM cdt_website.opportunities_education where OpportunityID = ?1", nativeQuery = true)
	Integer[] findEducationIdByOpportunityID(Integer opportunityId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from cdt_website.opportunities_education where OpportunityID = ?1 and EducationID = ?2", nativeQuery = true)
	void deleteByOpportunityIDAndEducationID(Integer opportunityId, Integer educationId);
	
	OpportunitiesEducation findByOpportunityIDAndEducationID(Integer opportunityId, Integer educationId);

}
