package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.GeneralPOIType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralPOITypeRepository extends JpaRepository<GeneralPOIType, Integer>
{
    Optional<GeneralPOIType> findByName(final String POIName);
    
    @Query(value="select ID,Name,Status from general_poi_type\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from general_poi_type\n" + 
    		"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<GeneralPOIType> getGeneralPOITypeListByPagenation(Pageable sortedByIdDesc, String searchText);
}
