package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriStress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgriStressRepository extends JpaRepository<AgriStress, Integer> {
	
	@Query(value = "select ID, Name, CreatedAt, UpdatedAt, Status, IsValid from agri_stress ass where ass.ID = ?1", nativeQuery = true)
	AgriStress findAgriStressById(Integer id);

	@Query(value = "SELECT \n" + 
			"    agri_stress.id,\n" + 
			"    agri_stress.name,\n" + 
			"    agri_stress.status,\n" + 
			"    agri_stress.isvalid,\n" + 
			"    ast.Name AS StressType\n" + 
			"FROM\n" + 
			"    cdt_master_data.agri_stress agri_stress\n" + 
			"        INNER JOIN\n" + 
			"    agri_stress_type ast ON (agri_stress.StressTypeID = ast.ID)\n" + 
			"WHERE\n" + 
			"    agri_stress.id LIKE :searchText\n" + 
			"        OR agri_stress.Name LIKE :searchText\n" + 
			"        OR agri_stress.Status LIKE :searchText\n" + 
			"        OR ast.Name LIKE :searchText",
			countQuery = "SELECT \n" + 
					"    agri_stress.id,\n" + 
					"    agri_stress.name,\n" + 
					"    agri_stress.status,\n" + 
					"    agri_stress.isvalid,\n" + 
					"    ast.Name AS StressType\n" + 
					"FROM\n" + 
					"    cdt_master_data.agri_stress agri_stress\n" + 
					"        INNER JOIN\n" + 
					"    agri_stress_type ast ON (agri_stress.StressTypeID = ast.ID)\n" + 
					"WHERE\n" + 
					"    agri_stress.id LIKE :searchText\n" + 
					"        OR agri_stress.Name LIKE :searchText\n" + 
					"        OR agri_stress.Status LIKE :searchText\n" + 
					"        OR ast.Name LIKE :searchText", nativeQuery = true)
	Page<AgriDistrictCommodityStressInfDto> getStressList(Pageable pageable,String searchText);

	@Query(value = "select ID,CommodityID,StressTypeID,StartPhenophaseID,EndPhenophaseID,Name,ScientificName,ImageID,FileUrl  as ImageUrl,"
			+ "UpdatedAt,CreatedAt,status from agri_commodity_stress where CommodityID=?1 and StressTypeID=?2 and status='Active'", nativeQuery = true)
	List<AgriDistrictCommodityStressInfDto> findAllByStressTypeId(int commodityID, int stressTypeId);

	@Query(value = "select ID from agri_stress where Name = ?1", nativeQuery = true)
	Integer findIDByName(String name);
}