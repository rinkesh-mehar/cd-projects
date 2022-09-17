package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.Tools;
import in.cropdata.cdtmasterdata.website.model.vo.ToolsVO;

@Repository
public interface ToolsRepository extends JpaRepository<Tools, Integer> {

	@Query(value="SELECT\n" + 
			"				    tools.ID,\n" + 
			"				    UPPER(tools.Name) AS Name, \n" + 
			"		            UPPER(tools.Description) as Description,\n" + 
			"		            UPPER(engines.Name) as engine,\n" + 
			"				    UPPER(pm.Name) As Platform,\n" + 
			"                    tools.Logo,\n" + 
			"				    tools.Status\n" + 
			"				FROM\n" + 
			"				    tools tools\n" + 
			"				        INNER JOIN \n" + 
			"				    cdt_master_data.platform_master pm ON (pm.ID = tools.PlatformID)\n" + 
			"		            Left Join engines engines On (engines.ID = tools.EngineID) \n" + 
			"				WHERE\n" + 
			"				    tools.Status <> 'Deleted'\n" + 
			"				    AND (tools.ID like :searchText \n" + 
			"				        OR tools.Name LIKE :searchText\n" + 
			"						OR tools.Description like :searchText\n" + 
			"		                OR engines.Name like :searchText \n" + 
			"				        OR pm.Name LIKE :searchText\n" + 
			"				        OR tools.Status LIKE :searchText)",countQuery = "SELECT\n" + 
					"				    tools.ID,\n" + 
					"				    UPPER(tools.Name) AS Name, \n" + 
					"		            UPPER(tools.Description) as Description,\n" + 
					"		            UPPER(engines.Name) as engine,\n" + 
					"				    UPPER(pm.Name) As Platform,\n" + 
					"                    tools.Logo,\n" + 
					"				    tools.Status\n" + 
					"				FROM\n" + 
					"				    tools tools\n" + 
					"				        INNER JOIN \n" + 
					"				    cdt_master_data.platform_master pm ON (pm.ID = tools.PlatformID)\n" + 
					"		            Left Join engines engines On (engines.ID = tools.EngineID) \n" + 
					"				WHERE\n" + 
					"				    tools.Status <> 'Deleted'\n" + 
					"				    AND (tools.ID like :searchText \n" + 
					"				        OR tools.Name LIKE :searchText\n" + 
					"						OR tools.Description like :searchText\n" + 
					"		                OR engines.Name like :searchText \n" + 
					"				        OR pm.Name LIKE :searchText\n" + 
					"				        OR tools.Status LIKE :searchText)",nativeQuery = true)
	Page<ToolsVO> getToolsListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from engines where Status <> 'Deleted' and Name= ?1 and PlatformID = ?2",nativeQuery = true)
	Integer findAlreadyExistToolsForAddMode(String name,Integer platformId);
	
	@Query(value="select ID,Name from tools where Status in('Active')",nativeQuery = true)
	List<ToolsVO> getToolsList();
	
	@Query(value="select count(*) as count from engines where Status <> 'Deleted' and ID <> ?1 and Name= ?2 and PlatformID = ?3",nativeQuery = true)
	Integer findAlreadyExistToolsForEditMode(Integer id, String name,Integer platformId);
	
	@Modifying
    @Transactional
	@Query(value="update tools set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeTools(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update tools set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveTools(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update tools set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteTools(Integer id);
	
}
