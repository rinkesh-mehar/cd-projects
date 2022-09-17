package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.model.vo.PlatformMasterVO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 25/08/20
 * @time 10:01 AM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface PlatFormRepository extends JpaRepository<PlatFormMaster, Integer>
{
	@Query(value="SELECT\n" + 
			"						    pm.ID,\n" + 
			"						    UPPER(pm.Name) AS Name, \n" + 
			"				            UPPER(pm.Description) as Description,\n" + 
			"						    pm.ViewOrder,\n" + 
			"		                    pm.Logo,\n" + 
			"						    pm.Status\n" + 
			"						FROM\n" + 
			"						    cdt_master_data.platform_master pm\n" + 
			"						WHERE\n" + 
			"						    pm.Status <> 'Deleted'\n" + 
			"						    AND (pm.ID like :searchText\n" + 
			"						        OR pm.Name LIKE :searchText\n" + 
			"								OR pm.Description like :searchText\n" + 
			"						        OR pm.ViewOrder LIKE :searchText\n" + 
			"						        OR pm.Status LIKE :searchText)",countQuery = "SELECT\n" + 
					"						    pm.ID,\n" + 
					"						    UPPER(pm.Name) AS Name, \n" + 
					"				            UPPER(pm.Description) as Description,\n" + 
					"						    pm.ViewOrder,\n" + 
					"		                    pm.Logo,\n" + 
					"						    pm.Status\n" + 
					"						FROM\n" + 
					"						    cdt_master_data.platform_master pm\n" + 
					"						WHERE\n" + 
					"						    pm.Status <> 'Deleted'\n" + 
					"						    AND (pm.ID like :searchText\n" + 
					"						        OR pm.Name LIKE :searchText\n" + 
					"								OR pm.Description like :searchText\n" + 
					"						        OR pm.ViewOrder LIKE :searchText\n" + 
					"						        OR pm.Status LIKE :searchText)",nativeQuery = true)
	Page<PlatformMasterVO> getPlatformMasterListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from cdt_master_data.platform_master where Status <> 'Deleted' and Name= ?1 and PlatformID = ?2",nativeQuery = true)
	Integer findAlreadyExistPlatformMasterForAddMode(String name,Integer platformId);
	
	@Query(value="select ID,Name from cdt_master_data.platform_master where Status in('Active') order by Name",nativeQuery = true)
	List<PlatformMasterVO> getPlatformMasterList();
	
	@Query(value="select count(*) as count from cdt_master_data.platform_master where Status <> 'Deleted' and ID <> ?1 and Name= ?2 and PlatformID = ?3",nativeQuery = true)
	Integer findAlreadyExistPlatformMasterForEditMode(Integer id, String name,Integer platformId);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_master_data.platform_master set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activePlatformMaster(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_master_data.platform_master set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactivePlatformMaster(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update cdt_master_data.platform_master set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deletePlatformMaster(Integer id);
}
