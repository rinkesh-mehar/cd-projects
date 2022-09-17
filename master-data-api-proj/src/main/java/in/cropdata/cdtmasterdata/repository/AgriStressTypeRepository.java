package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriStressType;

public interface AgriStressTypeRepository extends JpaRepository<AgriStressType, Integer> {

	@Query(value = "SELECT ID,Name,Status FROM cdt_master_data.agri_stress_type\n" + 
			"where ID like :searchText\n" + 
			"OR Name  like :searchText\n" +
			"OR Status  like :searchText", countQuery = "SELECT ID,Name,Status FROM cdt_master_data.agri_stress_type\n" + 
					"where ID like :searchText\n" + 
					"OR Name  like :searchText\n" +
					"OR Status  like :searchText", nativeQuery = true)
	Page<AgriStressType> getPeginatedStressTypeList(Pageable sortedByIdDesc, String searchText);
	
}
