package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriStateCommodity;
import in.cropdata.cdtmasterdata.model.vo.AgriStateCommodityVO;

@Repository
public interface AgriStateCommodityRepository extends JpaRepository<AgriStateCommodity, Integer> {
	
	
	@Query(value = "select agri_state_commodity.ID,gs.Name as State,agri_commodity.Name as Commodity,agri_state_commodity.Status from agri_state_commodity agri_state_commodity \n" + 
			"			inner join geo_state gs on (gs.ID = agri_state_commodity.StateCode) \n" + 
			"			inner join agri_commodity agri_commodity on (agri_commodity.ID = agri_state_commodity.CommodityID)\n" + 
			"			where agri_state_commodity.ID like :searchText OR gs.Name like :searchText OR agri_commodity.Name like :searchText\n" + 
			"            OR agri_state_commodity.Status like :searchText", 
			countQuery = "select agri_state_commodity.ID,gs.Name as State,agri_commodity.Name as Commodity,agri_state_commodity.Status from agri_state_commodity agri_state_commodity \n" + 
					"			inner join geo_state gs on (gs.ID = agri_state_commodity.StateCode) \n" + 
					"			inner join agri_commodity agri_commodity on (agri_commodity.ID = agri_state_commodity.CommodityID)\n" + 
					"			where agri_state_commodity.ID like :searchText OR gs.Name like :searchText OR agri_commodity.Name like :searchText\n" + 
					"            OR agri_state_commodity.Status like :searchText", nativeQuery = true)
	Page<AgriStateCommodityVO> getAgriStateCommodityPagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_state_commodity set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveAgriStateCommodity(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_state_commodity set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeAgriStateCommodity(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_state_commodity set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectAgriStateCommodity(Integer id);
	
	@Query(value="select count(*) as count from agri_state_commodity where  StateCode= ?1 and CommodityID=?2",nativeQuery = true)
	Integer findAlreadyExistStateCommodityForAddMode(Integer stateCode,Integer commodityId);
	
	@Query(value="select count(*) as count from agri_state_commodity where  ID <> ?1 and StateCode= ?2 and CommodityID=?3",nativeQuery = true)
	Integer findAlreadyExistStateCommodityForEditMode(Integer id,Integer stateCode,Integer commodityId);


}
