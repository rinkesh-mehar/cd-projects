package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.farmers.app.masters.model.Variety;

public interface VarietyRepository extends JpaRepository<Variety, Integer> {
	
	@Query(value = "SELECT DISTINCT\n"
			+ "    zv.VarietyID as ID, av.Name\n"
			+ "FROM\n"
			+ "    cdt_master_data.zonal_commodity zc\n"
			+ "        INNER JOIN\n"
			+ "    cdt_master_data.zonal_variety zv ON zc.ID = zv.ZonalCommodityID\n"
			+ "        INNER JOIN\n"
			+ "    cdt_master_data.agri_variety av ON zv.VarietyID = av.ID\n"
			+ "   where\n"
			+ "     zc.ID = ?1 order by av.Name", nativeQuery = true)
	List<Variety> getAllVarietyByCommodityIdAndStateCode(Integer zonalCommodityID);
	

}
