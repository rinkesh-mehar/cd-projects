package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.PositionDto;
import in.cropdata.cdtmasterdata.website.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query(value="select ID,UPPER(Name) as Name, Status from position where Status <> 'Deleted' and Name like :searchText",countQuery = "select ID,Name, Status from position where Status <> 'Deleted' and Name like :searchText",nativeQuery = true)
	Page<PositionDto> getPositionListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update position set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactivePosition(Integer id);
	
	@Query(value="select ID,Name from position where Status <> 'Deleted' and Name= ?1",nativeQuery = true)
	PositionDto findAlreadyExistPositionForAddMode(String position);
	
	@Query(value="select ID,Name from position where Status in('Active') order by Name",nativeQuery = true)
	List<PositionDto> getPositionList();
	
	@Query(value="select ID,Name from position where Status <> 'Deleted' and ID <> ?1 and Name= ?2",nativeQuery = true)
	PositionDto findAlreadyExistPositionForEditMode(Integer id, String position);
	
	@Modifying
    @Transactional
	@Query(value="update position set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activePosition(Integer id);
	
	@Query(value="select count(*) as opportunityCount from opportunities opportunities Inner join position position on (opportunities.PositionID = position.ID)\n" + 
			"where opportunities.Status = 'Active' and opportunities.PositionID = ?1",nativeQuery = true)
	Integer opportunityCountByPosition(Integer positionId);
	
	@Modifying
    @Transactional
	@Query(value="update position set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deletePosition(Integer id);
	
}
