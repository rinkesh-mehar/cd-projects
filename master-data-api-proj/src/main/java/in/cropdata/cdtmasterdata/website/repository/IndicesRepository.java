package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.Indices;
import in.cropdata.cdtmasterdata.website.model.vo.IndicesVO;

@Repository
public interface IndicesRepository extends JpaRepository<Indices, Integer> {

	@Query(value="SELECT\n" + 
			"		    indices.ID, \n" + 
			"		    UPPER(indices.Name) AS Name,\n" + 
			"            UPPER(indices.Description) as Description,\n" + 
			"		    indices.Status,\n" + 
			"		    UPPER(pm.Name) As Platform \n" + 
			"		FROM\n" + 
			"		    indices indices\n" + 
			"		        left JOIN\n" + 
			"		    cdt_master_data.platform_master pm ON (pm.ID = indices.PlatformID)\n" + 
			"            WHERE \n" + 
			"			    indices.Status <> 'Deleted'\n" + 
			"			    AND (indices.ID like :searchText\n" + 
			"			        OR indices.Name LIKE :searchText\n" + 
			"                    OR indices.Description LIKE :searchText\n" + 
			"			        OR indices.Status LIKE :searchText\n" + 
			"			        OR pm.Name LIKE :searchText)",countQuery = "SELECT\n" + 
					"		    indices.ID, \n" + 
					"		    UPPER(indices.Name) AS Name,\n" + 
					"            UPPER(indices.Description) as Description,\n" + 
					"		    indices.Status,\n" + 
					"		    UPPER(pm.Name) As Platform \n" + 
					"		FROM\n" + 
					"		    indices indices\n" + 
					"		        left JOIN\n" + 
					"		    cdt_master_data.platform_master pm ON (pm.ID = indices.PlatformID)\n" + 
					"            WHERE \n" + 
					"			    indices.Status <> 'Deleted'\n" + 
					"			    AND (indices.ID like :searchText\n" + 
					"			        OR indices.Name LIKE :searchText\n" + 
					"                    OR indices.Description LIKE :searchText\n" + 
					"			        OR indices.Status LIKE :searchText\n" + 
					"			        OR pm.Name LIKE :searchText)",nativeQuery = true)
	Page<IndicesVO> getIndicesListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from indices where Status <> 'Deleted' and Name= ?1 and PlatformID=?2",nativeQuery = true)
	Integer findAlreadyExistIndicesForAddMode(String name,Integer platformId);
	
	@Query(value="select ID,Name from indices where Status in('Active')",nativeQuery = true)
	List<IndicesVO> getIndicesList();
	
	@Query(value="select count(*) as count from indices where Status <> 'Deleted' and ID <> ?1 and Name= ?2 and PlatformID=?3",nativeQuery = true)
	Integer findAlreadyExistIndicesForEditMode(Integer id, String name,Integer platformId);
	
	@Modifying
    @Transactional
	@Query(value="update indices set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeIndices(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update indices set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveIndices(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update indices set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteIndices(Integer id);
	
}
