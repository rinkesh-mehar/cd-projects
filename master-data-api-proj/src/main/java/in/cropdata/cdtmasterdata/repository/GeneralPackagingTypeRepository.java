package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.GeneralPackagingType;
import in.cropdata.cdtmasterdata.model.vo.GeneralPackagingTypeVo;

@Repository
public interface GeneralPackagingTypeRepository extends JpaRepository<GeneralPackagingType, Integer> {
	
	
	@Query(value = "select aqcp.ID,aqcp.Name,aqcp.Status from general_packaging_type aqcp\n" + 
			"where aqcp.ID like :searchText OR aqcp.Name like :searchText\n" + 
			"OR aqcp.Status like :searchText", 
			countQuery = "select aqcp.ID,aqcp.Name,aqcp.Status from general_packaging_type aqcp\n" + 
					"where aqcp.ID like :searchText OR aqcp.Name like :searchText\n" + 
					"OR aqcp.Status like :searchText", nativeQuery = true)
	Page<GeneralPackagingTypeVo> getGeneralPackagingTypePagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update general_packaging_type set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveGeneralPackagingType(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update general_packaging_type set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeGeneralPackagingType(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update general_packaging_type set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectGeneralPackagingType(Integer id);


}
