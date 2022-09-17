package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriPlantPartInfo;
import in.cropdata.cdtmasterdata.model.AgriPlantPart;

public interface AgriPlantPartRepository extends JpaRepository<AgriPlantPart, Integer> {

	@Query(value = "SELECT APP.ID,APP.Name,APP.Status FROM agri_plant_part APP\n" + 
			"where APP.Name like :searchText",
			countQuery = "SELECT APP.ID,APP.Name,APP.CreatedAt,APP.UpdatedAt,APP.Status FROM agri_plant_part APP\n" + 
					"where APP.Name like :searchText", nativeQuery = true)
	Page<AgriPlantPartInfo> findAllWithSearch(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from agri_plant_part where  Name= ?1",nativeQuery = true)
	Integer findAlreadyExistPlantPartForAddMode(String name);
	
	@Query(value="select count(*) as count from agri_plant_part where  ID <> ?1 and Name= ?2",nativeQuery = true)
	Integer findAlreadyExistPlantPartForEditMode(Integer id, String name);

}
