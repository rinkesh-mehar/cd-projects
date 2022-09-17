package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriActivity;

public interface AgriActivityRepository extends JpaRepository<AgriActivity, Integer> {

	@Query(value="select ID,Name,Status from agri_allied_activity\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_allied_activity\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriActivity> getActivityListByPagenation(Pageable sortedByIdDesc, String searchText);

	
}
