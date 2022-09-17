package com.krishi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.Commodity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommodityRepository  extends JpaRepository<Commodity, Integer>{

	@Query("select c from Commodity c inner join RegionalCommodity as rc on c.id = rc.commodityId where c.status = 1 and rc.regionId = :regionId  group by c.id")
	List<Commodity> findByRegionId(@Param("regionId") Integer regionId);


	/*@Query("SELECT\n" +
			"    pd.commodityId,\n" +
			"    c.name as commodity_name\n" +
			"FROM PhenophaseDuration pd\n" +
			"         inner join Commodity c on pd.commodityId=c.id\n" +
			"where\n" +
			"        pd.aczId IN (\n" +
			"        SELECT DISTINCT v.acz_id\n" +
			"        FROM Village v\n" +
			"        WHERE v.regionId = 2\n" +
			"    ) AND IF ( week(curdate()) < pd.sowing_start_week,week(curdate()) + 52,week(curdate())) BETWEEN pd.sowingStartWeek AND IF(pd.harvest_end_week < pd.sowing_start_week, pd.harvest_end_week + 52, pd.harvest_end_week) and pd.status=1\n" +
			"group by pd.commodity_id")
	List<Commodity> findById(@Param("regionId"), Integer regionId);*/

	@Query(value = "select * from commodity where id in (\n" +
			"\tselect distinct(commodity_id)\n" +
			"\tfrom drkrishi_s1.zonal_commodity \n" +
			"\twhere acz_id IN (\n" +
			"\tselect distinct(acz_id) from village where region_id=?1\n" +
			"\t)\n" +
			"\tAND IF(week(curdate()) < sowing_week_start, week(curdate()) + 52, week(curdate())) BETWEEN sowing_week_start AND IF(harvest_week_end < sowing_week_start, harvest_week_end + 52, harvest_week_end) \n" +
			"\tAND status=1)", nativeQuery = true)
	List<Commodity> farForwardCommodityList(Integer regionId);

	@Query(value = "select * from commodity where id in (\n" +
			"\tselect distinct(commodity_id)\n" +
			"\tfrom drkrishi_s1.zonal_commodity \n" +
			"\twhere acz_id IN (\n" +
			"\tselect distinct(acz_id) from village where region_id=?1\n" +
			"\t)\n" +
			"\tAND IF(week(curdate()) < sowing_week_start, week(curdate()) + 52, week(curdate())) BETWEEN sowing_week_start AND IF(harvest_week_end < sowing_week_start, harvest_week_end + 52, harvest_week_end) \n" +
			"\tAND status=1)", nativeQuery = true)
	List<Commodity> nearForwardCommodityList(Integer regionId);
}
