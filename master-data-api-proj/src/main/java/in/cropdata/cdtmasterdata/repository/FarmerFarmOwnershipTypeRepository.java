package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.FarmerFarmOwnershipType;

public interface FarmerFarmOwnershipTypeRepository extends JpaRepository<FarmerFarmOwnershipType, Integer> {
	
	@Query(value="select ID,Name,Status from farmer_farm_ownership_type\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from farmer_farm_ownership_type\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<FarmerFarmOwnershipType> getFarmerFarmOwnershipTypeListByPagenation(Pageable sortedByIdDesc, String searchText);



}
