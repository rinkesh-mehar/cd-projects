package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.GeneralTypeOfService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralTypeOfServiceRepository extends JpaRepository<GeneralTypeOfService, Integer>
{
    Optional<GeneralTypeOfService> findByTypeOfService(final String typeOfService);
    
    @Query(value="select ID,Name,Status from general_type_of_service\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from general_type_of_service\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<GeneralTypeOfService> getGeneralTypeOfServiceListByPagenation(Pageable sortedByIdDesc, String searchText);
}
