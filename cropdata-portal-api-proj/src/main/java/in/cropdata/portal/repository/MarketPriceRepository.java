package in.cropdata.portal.repository;

import in.cropdata.portal.model.MarketPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Integer> {

	@Query(value = "SELECT TMP.ID, TMP.Status, TMP.CommodityID,AC.Name as Commodity,VarietyID,\n"
			+ "AV.Name as Variety,MarketID,AM.Name as Market,MinPrice,MaxPrice,ModalPrice \n"
			+ "FROM cdt_website.ticker_market_price TMP \n"
			+ "INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n"
			+ "INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n"
			+ "INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)", nativeQuery = true)
	List<Map<String, Object>> getMarketPriceList();

	@Query(value = "SELECT TMP.ID,TMP.CommodityID,AC.Name as Commodity,VarietyID,\n"
			+ "			AV.Name as Variety,MarketID,AM.Name as Market,MinPrice,MaxPrice,ModalPrice \n"
			+ "			FROM cdt_website.ticker_market_price TMP \n"
			+ "			INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n"
			+ "			INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n"
			+ "			INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)", countQuery = "SELECT TMP.ID,TMP.CommodityID,AC.Name as Commodity,VarietyID,\n"
					+ "			AV.Name as Variety,MarketID,AM.Name as Market,MinPrice,MaxPrice,ModalPrice \n"
					+ "			FROM cdt_website.ticker_market_price TMP \n"
					+ "			INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n"
					+ "			INNER JOIN cdt_master_data.agri_variety AV ON (TMP.VarietyID = AV.ID)\n"
					+ "			INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)", nativeQuery = true)
	Page<Map<String, Object>> getMarketPriceListPaginated(Pageable sortedByIdDesc, String searchText);

	@Query(value = "select count(*) from cdt_website.ticker_market_price where CommodityID=?1 and MarketID=?2 and VarietyID =?3 ", nativeQuery = true)
	int checkExistMarketPrice(Integer commodityId, Integer marketId, Integer varietyId);

	@Query(value = "SELECT AC.Name as Commodity,\n"
			+ "            AV.VarietyName as Variety,AM.Name as Market, MinPrice\n"
			+ "            , MaxPrice, ModalPrice FROM gstm_data_models.pvi_winner_markets TMP \n"
			+ "            INNER JOIN cdt_master_data.agri_commodity AC ON (TMP.CommodityID = AC.ID)\n"
			+ "            INNER JOIN cdt_master_data.pricing_agri_variety AV ON (TMP.PricingAgriVarietyID = AV.ID)\n"
			+ "            INNER JOIN cdt_master_data.agri_market AM ON (TMP.MarketID = AM.ID)\n"
			+ "            where TMP.ArrivalDate >= ?1", nativeQuery = true)
	List<Map<String, Object>> getMarketPriceListDateWise(LocalDate date);

}
