package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriQualityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;

@Repository
public interface AgriQualityParameterRepository extends JpaRepository<AgriQualityParameter, Integer> {
	
	
	@Query(value = "select AQP.ID,AQP.Name,AQP.Status from agri_quality_parameter AQP\n" + 
			"where AQP.ID like :searchText OR AQP.Name like :searchText\n" + 
			"OR AQP.Status like :searchText", 
			countQuery = "select AQP.ID,AQP.Name,AQP.Status from agri_quality_parameter AQP\n" + 
					"where AQP.ID like :searchText OR AQP.Name like :searchText\n" + 
					"OR AQP.Status like :searchText", nativeQuery = true)
	Page<AgriQualityParameterVo> getQualityParameterPagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_quality_parameter set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveQualityParameter(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_quality_parameter set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeQualityParameter(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_quality_parameter set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectQualityParameter(Integer id);


}
