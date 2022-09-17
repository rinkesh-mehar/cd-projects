package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;

@Repository
public interface AgriStageRepository extends JpaRepository<AgriStage, Integer> {
	
	@Query(value = "SELECT ID,Name,Description,Status FROM agri_stage\n" + 
			"where ID like :searchText OR Name like :searchText OR Description like :searchText OR Status like :searchText", countQuery = "SELECT ID,Name,Description,Status FROM agri_stage\n" + 
					"where ID like :searchText OR Name like :searchText OR Description like :searchText OR Status like :searchText", nativeQuery = true)
	Page<AgriStageVO> getStagePaginatedList(Pageable sortedByIdDesc,String searchText);
	
	@Modifying
	@Transactional
	@Query(value="update agri_stage set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveStage(Integer id);
	
	@Modifying
	@Transactional
	@Query(value="update agri_stage set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectStage(Integer id);
	
	@Modifying
	@Transactional
	@Query(value="update agri_stage set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeStage(Integer id);

}
