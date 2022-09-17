package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriSourceOfWater;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgriSourceOfWaterRepository extends JpaRepository<AgriSourceOfWater, Integer> {

    @Query(value = "SELECT SW.Name FROM cdt_master_data.agri_source_of_water SW WHERE SW.Name = ?1",nativeQuery = true)
    String foundByName(String name);

    Optional<AgriSourceOfWater> findByName(String name);
    
    @Query(value="select ID,Name,Status from agri_source_of_water\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_source_of_water\n" + 
    				"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriSourceOfWater> getSourceOfWaterListByPagenation(Pageable sortedByIdDesc, String searchText);
}
