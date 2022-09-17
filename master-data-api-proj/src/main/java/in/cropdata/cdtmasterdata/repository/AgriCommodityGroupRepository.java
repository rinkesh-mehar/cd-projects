package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriCommodityGroup;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityGroupVo;

@Repository
public interface AgriCommodityGroupRepository extends JpaRepository<AgriCommodityGroup, Integer> {
	
	
	@Query(value = "select CG.ID,CG.Name,CG.Status,CG.ViewOrder from agri_commodity_group CG\n" + 
			"where CG.ID like :searchText OR CG.Name like :searchText\n" + 
			"OR CG.Status like :searchText OR CG.ViewOrder like :searchText", 
			countQuery = "select CG.ID,CG.Name,CG.Status,CG.ViewOrder from agri_commodity_group CG\n" + 
					"where CG.ID like :searchText OR CG.Name like :searchText\n" + 
					"OR CG.Status like :searchText OR CG.ViewOrder like :searchText", nativeQuery = true)
	Page<AgriCommodityGroupVo> getCommodityGroupPagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_commodity_group set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveCommodityGroup(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_commodity_group set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeCommodityGroup(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_commodity_group set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectCommodityGroup(Integer id);


}
