package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.cropdata.farmers.app.masters.model.Commodity;
import in.cropdata.farmers.app.masters.model.DTO.CommodityDTO;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
	

	

	@Query(value = " select ac.ID as ID , ac.Name as name  , MonthName(STR_TO_DATE(concat(Year(curdate()), zc.SowingWeekStart, ' Wednesday'), '%X %V %W')) as SowingWeekStart,\n"
			+ "MonthName(STR_TO_DATE(concat(Year(curdate()), zc.SowingWeekEnd, ' Wednesday'), '%X %V %W')) as SowingWeekEnd ,zc.ID as zonalCommodityId  \n"
			+ "from cdt_master_data.agri_commodity ac  inner join cdt_master_data.zonal_commodity zc on zc.CommodityID = ac.ID \n"
			+ "where zc.AczID = ?1 and ?2 between zc.SowingWeekStart and zc.SowingWeekEnd order by ac.Name ASC ", nativeQuery = true)
	List<CommodityDTO> getAllActiveCommodityByStateCode(Integer aczId, Integer sowingWeek);

//	@Query(value="  select distinct temp.id as id,temp.name as name from (select distinct ac.ID as id , ac.Name as name from cdt_master_data.agri_commodity ac \n" + 
//			"inner join cdt_master_data.regional_commodity rc on rc.CommodityID = ac.ID \n" + 
//			"inner join cdt_master_data.regional_variety rv on rv.CommodityID = rc.CommodityID and rv.StateCode = rc.StateCode  \n" + 
//			"inner join cdt_master_data.geo_district GD on GD.StateCode = rc.StateCode where rc.Status ='Active' and GD.DistrictCode = ?1) temp\n" + 
//			"inner join gstm_data_models.pvi_daily_market_price pd on pd.CommodityID = temp.id\n" + 
//			"where pd.DistrictCode = ?2  ",nativeQuery = true)
//	List<Commodity> getAllActiveCommodityByDistrictCode(Integer districtCode,Integer districtCodePVI);

//	@Query(value = " select distinct pd.CommodityID as id, CONCAT(UCASE(SUBSTRING(ac.name, 1, 1)), LOWER(SUBSTRING(ac.name, 2))) as name\n"
//			+ "from gstm_data_models.pvi_daily_market_price pd inner join cdt_master_data.agri_commodity ac on ac.id = pd.CommodityID\n"
//			+ "where pd.DistrictCode = ?1 order by name Asc  ", nativeQuery = true)
//	List<Commodity> getAllActiveCommodityByDistrictCode(Integer districtCode, String neighnouringDistrict);
	
	


}
