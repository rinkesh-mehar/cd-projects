package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.EducationDto;
import in.cropdata.cdtmasterdata.website.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
	
	@Query(value="select ID,UPPER(Name) as Name, Status from education where Status <> 'Deleted' and Name like :searchText",countQuery = "select ID,Name, Status from education where Status <> 'Deleted' and Name like :searchText",nativeQuery = true)
	Page<EducationDto> getEducationListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update education set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveEducation(Integer id);
	
	@Query(value="select ID,Name from education where Status <> 'Deleted' and Name= ?1",nativeQuery = true)
	EducationDto findAlreadyExistEducationForAddMode(String education);

	@Query(value="select ID,Name from education where Status in('Active') Order by Name",nativeQuery = true)
	List<EducationDto> getEducationList();
	
	@Query(value="select ID,Name from education where Status <> 'Deleted' and ID <> ?1 and Name= ?2",nativeQuery = true)
	EducationDto findAlreadyExistEducationForEditMode(Integer id, String education);
	
	@Modifying
    @Transactional
	@Query(value="update education set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeEducation(Integer id);
	
	@Query(value="select count(*) as opportunityCount from opportunities opportunities Inner join opportunities_education opportunities_education on (opportunities.ID = opportunities_education.OpportunityID)\n" + 
			"Inner join education education on (opportunities_education.EducationID = education.ID)\n" + 
			"where opportunities.Status = 'Active' and opportunities_education.EducationID = ?",nativeQuery = true)
	Integer opportunityCountByEducation(Integer departmentId);
	
	@Modifying
    @Transactional
	@Query(value="update education set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteEducation(Integer id);
}
