package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriStressSeverity;

public interface AgriStressSeverityRepositoy extends JpaRepository<AgriStressSeverity, Integer> {
	
	@Query(value = "SELECT ID,Name,Value,Status FROM agri_stress_severity\n" + 
			"where ID like :searchText OR Name like :searchText OR Value like :searchText\n" + 
			"", countQuery = "SELECT ID,Name,Value,Status FROM agri_stress_severity\n" + 
					"where ID like :searchText OR Name like :searchText OR Value like :searchText\n" + 
					"", nativeQuery = true)
	Page<AgriStressSeverity> getPeginatedStressSeverityList(Pageable sortedByIdDesc, String searchText);

}
