package in.cropdata.cdtmasterdata.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriAgroChemicalApplicationType;

public interface AgriAgroChemicalApplicationTypeRepository extends JpaRepository<AgriAgroChemicalApplicationType, Integer> {

	Optional<AgriAgroChemicalApplicationType> findByName(String name);
	
	@Query(value = "SELECT \n" + 
			"    ID, Name, Status\n" + 
			"FROM\n" + 
			"    agri_agrochemical_application_type\n" + 
			"WHERE\n" + 
			"    ID LIKE :searchText OR Name LIKE :searchText\n" + 
			"        OR Status LIKE :searchText", countQuery = "SELECT \n" + 
					"    ID, Name, Status\n" + 
					"FROM\n" + 
					"    agri_agrochemical_application_type\n" + 
					"WHERE\n" + 
					"    ID LIKE :searchText OR Name LIKE :searchText\n" + 
					"        OR Status LIKE :searchText", nativeQuery = true)
	Page<AgriAgroChemicalApplicationType> getPeginatedAgriAgroChemicalApplicationTypeList(Pageable sortedByIdDesc, String searchText);

}
