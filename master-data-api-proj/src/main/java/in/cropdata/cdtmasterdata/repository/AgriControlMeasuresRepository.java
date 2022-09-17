package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriControlMeasures;
import in.cropdata.cdtmasterdata.model.GeneralUom;

public interface AgriControlMeasuresRepository extends JpaRepository<AgriControlMeasures, Integer> {
	
	@Query(value = "select ID,Name,Status from agri_control_measures\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText", countQuery = "select ID,Name,Status from agri_control_measures\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText", nativeQuery = true)
	Page<AgriControlMeasures> getPeginatedControlMeasuresList(Pageable sortedByIdDesc, String searchText);

}
