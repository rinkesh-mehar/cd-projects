package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.farmers.app.masters.model.IrrigationSource;
import in.cropdata.farmers.app.masters.model.State;
import in.cropdata.farmers.app.masters.model.Variety;

public interface IrrigationSourceRepository extends JpaRepository<IrrigationSource, Integer> {
	
	
	
	@Query(value = " select aw.ID as id , aw.Name as name from cdt_master_data.agri_source_of_water aw ", nativeQuery = true)
	List<IrrigationSource> getAllIrrigationSource();

}
