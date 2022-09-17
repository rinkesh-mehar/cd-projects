package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriQualityCommodityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityCommodityParameterVO;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;

@Repository
public interface AgriQualityCommodityParameterRepository extends JpaRepository<AgriQualityCommodityParameter, Integer> {
	
	
	@Query(value = "SELECT\n" + 
			"    aqcp.CommodityID,\n" + 
			"    ac.Name AS Commodity,\n" + 
			"    REPLACE(GROUP_CONCAT(aqp.Name),',',', ') AS QualityParameter,\n" + 
			"    aqcp.Status\n" + 
			"FROM\n" + 
			"    agri_quality_commodity_parameter aqcp\n" + 
			"        INNER JOIN\n" + 
			"    agri_commodity ac ON (aqcp.CommodityID = ac.ID)\n" + 
			"        INNER JOIN\n" + 
			"    agri_quality_parameter aqp ON (aqcp.ParameterID = aqp.ID)\n" + 
			"WHERE\n" + 
			"    aqcp.CommodityID LIKE :searchText\n" + 
			"        OR ac.Name LIKE :searchText\n" + 
			"        OR aqcp.Status LIKE :searchText\n" + 
			"GROUP BY aqcp.CommodityID , ac.Name , aqcp.Status", 
			countQuery = "SELECT\n" + 
					"    aqcp.CommodityID,\n" + 
					"    ac.Name AS Commodity,\n" + 
					"    REPLACE(GROUP_CONCAT(aqp.Name),',',', ') AS QualityParameter,\n" + 
					"    aqcp.Status\n" + 
					"FROM\n" + 
					"    agri_quality_commodity_parameter aqcp\n" + 
					"        INNER JOIN\n" + 
					"    agri_commodity ac ON (aqcp.CommodityID = ac.ID)\n" + 
					"        INNER JOIN\n" + 
					"    agri_quality_parameter aqp ON (aqcp.ParameterID = aqp.ID)\n" + 
					"WHERE\n" + 
					"    aqcp.CommodityID LIKE :searchText\n" + 
					"        OR ac.Name LIKE :searchText\n" + 
					"        OR aqcp.Status LIKE :searchText\n" + 
					"GROUP BY aqcp.CommodityID , ac.Name , aqcp.Status", nativeQuery = true)
	Page<AgriQualityCommodityParameterVO> getQualityCommodityParameterPagenatedList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="SELECT aqp.ID,aqp.Name FROM agri_quality_commodity_parameter aqcp\n" + 
			"Inner Join agri_quality_parameter aqp on (aqcp.ParameterID = aqp.ID)\n" + 
			"where aqcp.CommodityID = ?1",nativeQuery = true)
	List<AgriQualityParameterVo> getParameterListByCommodityId(Integer commodityId);
	
	@Query(value = "SELECT ParameterID FROM agri_quality_commodity_parameter where CommodityID = ?1", nativeQuery = true)
	Integer[] findParameterIdsByCommodityId(Integer commodityId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from agri_quality_commodity_parameter where CommodityID = ?1 and ParameterID = ?2", nativeQuery = true)
	void deleteByCommodityIdAndParameterId(Integer commodityId, Integer parameterId);
	
	@Query(value = "select * from agri_quality_commodity_parameter where CommodityID = ?1 and ParameterID = ?2", nativeQuery = true)
	AgriQualityCommodityParameter findByCommodityIDAndParameterID(Integer commodityId, Integer parameterId);
	
}
