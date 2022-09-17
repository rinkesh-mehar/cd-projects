package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriHealthParameter;

public interface AgriHealthParameterRepository extends JpaRepository<AgriHealthParameter, Integer> {
	
	@Query(value="select ID,Name,Status from agri_health_parameter\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select count(ID) as Count from agri_health_parameter\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriHealthParameter> getHealthParameterListByPagenation(Pageable sortedByIdDesc, String searchText);

}
