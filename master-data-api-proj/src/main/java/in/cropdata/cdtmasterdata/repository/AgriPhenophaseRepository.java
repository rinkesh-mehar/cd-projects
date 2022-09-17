package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriPhenophase;

public interface AgriPhenophaseRepository extends JpaRepository<AgriPhenophase, Integer> {

	Page<AgriPhenophase> findAllByNameIgnoreCaseContaining(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select ID,Name,Status from agri_phenophase\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select count(ID) as Count from agri_phenophase\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriPhenophase> getPhenophaseListByPagenation(Pageable sortedByIdDesc, String searchText);

}
