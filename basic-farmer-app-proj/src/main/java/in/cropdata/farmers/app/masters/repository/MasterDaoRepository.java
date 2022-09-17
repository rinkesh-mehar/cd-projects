package in.cropdata.farmers.app.masters.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.masters.model.Commodity;


@Repository
public class MasterDaoRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDaoRepository.class);

    @Autowired
    @Qualifier("masterDataJdbcTemplate")
    JdbcTemplate masterDataJdbcTemplate;

    public List<Map<String, Object>> getAllActiveCommodity(Integer aczId, String sowingDate,Integer cropTypeId)
    { 
    	StringJoiner sb = new StringJoiner(" "); 
    	
        sb.add("select ac.ID as ID , ac.Name as name  , MonthName(STR_TO_DATE(concat(Year(curdate()), zc.SowingWeekStart, ' Wednesday'), '%X %V %W')) as SowingWeekStart,\n" + 
        		"MonthName(STR_TO_DATE(concat(Year(curdate()), zc.SowingWeekEnd, ' Wednesday'), '%X %V %W')) as SowingWeekEnd ,zc.ID as zonalCommodityId  \n" + 
        		"from cdt_master_data.agri_commodity ac  inner join cdt_master_data.zonal_commodity zc on zc.CommodityID = ac.ID \n") ; 
        if(cropTypeId == 1 || cropTypeId == 2) {
        	sb.add(" inner join cdt_master_data.agri_meteorological_weeks amw on  amw.Day = day('"+ sowingDate +"') and amw.Month = month('"+ sowingDate +"') ");
        }
        sb.add(" where zc.AczID =  "+aczId+" ");
        if(cropTypeId == 1 || cropTypeId == 2) {
        	sb.add(" and amw.Week between zc.SowingWeekStart and zc.SowingWeekEnd ");
        }
        sb.add("  order by ac.Name ASC   ");
        LOGGER.warn("final query for fetching data is -> {}", sb.toString());
        return masterDataJdbcTemplate.queryForList(sb.toString());
    }
    
    
//    public List<Map<String,Object>> getAllActiveCommodityByDistrictCode(String farmerId,String neighnouringDistrict)
//    {
//        String query = " select distinct ac.id as ID, CONCAT(UCASE(SUBSTRING(ac.name, 1, 1)), LOWER(SUBSTRING(ac.name, 2))) as name from drkrishi.farm_case fc \n" + 
//    			"inner join drkrishi.case_crop_info cci on cci.case_id = fc.id\n" + 
//    			"inner join drkrishi.variety v on v.id = cci.variety_id\n" + 
//    			"inner join drkrishi.commodity c on c.id = v.commodity_id\n" + 
//    			"inner join drkrishi.farmer_farm ff on ff.id = fc.farm_id\n" + 
//    			"inner join drkrishi.farmer f on f.id = ff.farmer_id\n" + 
//    			"inner join gstm_data_models.pvi_daily_market_price pd on pd.CommodityID = v.commodity_id\n" + 
//    			"inner join cdt_master_data.agri_commodity ac on ac.ID = pd.CommodityID\n" + 
//    			"where f.id = '"+farmerId+"' and pd.DistrictCode in("+ neighnouringDistrict +")" ;
//        
//        LOGGER.info("final query for fetching data is -> {}", query);
//        try {
//        	return  masterDataJdbcTemplate.queryForList(query);
//		} catch (DataAccessException e) {
//			return new ArrayList<>();
//		}
//    }
    
    public List<Map<String,Object>> getNeighbourDistrictByFarmerID(String farmerId)
    {
    	String query = "select concat(cd.DistrictCode, if(gd.NeighbourDistricts is null, \"\", concat(\",\", gd.NeighbourDistricts))) as Districts\n" + 
    			"from gstm_transitory.case_details as cd\n" + 
    			"inner join cdt_master_data.geo_district as gd on gd.DistrictCode = cd.DistrictCode\n" + 
    			"where cd.FarmerID = '"+farmerId+"' and cd.expired = \"No\"";
    	LOGGER.info("final query for fetching data is -> {}", query);
    	try {
    		return  masterDataJdbcTemplate.queryForList(query);
    	} catch (DataAccessException e) {
    		return new ArrayList<>();
    	}
    	
    }
    
    public List<Map<String,Object>> getNeighbourDistrictByCommodityID(String farmerId,Integer commodityId)
    {
    	String query = "select concat(cd.DistrictCode, if(gd.NeighbourDistricts is null, \"\", concat(\",\", gd.NeighbourDistricts))) as Districts\n" + 
    			"from gstm_transitory.case_details as cd\n" + 
    			"inner join cdt_master_data.geo_district as gd on gd.DistrictCode = cd.DistrictCode\n" + 
    			"where cd.FarmerID = '"+farmerId+"' and cd.expired = \"No\" and cd.CommodityID = "+commodityId+" ";
    	LOGGER.info("final query for fetching data is -> {}", query);
    	try {
    		return  masterDataJdbcTemplate.queryForList(query);
    	} catch (DataAccessException e) {
    		return new ArrayList<>();
    	}
    	
    }
    
    
    public List<Map<String,Object>> getActiveCommodityByIncludingNeighboringDistrictCode(String farmerId,String neighnouringDistrict)
    {
        String query = " select cd.CommodityID as ID,\n" + 
        		"CONCAT(UCASE(SUBSTRING(any_value(ac.Name), 1, 1)), LOWER(SUBSTRING(any_value(ac.Name), 2))) as name\n" + 
        		"from gstm_transitory.case_details as cd\n" + 
        		"inner join cdt_master_data.agri_commodity as ac on ac.id = cd.CommodityID\n" + 
        		"inner join gstm_data_models.pvi_daily_market_price pd on pd.CommodityID = cd.CommodityID\n" + 
        		"where cd.FarmerID = '"+farmerId+"' and cd.expired = \"No\" and pd.DistrictCode in("+neighnouringDistrict+")\n" + 
        		"group by cd.CommodityID order by name;" ;
        
        LOGGER.info("final query for fetching data is -> {}", query);
        try {
        	return  masterDataJdbcTemplate.queryForList(query);
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
    }
    
    public String getNeighbourDistrictByDistrictID(Integer distrcitID)
    {
        String query = " select concat(gd.DistrictCode, if(gd.NeighbourDistricts is null, \"\", concat(\",\", gd.NeighbourDistricts))) as neighbourDistricts from cdt_master_data.geo_district gd where gd.DistrictCode ="+distrcitID ;
        LOGGER.info("final query for fetching data is -> {}", query);
        try {
        	return  masterDataJdbcTemplate.queryForObject(query, String.class);
		} catch (DataAccessException e) {
			return "";
		}
    }
    
    public Integer getCurrentPhenophase(Integer zonalVarietyId,String sowingDate)
    {
    	String query = " select zp.PhenophaseID from cdt_master_data.zonal_phenophase_duration zp where zp.ZonalVarietyID = "+zonalVarietyId+" and (DATEDIFF(curdate(), '"+sowingDate+"' ) between zp.StartDas and zp.EndDas) ";
    	LOGGER.info("Phenophase pull -> {}", query);
    	try {
    		return  masterDataJdbcTemplate.queryForObject(query, Integer.class);
    	} catch (DataAccessException e) {
    		return 0;
    	}
    }
    
    public Integer getCommodityIdByVarietyId(Integer varietyId)
    {
        String query = "select av.CommodityID from cdt_master_data.agri_variety av where av.ID = " + varietyId ;
        LOGGER.info("final query for fetching commodityID is -> {}", query);
        try {
        	return  masterDataJdbcTemplate.queryForObject(query, Integer.class);
		} catch (DataAccessException e) {
			return null;
		}
    }
	
}
