package in.cropdata.cdtmasterdata.website.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.website.model.MarketPrice;

import javax.transaction.Transactional;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Integer> {

	@Query(value = "SELECT TMP.ID, TMP.Status, TMP.CommodityID,AC.Name as Commodity,VarietyID,\n"
			+ "AV.Name as Variety,MarketID,AM.Name as Market,MinPrice,MaxPrice,ModalPrice \n"
			+ "FROM cdt_website.ticker_market_price TMP \n"
			+ "INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n"
			+ "INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n"
			+ "INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)", nativeQuery = true)
	List<Map<String, Object>> getMarketPriceList();

	@Query(value = "SELECT TMP.ID, TMP.Status, TMP.CommodityID,AC.Name as Commodity,VarietyID,\n" + 
			"			AV.Name as Variety,MarketID,AM.Name as Market,MinPrice,MaxPrice,ModalPrice\n" + 
			"			FROM cdt_website.ticker_market_price TMP\n" + 
			"			INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n" + 
			"			INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n" + 
			"			INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)\n" + 
			"            where TMP.ID like :searchText OR TMP.Status like :searchText OR TMP.CommodityID like :searchText OR AC.Name like :searchText OR VarietyID like :searchText\n" + 
			"            OR AV.Name like :searchText OR MarketID like :searchText OR AM.Name like :searchText OR MinPrice like :searchText OR MaxPrice like :searchText\n" + 
			"            OR ModalPrice like :searchText\n" + 
			"            order by TMP.ID desc", countQuery = "SELECT count(TMP.ID) as Count \n" + 
					"			FROM cdt_website.ticker_market_price TMP\n" + 
					"			INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n" + 
					"			INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n" + 
					"			INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)\n" + 
					"            where TMP.ID like :searchText OR TMP.Status like :searchText OR TMP.CommodityID like :searchText OR AC.Name like :searchText OR VarietyID like :searchText\n" + 
					"            OR AV.Name like :searchText OR MarketID like :searchText OR AM.Name like :searchText OR MinPrice like :searchText OR MaxPrice like :searchText\n" + 
					"            OR ModalPrice like :searchText\n" + 
					"            order by TMP.ID desc", nativeQuery = true)
	Page<Map<String, Object>> getMarketPriceListPaginated(Pageable sortedByIdDesc, String searchText);

	@Query(value = "select count(*) from cdt_website.ticker_market_price where CommodityID=?1 and MarketID=?2 and VarietyID =?3 ", nativeQuery = true)
	int checkExistMarketPrice(Integer commodityId, Integer marketId, Integer varietyId);

	@Query(value = "SELECT AC.Name as Commodity,\n" + 
			"group_concat(AV.Name) as Variety,group_concat(AM.Name) as Market,group_concat(MinPrice) as MinPrice\n" + 
			",group_concat(MaxPrice) as MaxPrice,group_concat(ModalPrice) as ModalPrice\n" + 
			"FROM cdt_website.ticker_market_price TMP \n" + 
			"INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n" + 
			"INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n" + 
			"INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)\n" + 
			"where TMP.CreatedAt >= ?1\n" + 
			"group by Commodity", nativeQuery = true)
	List<Map<String, Object>> getMarketPriceListDateWise(LocalDate date);


	@Modifying
	@Transactional
	@Query(value = "update cdt_website.ticker_market_price TMP SET TMP.status = 'Deleted' WHERE TMP.ID = ?1", nativeQuery = true)
	int deleteMarketPrice(Integer marketPriceId);
}
