package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriIrrigationMethod;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgriIrrigationMethodRepository extends JpaRepository<AgriIrrigationMethod, Integer> {

    Optional<AgriIrrigationMethod> findByName(String name);
    
    @Query(value="select ID,Name,Status from agri_irrigation_method\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_irrigation_method\n" + 
    				"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriIrrigationMethod> getIrrigationMethodListByPagenation(Pageable sortedByIdDesc, String searchText);

}
