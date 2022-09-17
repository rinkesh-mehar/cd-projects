package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriAgroChemicalType;

import java.util.Optional;

public interface AgriAgroChemicalTypeRepository extends JpaRepository<AgriAgroChemicalType, Integer> {

    Optional<AgriAgroChemicalType> findByName(String name);
    
    @Query(value="select ID,Name,Status from agri_agrochemical_type\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_agrochemical_type\n" + 
    				"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriAgroChemicalType> getAgroChemicalTypeListByPagenation(Pageable sortedByIdDesc, String searchText);
}
