package in.cropdata.portal.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.AgriComodity;
import in.cropdata.portal.vo.AgriCommodityVo;

@Repository
public interface AgriCommodityRepository extends JpaRepository<AgriComodity, Integer>{

	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agri_commodity where Status = 'Active' order by Name", nativeQuery = true)
	List<AgriCommodityVo> getActiveCommodityList();

	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agri_commodity where Status = 'Active' order by Name", nativeQuery = true)
	List<AgriCommodityVo> getCommodityList();

	@Query(value = "SELECT LOWER(Name) as name,ID as id FROM cdt_master_data.agri_commodity where ID in(?1);", nativeQuery = true)
	List<Map<String,Object>> getCommodityNameList(List<Integer> ids);


	
}
