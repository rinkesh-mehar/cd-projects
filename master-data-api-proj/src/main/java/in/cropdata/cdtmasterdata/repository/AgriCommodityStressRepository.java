package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriCommodityStress;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityStressVO;

public interface AgriCommodityStressRepository extends JpaRepository<AgriCommodityStress, Integer> {

	@Query(value = "select acs.ID,ac.Name as Commodity,agri_stress.Name as Stress,acs.Status from agri_commodity_stress acs\n" + 
			"Inner Join agri_commodity ac On (acs.CommodityID = ac.ID)\n" + 
			"Inner Join agri_stress agri_stress On (acs.StressID = agri_stress.ID)\n" + 
			"where acs.ID like :searchText\n" + 
			"OR ac.Name like :searchText\n" + 
			"OR agri_stress.Name like :searchText\n" + 
			"OR acs.Status like :searchText",countQuery = "select acs.ID,ac.Name as Commodity,agri_stress.Name as Stress,acs.Status from agri_commodity_stress acs\n" + 
					"Inner Join agri_commodity ac On (acs.CommodityID = ac.ID)\n" + 
					"Inner Join agri_stress agri_stress On (acs.StressID = agri_stress.ID)\n" + 
					"where acs.ID like :searchText\n" + 
					"OR ac.Name like :searchText\n" + 
					"OR agri_stress.Name like :searchText\n" + 
					"OR acs.Status like :searchText",nativeQuery = true)
	Page<AgriCommodityStressVO> getCommodityStressPagenatedLisy(Pageable sortedByIdDesc,String searchText);
	
	@Modifying
	@Transactional
	@Query(value="update agri_commodity_stress set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveCommodityStress(Integer Id); 
	
	@Modifying
	@Transactional
	@Query(value="update agri_commodity_stress set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeCommodityStress(Integer Id); 
	
	@Modifying
	@Transactional
	@Query(value="update agri_commodity_stress set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectCommodityStress(Integer Id); 
	
}
