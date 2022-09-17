package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriMspMbep;

public interface AgriMspMbepRepository extends JpaRepository<AgriMspMbep, Integer> {
	
	@Query(value = "select amm.ID,amm.StateCode,amm.RegionID,amm.CommodityID,amm.VarietyID,amm.Date,amm.year,\n" + 
			"amm.PriceTypeID,amm.PriceValue,gs.Name as State, gr.Name as Region,\n" + 
			"ac.Name as Commodity, av.Name as Variety, apt.Name as PriceType from agri_MSP_MBEP amm\n" + 
			"left join geo_state gs on(gs.StateCode = amm.StateCode)\n" + 
			"left join geo_region gr on(gr.RegionID = amm.RegionID)\n" + 
			"left join agri_commodity ac on(ac.ID = amm.CommodityID)\n" + 
			"left join agri_variety av on(av.ID = amm.VarietyID)\n" + 
			"left join agri_price_type apt on(apt.ID = amm.PriceTypeID)", nativeQuery = true)
	List<AgriMspMbep> getAllAgriMspMbep();

}
