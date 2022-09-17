package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.Engines;
import in.cropdata.cdtmasterdata.website.model.vo.EnginesVO;

@Repository
public interface EnginesRepository extends JpaRepository<Engines, Integer> {

	@Query(value="SELECT \n" + 
			"    engines.ID,\n" + 
			"    UPPER(engines.Name) AS Name,\n" + 
			"    engines.Status,\n" + 
			"    UPPER(pm.Name) As Platform\n" + 
			"FROM\n" + 
			"    engines engines\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master pm ON (pm.ID = engines.PlatformID)\n" + 
			"WHERE\n" + 
			"    engines.Status <> 'Deleted'\n" + 
			"    AND (engines.ID like :searchText\n" + 
			"        OR engines.Name LIKE :searchText\n" + 
			"        OR engines.Status LIKE :searchText\n" + 
			"        OR pm.Name LIKE :searchText)",countQuery = "SELECT \n" + 
					"    engines.ID,\n" + 
					"    UPPER(engines.Name) AS Name,\n" + 
					"    engines.Status,\n" + 
					"    UPPER(pm.Name) As Platform\n" + 
					"FROM\n" + 
					"    engines engines\n" + 
					"        INNER JOIN\n" + 
					"    cdt_master_data.platform_master pm ON (pm.ID = engines.PlatformID)\n" + 
					"WHERE\n" + 
					"    engines.Status <> 'Deleted'\n" + 
					"    AND (engines.ID like :searchText\n" + 
					"        OR engines.Name LIKE :searchText\n" + 
					"        OR engines.Status LIKE :searchText\n" + 
					"        OR pm.Name LIKE :searchText)",nativeQuery = true)
	Page<EnginesVO> getEnginesListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from engines where Status <> 'Deleted' and Name= ?1 and PlatformID = ?2",nativeQuery = true)
	Integer findAlreadyExistEnginesForAddMode(String name,Integer platformId);
	
	@Query(value="select ID,Name,PlatformID from engines where Status in('Active') order by Name",nativeQuery = true)
	List<EnginesVO> getEnginesList();
	
	@Query(value="select count(*) as count from engines where Status <> 'Deleted' and ID <> ?1 and Name= ?2 and PlatformID = ?3",nativeQuery = true)
	Integer findAlreadyExistEnginesForEditMode(Integer id, String name,Integer platformId);
	
	@Modifying
    @Transactional
	@Query(value="update engines set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeEngines(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update engines set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveEngines(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update engines set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteEngines(Integer id);
	
}
