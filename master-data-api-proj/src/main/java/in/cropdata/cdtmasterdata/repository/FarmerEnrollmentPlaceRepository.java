package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.FarmerEnrollmentPlace;

public interface FarmerEnrollmentPlaceRepository extends JpaRepository<FarmerEnrollmentPlace, Integer> {
	
	@Query(value="select ID,Name,Status from farmer_enrollment_place\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from farmer_enrollment_place\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<FarmerEnrollmentPlace> getFarmarEnrollmentPlaceListByPagenation(Pageable sortedByIdDesc, String searchText);

}
