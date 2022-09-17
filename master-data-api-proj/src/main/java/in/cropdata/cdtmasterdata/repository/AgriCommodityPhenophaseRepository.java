package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityPhenophaseInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityPhenophase;
import in.cropdata.cdtmasterdata.model.vo.AgriPhenophaseVo;

public interface AgriCommodityPhenophaseRepository extends JpaRepository<AgriCommodityPhenophase, Integer> {

	@Query(value = "SELECT ACP.ID,ACP.CommodityID,ACP.PhenophaseID,ACP.CreatedAt,ACP.UpdatedAt,ACP.Status,AC.Name as Commodity,\n "
			+ "AP.Name as Phenophase from agri_commodity_phenophase ACP \n "
			+ "LEFT JOIN agri_commodity AC ON AC.ID = ACP.CommodityID \n "
			+ "LEFT JOIN agri_phenophase AP ON AP.ID = ACP.PhenophaseID", nativeQuery = true)
	List<AgriCommodityPhenophaseInfDto> getCommodityPhenophaseList();

	@Query(value = "SELECT ACP.ID,ACP.CommodityID,ACP.PhenophaseID,ACP.CreatedAt,ACP.UpdatedAt,ACP.Status,AC.Name as Commodity,\n "
			+ "AP.Name as Phenophase from agri_commodity_phenophase ACP \n "
			+ "LEFT JOIN agri_commodity AC ON AC.ID = ACP.CommodityID \n "
			+ "LEFT JOIN agri_phenophase AP ON AP.ID = ACP.PhenophaseID  "
			+ "where  AC.Name like :searchText  or  AP.Name like :searchText",
			countQuery = "SELECT count(ACP.ID) from agri_commodity_phenophase ACP \n"
					+ "LEFT JOIN agri_commodity AC ON AC.ID = ACP.CommodityID \n"
					+ "LEFT JOIN agri_phenophase AP ON AP.ID = ACP.PhenophaseID "
					+ "where  AC.Name like :searchText  or  AP.Name like :searchText", nativeQuery = true)
	Page<AgriCommodityPhenophaseInfDto> getCommodityPhenophaseList(Pageable pageable,String searchText);
	
	@Query(value = "SELECT ACP.ID,ACP.CommodityID,ACP.PhenophaseID,ACP.CreatedAt,ACP.UpdatedAt,ACP.Status,AC.Name as Commodity,\n "
			+ "AP.Name as Phenophase from agri_commodity_phenophase_missing ACP \n "
			+ "LEFT JOIN agri_commodity AC ON AC.ID = ACP.CommodityID \n "
			+ "LEFT JOIN agri_phenophase AP ON AP.ID = ACP.PhenophaseID  "
			+ "where  AC.Name like :searchText  or  AP.Name like :searchText",
			countQuery = "SELECT count(ACP.ID) from agri_commodity_phenophase_missing ACP \n"
					+ "LEFT JOIN agri_commodity AC ON AC.ID = ACP.CommodityID \n"
					+ "LEFT JOIN agri_phenophase AP ON AP.ID = ACP.PhenophaseID "
					+ "where  AC.Name like :searchText  or  AP.Name like :searchText", nativeQuery = true)
	Page<AgriCommodityPhenophaseInfDto> getCommodityPhenophaseListMissing(Pageable pageable,String searchText);

	@Query (value = "SELECT p.ID,p.Name FROM agri_commodity_phenophase cp\n" + 
			"Left Join agri_phenophase p ON cp.PhenophaseID = p.ID where cp.CommodityID=:commodityId", nativeQuery = true)
	List<AgriPhenophaseVo> findByCommodityId(int commodityId);
	
	AgriCommodityPhenophase findByCommodityIdAndPhenophaseId(int commodityId, int phenophaseId);
	
	
	

}
