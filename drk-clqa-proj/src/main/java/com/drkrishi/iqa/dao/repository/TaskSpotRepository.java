package com.drkrishi.iqa.dao.repository;

import com.drkrishi.iqa.entity.TaskSpot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package com.drkrishi.iqa
 * @date 14/09/21
 * @time 10:55 AM
 */
@Repository
public interface TaskSpotRepository extends JpaRepository<TaskSpot, String>
{
    /*@Query(value = "select group_concat(distinct ts.id)as spotIds, count(distinct ts.id) as totalSpotCount, ts.taskId as taskId \n " +
            "from TaskSpot as ts \n" +
            "inner join Task as t on t.id = ts.taskId \n" +
            "inner join FarmerFarm ff on ff.id=fc.farmId \n"+
            "inner join Farmer f on f.id=ff.farmerId \n"+
            "inner join Village vg on vg.id=f.villageId \n"+
            "inner join SubTask st on st.taskId=t.id \n"+
            "inner join State s on s.stateId=vg.stateId \n"+
            "inner join Region r on r.regionId=vg.regionId \n"+
            "inner join CaseCropInfo cci on cci.caseId=t.entityId \n"+
            "inner join Variety v on v.id=cci.varietyId \n"+
            "inner join Commodity c on c.id=v.commodityId \n"+
            "inner join TaskSpotHealthImage  tshi on tshi.taskSpotId = ts.id \n"+
            "where t.taskTypeId=6 and st.imageIsVerified=0 and s.stateId=?1 and vg.regionId=?2 and c.id = ?3 group by ts.taskId", nativeQuery = true)*/
    @Query(value = "select group_concat( distinct ts.id) as spotIds, count(distinct ts.id) totalSpotCount, ts.task_id as taskId from task_spot ts\n" +
            "    inner join task t on (t.id=ts.task_id)\n" +
            "    inner join task_spot_health_image tshi on tshi.task_spot_id = ts.id\n" +
            "    inner join farm_case fc on (fc.id=t.entity_id)\n" +
            "    inner join farmer_farm ff on (ff.id=fc.farm_id)\n" +
            "    inner join farmer f on (f.id=ff.farmer_id)\n" +
            "    inner join village v on (v.id=f.village_id)\n" +
            "    inner join subtask st on (st.task_id=t.id)\n" +
            "    inner join state s on (s.id=v.state_id)\n" +
            "    inner join region r on (r.id=v.region_id)\n" +
            "    inner join case_crop_info cci on (cci.case_id=t.entity_id)\n" +
            "    inner join variety vt on (vt.id=cci.variety_id)\n" +
            "    inner join commodity c on (c.id=vt.commodity_id)\n" +
            "where t.task_type_id=6 and st.image_is_verified=0 and s.id=?1 and v.region_id=?2 \n" +
            "  and c.id=?3 group by ts.task_id", nativeQuery = true)
    List<Object[]> getTaskSpotByByRegion(Integer stateId, Integer regionId, Integer commodityId);
}
