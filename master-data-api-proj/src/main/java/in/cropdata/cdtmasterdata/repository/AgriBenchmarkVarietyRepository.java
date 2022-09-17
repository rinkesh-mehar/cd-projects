package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriBenchmarkVarietyInfo;
import in.cropdata.cdtmasterdata.model.AgriBenchmarkVariety;

public interface AgriBenchmarkVarietyRepository extends JpaRepository<AgriBenchmarkVariety, Integer> {
	
	@Query(value = "Select abv.ID,abv.StateCode,abv.RegionID,abv.SeasonID,abv.CommodityID,abv.VarietyID,abv.IsDrkBenchmark,abv.IsAgmBenchmark,abv.CreatedAt,abv.UpdatedAt,abv.Status,\n" + 
			"						gs.Name as State,gr.Name as Region, ass.Name Season, ac.Name as Commodity,\n" + 
			"			            av.Name as Variety from agri_benchmark_variety abv \n" + 
			"						left join geo_state gs on(gs.StateCode = abv.StateCode)\n" + 
			"						left join geo_region gr on(gr.RegionID = abv.RegionID)\n" + 
			"						left join agri_season ass on(ass.ID = abv.SeasonID)\n" + 
			"						left join agri_commodity ac on(ac.ID = abv.CommodityID)\n" + 
			"						left join agri_variety av on(av.ID = abv.VarietyID)", nativeQuery = true)
	List<AgriBenchmarkVarietyInfo> getAllAgriBenchmarkVarietyList();
	
	@Query(value = "SELECT \n" + 
			"    abv.ID,\n" + 
			"    abv.StateCode,\n" + 
			"    abv.RegionID,\n" + 
			"    abv.SeasonID,\n" + 
			"    abv.CommodityID,\n" + 
			"    abv.VarietyID,\n" + 
			"    abv.IsDrkBenchmark,\n" + 
			"    abv.IsAgmBenchmark,\n" + 
			"    abv.CreatedAt,\n" + 
			"    abv.UpdatedAt,\n" + 
			"    abv.Status,\n" + 
			"    gs.Name AS State,\n" + 
			"    gr.Name AS Region,\n" + 
			"    ass.Name Season,\n" + 
			"    ac.Name AS Commodity,\n" + 
			"    av.Name AS Variety\n" + 
			"FROM\n" + 
			"    agri_benchmark_variety abv\n" + 
			"        LEFT JOIN\n" + 
			"    geo_state gs ON (gs.StateCode = abv.StateCode)\n" + 
			"        LEFT JOIN\n" + 
			"    geo_region gr ON (gr.RegionID = abv.RegionID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_season ass ON (ass.ID = abv.SeasonID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_commodity ac ON (ac.ID = abv.CommodityID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_variety av ON (av.ID = abv.VarietyID)\n" + 
			"    where abv.ID like :searchText\n" + 
			"    OR gr.Name like :searchText\n" + 
			"    OR gs.Name like :searchText\n" + 
			"    OR ass.Name like :searchText\n" + 
			"    OR ac.Name like :searchText\n" + 
			"    OR av.Name like :searchText\n" + 
			"    OR abv.IsDrkBenchmark like :searchText\n" + 
			"    OR abv.IsAgmBenchmark like :searchText", countQuery = "SELECT \n" + 
					"    abv.ID,\n" + 
					"    abv.StateCode,\n" + 
					"    abv.RegionID,\n" + 
					"    abv.SeasonID,\n" + 
					"    abv.CommodityID,\n" + 
					"    abv.VarietyID,\n" + 
					"    abv.IsDrkBenchmark,\n" + 
					"    abv.IsAgmBenchmark,\n" + 
					"    abv.CreatedAt,\n" + 
					"    abv.UpdatedAt,\n" + 
					"    abv.Status,\n" + 
					"    gs.Name AS State,\n" + 
					"    gr.Name AS Region,\n" + 
					"    ass.Name Season,\n" + 
					"    ac.Name AS Commodity,\n" + 
					"    av.Name AS Variety\n" + 
					"FROM\n" + 
					"    agri_benchmark_variety abv\n" + 
					"        LEFT JOIN\n" + 
					"    geo_state gs ON (gs.StateCode = abv.StateCode)\n" + 
					"        LEFT JOIN\n" + 
					"    geo_region gr ON (gr.RegionID = abv.RegionID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_season ass ON (ass.ID = abv.SeasonID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_commodity ac ON (ac.ID = abv.CommodityID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_variety av ON (av.ID = abv.VarietyID)\n" + 
					"    where abv.ID like :searchText\n" + 
					"    OR gr.Name like :searchText\n" + 
					"    OR gs.Name like :searchText\n" + 
					"    OR ass.Name like :searchText\n" + 
					"    OR ac.Name like :searchText\n" + 
					"    OR av.Name like :searchText\n" + 
					"    OR abv.IsDrkBenchmark like :searchText\n" + 
					"    OR abv.IsAgmBenchmark like :searchText", nativeQuery = true)
	public Page<AgriBenchmarkVarietyInfo> getPageAgriBenchmarkVarietyList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT \n" + 
			"    abv.ID,\n" + 
			"    abv.StateCode,\n" + 
			"    abv.RegionID,\n" + 
			"    abv.SeasonID,\n" + 
			"    abv.CommodityID,\n" + 
			"    abv.VarietyID,\n" + 
			"    abv.IsDrkBenchmark,\n" + 
			"    abv.IsAgmBenchmark,\n" + 
			"    abv.CreatedAt,\n" + 
			"    abv.UpdatedAt,\n" + 
			"    abv.Status,\n" + 
			"    gs.Name AS State,\n" + 
			"    gr.Name AS Region,\n" + 
			"    ass.Name Season,\n" + 
			"    ac.Name AS Commodity,\n" + 
			"    av.Name AS Variety\n" + 
			"FROM\n" + 
			"    agri_benchmark_variety_missing abv\n" + 
			"        LEFT JOIN\n" + 
			"    geo_state gs ON (gs.StateCode = abv.StateCode)\n" + 
			"        LEFT JOIN\n" + 
			"    geo_region gr ON (gr.RegionID = abv.RegionID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_season ass ON (ass.ID = abv.SeasonID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_commodity ac ON (ac.ID = abv.CommodityID)\n" + 
			"        LEFT JOIN\n" + 
			"    agri_variety av ON (av.ID = abv.VarietyID)\n" + 
			"    where abv.ID like :searchText\n" + 
			"    OR gr.Name like :searchText\n" + 
			"    OR gs.Name like :searchText\n" + 
			"    OR ass.Name like :searchText\n" + 
			"    OR ac.Name like :searchText\n" + 
			"    OR av.Name like :searchText\n" + 
			"    OR abv.IsDrkBenchmark like :searchText\n" + 
			"    OR abv.IsAgmBenchmark like :searchText", countQuery = "SELECT \n" + 
					"    abv.ID,\n" + 
					"    abv.StateCode,\n" + 
					"    abv.RegionID,\n" + 
					"    abv.SeasonID,\n" + 
					"    abv.CommodityID,\n" + 
					"    abv.VarietyID,\n" + 
					"    abv.IsDrkBenchmark,\n" + 
					"    abv.IsAgmBenchmark,\n" + 
					"    abv.CreatedAt,\n" + 
					"    abv.UpdatedAt,\n" + 
					"    abv.Status,\n" + 
					"    gs.Name AS State,\n" + 
					"    gr.Name AS Region,\n" + 
					"    ass.Name Season,\n" + 
					"    ac.Name AS Commodity,\n" + 
					"    av.Name AS Variety\n" + 
					"FROM\n" + 
					"    agri_benchmark_variety_missing abv\n" + 
					"        LEFT JOIN\n" + 
					"    geo_state gs ON (gs.StateCode = abv.StateCode)\n" + 
					"        LEFT JOIN\n" + 
					"    geo_region gr ON (gr.RegionID = abv.RegionID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_season ass ON (ass.ID = abv.SeasonID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_commodity ac ON (ac.ID = abv.CommodityID)\n" + 
					"        LEFT JOIN\n" + 
					"    agri_variety av ON (av.ID = abv.VarietyID)\n" + 
					"    where abv.ID like :searchText\n" + 
					"    OR gr.Name like :searchText\n" + 
					"    OR gs.Name like :searchText\n" + 
					"    OR ass.Name like :searchText\n" + 
					"    OR ac.Name like :searchText\n" + 
					"    OR av.Name like :searchText\n" + 
					"    OR abv.IsDrkBenchmark like :searchText\n" + 
					"    OR abv.IsAgmBenchmark like :searchText", nativeQuery = true)
	public Page<AgriBenchmarkVarietyInfo> getPageAgriBenchmarkVarietyMissingList(Pageable sortedByIdDesc, String searchText);

}
