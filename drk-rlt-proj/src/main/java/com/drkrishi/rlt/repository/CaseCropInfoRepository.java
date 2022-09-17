package com.drkrishi.rlt.repository;

import com.drkrishi.rlt.entity.CaseCropInfo;
import com.drkrishi.rlt.model.vo.CaseCropInfoVo;
import com.drkrishi.rlt.model.vo.StressCaseVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author RinkeshKM
 * @Date 05/08/21
 */

@Repository
public interface CaseCropInfoRepository extends JpaRepository<CaseCropInfo, String> {

    @Query(value = "select zc.id as zonalCommodityId, c.id as commodityId from task t \n" +
            "    inner join case_crop_info cci on cci.case_id = t.entity_id\n" +
            "    inner join variety v on cci.variety_id = v.id\n" +
            "    inner join commodity c on c.id = v.commodity_id\n" +
            "    inner join zonal_commodity zc on (zc.commodity_id = v.commodity_id and cci.acz_id = zc.acz_id\n" +
            "        /*and week(curdate()) between zc.sowing_week_start and zc.harvest_week_end*/)\n" +
            "where t.id =:taskId limit 1", nativeQuery = true)
    CaseCropInfoVo findCommodityByTaskId(@Param("taskId") String taskId);


    @Query(value = "select\n" +
            "#    distinct ts.id,\n" +
            "       if(`tsssi`.`side` = 'L', `tsssi`.incidence, null) AS `leftIncident`,\n" +
            "       if(`tsssi`.`side` = 'R', `tsssi`.incidence, null) AS `rightIncident`,\n" +
            "       if(`tsssi`.`side` = 'F', `tsssi`.incidence, null) AS `frontIncident`,\n" +
            "       if(`tsssi`.`side` = 'L' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null) AS `leftPhoto`,\n" +
            "       if(`tsssi`.`side` = 'R' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null) AS `rightPhoto`,\n" +
            "       if(`tsssi`.`side` = 'F' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null) AS `frontPhoto`,\n" +
            "       if(`tsssi`.`side` = 'L', `tsssi`.severity, null) AS `leftSeverity`,\n" +
            "       if(`tsssi`.`side` = 'R', `tsssi`.severity, null) AS `rightSeverity`,\n" +
            "       if(`tsssi`.`side` = 'F', `tsssi`.severity, null) AS `frontSeverity`,\n" +
            "       if(`tsssi`.`side` = 'F', `tsssi`.benchmark, null) AS `isBenchmarkedFront`,\n" +
            "       if(`tsssi`.`side` = 'L', `tsssi`.benchmark, null) AS `isBenchmarkedLeft`,\n" +
            "       if(`tsssi`.`side` = 'R', `tsssi`.benchmark, null) AS `isBenchmarkedRight`,\n" +
            "       `ts`.`id` as `spotId`, `tsss`.`symptom_id` AS `symptomDesc`, `s`.`status` AS `stressStatus`,\n" +
            "       `s`.`name`                   AS `stressName`,\n" +
            "       `s`.`stress_id`              AS `stressId`,\n" +
            "       `st`.`name`                  AS `stressType`\n" +
            "       , group_concat(distinct tsssi.id) AS `id`\n" +
            "from `drkrishi_s1`.`task_spot` `ts`\n" +
            "    inner join drkrishi_s1.task_spot_stress tss on ts.id = tss.task_spot_id\n" +
            "    inner join drkrishi_s1.task_stress_spot_symptoms tsss on tsss.task_spot_stress_id = tss.id\n" +
            "    inner join drkrishi_s1.task_stress_spot_symptom_images tsssi on tsssi.task_spot_stress_symptom_id = tsss.id\n" +
            "    inner join `drkrishi_s1`.`stress_symptoms` `ss` on ((`tsss`.`symptom_id` = `ss`.`id`))\n" +
            "    inner join `drkrishi_s1`.`stress` `s` on ((`ss`.`stress_id` = `s`.`stress_id`))\n" +
            "    inner join `drkrishi_s1`.`stress_type` `st` on ((`s`.`stress_type_id` = `st`.`id`))\n" +
            "where ts.id = ?1 /*and tsssi.status = 'Verified'*/\n" +
            "group by\n" +
            "         if(`tsssi`.`side` = 'L', `tsssi`.incidence, null),\n" +
            "         if(`tsssi`.`side` = 'R', `tsssi`.incidence, null),\n" +
            "         ts.id,\n" +
            "         if(`tsssi`.`side` = 'F', `tsssi`.incidence, null),\n" +
            "         if(`tsssi`.`side` = 'L' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null),\n" +
            "         if(`tsssi`.`side` = 'R' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null),\n" +
            "         if(`tsssi`.`side` = 'F' /*and `tsssi`.`status` = 'Verified'*/, `tsssi`.image_url, null),\n" +
            "         if(`tsssi`.`side` = 'L', `tsssi`.severity, null),\n" +
            "         if(`tsssi`.`side` = 'R', `tsssi`.severity, null),\n" +
            "         if(`tsssi`.`side` = 'F', `tsssi`.severity, null),\n" +
            "         if(`tsssi`.`side` = 'F', `tsssi`.benchmark, null),\n" +
            "         if(`tsssi`.`side` = 'L', `tsssi`.benchmark, null),\n" +
            "         if(`tsssi`.`side` = 'R', `tsssi`.benchmark, null),\n" +
            "         `ts`.`id`, `tsss`.`symptom_id`, `s`.`status`,\n" +
            "         `s`.`name`, `s`.`stress_id`,\n" +
            "         `st`.`name` order by ts.id, group_concat(tsssi.id)", nativeQuery = true)
    List<StressCaseVO> getStressCaseDetailBySpotId(String spotId);

    @Query(value = "select\n" +
            "       stress_name as StressName,\n" +
            "       if(side = 'F' , severity, null) AS `frontSeverity`,\n" +
            "       if(side = 'L' , severity, null) AS `leftSeverity`,\n" +
            "       if(side = 'R' , severity, null) AS `rightSeverity`,\n" +
            "       stress_id as StressId\n" +
            "from view_stress_case where stress_spot_symptom_image_id in (?1)\n" +
            "group by stress_name, stress_id", nativeQuery = true)
    List<StressCaseVO> getRecommendation(List<String> spotId);

    @Query(value = "select tsssi.id from task_spot ts\n" +
            "    inner join task_spot_stress tss on ts.id = tss.task_spot_id\n" +
            "inner join task_stress_spot_symptoms tsss on tsss.task_spot_stress_id = tss.id\n" +
            "inner join task_stress_spot_symptom_images tsssi  on tsssi.task_spot_stress_symptom_id = tsss.id\n" +
            "where ts.id in (?1)", nativeQuery = true)
    List<String> taskStressSpotSymptomImages(List<String> spotIds);
}
