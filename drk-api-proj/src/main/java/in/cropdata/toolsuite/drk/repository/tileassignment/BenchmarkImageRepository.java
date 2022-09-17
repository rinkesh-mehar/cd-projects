package in.cropdata.toolsuite.drk.repository.tileassignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.dto.tileassignment.BenchmarkVO;
import in.cropdata.toolsuite.drk.model.tileassignment.BenchmarkImage;

public interface BenchmarkImageRepository extends JpaRepository<BenchmarkImage, Long> {

	@Query(value = "select  ac.Name as CommodityName ,ass.Name as StressName, ast.Name as StressTypeName from zonal_stress_duration zs inner join zonal_commodity zc on zs.ZonalCommodityID=zc.ID\n" +
			" inner join agri_commodity ac on zc.CommodityID=ac.ID\n" +
			" inner join agri_stress ass on zs.StressID=ass.ID\n" +
			" inner join agri_stress_type ast on ast.ID=ass.StressTypeID\n" +
			" where ac.ID=?2 and ass.ID=?1 \n" +
			" group by ac.Name,ass.Name,ast.Name;", nativeQuery = true)
	BenchmarkVO getData(Integer stressID, Integer commodityID);

	@Query(value = "select ac.Name CommodityName,ast.name StressTypeName,ap.name PhenophaseName,\n" +
			"       agstg.Name StrssStageName,agri_stress.Name StressName from agri_stage agstg\n" +
			"INNER JOIN agri_commodity_stress_stage acss on(acss.StageID = agstg.ID)\n" +
			"INNER JOIN agri_commodity ac ON acss.commodityID=ac.id\n" +
			"INNER JOIN agri_stress agri_stress on acss.StressID = agri_stress.ID\n" +
			"INNER JOIN agri_stress_type ast ON agri_stress.stressTypeID=ast.id\n" +
			"INNER JOIN agri_phenophase ap ON acss.startphenophaseId=ap.id \n" +
			"where agstg.ID =?1 and ac.ID =?2 and agri_stress.id =?3", nativeQuery = true)
	BenchmarkVO getMetaDataByStressStageID(Integer stressStageId, Integer commodityId, Integer stressId);

}
