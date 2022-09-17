package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriRecommendation;
import in.cropdata.cdtmasterdata.model.vo.AgriRecommendationVo;

@Repository
public interface AgriRecommendationRepository extends JpaRepository<AgriRecommendation, Integer> {
	
	
	@Query(value = "select AR.ID,AR.Name,AR.Status from agri_recommendation AR\n" + 
			"where AR.ID like :searchText OR AR.Name like :searchText\n" + 
			"OR AR.Status like :searchText", 
			countQuery = "select AR.ID,AR.Name,AR.Status from agri_recommendation AR\n" + 
					"where AR.ID like :searchText OR AR.Name like :searchText\n" + 
					"OR AR.Status like :searchText", nativeQuery = true)
	Page<AgriRecommendationVo> getRecommendationPagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_recommendation set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveRecommendation(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_recommendation set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeRecommendation(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_recommendation set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectRecommendation(Integer id);


}
