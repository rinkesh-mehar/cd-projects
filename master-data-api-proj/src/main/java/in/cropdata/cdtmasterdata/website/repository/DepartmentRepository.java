package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.DepartmentDto;
import in.cropdata.cdtmasterdata.website.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
	@Query(value="select ID,UPPER(Name) as Name, Status from department where Status <> 'Deleted' and Name like :searchText",countQuery = "select ID,UPPER(Name) as Name, Status from department where Status <> 'Deleted' and Name like :searchText",nativeQuery = true)
	Page<DepartmentDto> getDepartmentListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update department set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveDepartment(Integer id);
	
	@Query(value="select ID,Name from department where Status <> 'Deleted' and Name= ?1",nativeQuery = true)
	DepartmentDto findAlreadyExistDepartmentForAddMode(String department);
	
	@Query(value="select ID,Name from department where Status in('Active') Order by Name",nativeQuery = true)
	List<DepartmentDto> getDepartmentList();
	
	@Query(value="select ID,Name from department where Status <> 'Deleted' and ID <> ?1 and Name= ?2",nativeQuery = true)
	DepartmentDto findAlreadyExistDepartmentForEditMode(Integer id, String department);
	
	@Modifying
    @Transactional
	@Query(value="update department set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeDepartment(Integer id);
	
	@Query(value="select count(*) as opportunityCount from opportunities opportunities Inner join department department on (opportunities.DepartmentID = department.ID)\n" + 
			"where opportunities.Status = 'Active' and opportunities.DepartmentID = ?1",nativeQuery = true)
	Integer opportunityCountByDepartment(Integer departmentId);
	
	@Modifying
    @Transactional
	@Query(value="update department set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteDepartment(Integer id);

}
