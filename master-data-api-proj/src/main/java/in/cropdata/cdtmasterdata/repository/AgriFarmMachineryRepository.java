package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriFarmMachinery;

public interface AgriFarmMachineryRepository extends JpaRepository<AgriFarmMachinery, Integer> {
	
	@Query(value="select ID,Name,Status from agri_farm_machinery\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_farm_machinery\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriFarmMachinery> getFarmMachineryListByPagenation(Pageable sortedByIdDesc, String searchText);

}
