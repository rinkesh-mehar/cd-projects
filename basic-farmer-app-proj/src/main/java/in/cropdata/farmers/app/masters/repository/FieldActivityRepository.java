package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.model.FieldActivity;
import in.cropdata.farmers.app.masters.dto.FieldActivityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:32 PM
 */

@Repository
public interface FieldActivityRepository extends JpaRepository<FieldActivity, Integer> {

    @Query(value = "SELECT zf.ID, zf.Name, zf.Description FROM cdt_master_data.zonal_field_activity zf\n" + 
    		"where zf.ZonalCommodityID = ?1 and zf.PhenophaseID = ?2", nativeQuery = true)
    List<FieldActivityDTO> findFieldActivity(Integer zonalCommodityID, Integer phenophaseID);
}
