package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.Vas;
import in.cropdata.cdtmasterdata.website.model.vo.VasVO;

@Repository
public interface VasRepository extends JpaRepository<Vas, Integer> {

	@Query(value="SELECT\n" + 
			"						    vas.ID, \n" + 
			"						    UPPER(vas.Name) AS Name,\n" + 
			"				            UPPER(vas.Description) as Description,\n" + 
			"		                    vas.Logo,\n" + 
			"						    vas.Status\n" + 
			"						FROM\n" + 
			"						    vas vas\n" + 
			"						WHERE\n" + 
			"						    vas.Status <> 'Deleted'\n" + 
			"						    AND (vas.ID like :searchText\n" + 
			"						        OR vas.Name LIKE :searchText\n" + 
			"								OR vas.Description like :searchText\n" + 
			"						        OR vas.Status LIKE :searchText)",countQuery = "SELECT\n" + 
					"						    vas.ID, \n" + 
					"						    UPPER(vas.Name) AS Name,\n" + 
					"				            UPPER(vas.Description) as Description,\n" + 
					"		                    vas.Logo,\n" + 
					"						    vas.Status\n" + 
					"						FROM\n" + 
					"						    vas vas\n" + 
					"						WHERE\n" + 
					"						    vas.Status <> 'Deleted'\n" + 
					"						    AND (vas.ID like :searchText\n" + 
					"						        OR vas.Name LIKE :searchText\n" + 
					"								OR vas.Description like :searchText\n" + 
					"						        OR vas.Status LIKE :searchText)",nativeQuery = true)
	Page<VasVO> getVasListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from vas where Status <> 'Deleted' and Name= ?1",nativeQuery = true)
	Integer findAlreadyExistVasForAddMode(String name);
	
	@Query(value="select ID,Name from vas where Status in('Active')",nativeQuery = true)
	List<VasVO> getVasList();
	
	@Query(value="select count(*) as count from vas where Status <> 'Deleted' and ID <> ?1 and Name= ?2",nativeQuery = true)
	Integer findAlreadyExistVasForEditMode(Integer id, String name);
	
	@Modifying
    @Transactional
	@Query(value="update vas set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeVas(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update vas set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveVas(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update vas set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteVas(Integer id);
	
}
