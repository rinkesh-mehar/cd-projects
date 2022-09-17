package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriStressStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStressStageVO;

@Repository
public interface AgriStressStageRepository extends JpaRepository<AgriStressStage, Integer> {
	
	
	@Query(value = "select ass.ID,agri_stress.Name as Stress,agri_stage.Name as Stage,ass.Status from agri_stress_stage ass\n" + 
			"inner join agri_stress agri_stress on (agri_stress.ID = ass.StressID)\n" + 
			"inner join agri_stage agri_stage on (agri_stage.ID = ass.StageID)\n" + 
			"where ass.ID like :searchText OR agri_stress.Name like :searchText OR agri_stage.Name like :searchText", 
			countQuery = "select ass.ID,agri_stress.Name as Stress,agri_stage.Name as Stage,ass.Status from agri_stress_stage ass\n" + 
					"inner join agri_stress agri_stress on (agri_stress.ID = ass.StressID)\n" + 
					"inner join agri_stage agri_stage on (agri_stage.ID = ass.StageID)\n" + 
					"where ass.ID like :searchText OR agri_stress.Name like :searchText OR agri_stage.Name like :searchText", nativeQuery = true)
	Page<AgriStressStageVO> getStressStagePagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_stress_stage set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveStressStage(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_stress_stage set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeStressStage(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_stress_stage set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectStressStage(Integer id);


}
