package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriSeedSource;

import java.util.Optional;

public interface AgriSeedSourceRepository extends JpaRepository<AgriSeedSource, Integer> {

    Optional<AgriSeedSource> findByName(String name);
    

	@Query(value="select ID,Name,Status from agri_seed_source\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_seed_source\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriSeedSource> getSeedSourceListByPagenation(Pageable sortedByIdDesc, String searchText);
}
