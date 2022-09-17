package com.krishi.repository;

import com.krishi.vo.ViewFieldMonitoringDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.ViewFieldMonitoringDetail;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ViewFieldMonitoringDetailRepository extends JpaRepository<ViewFieldMonitoringDetail, String>{

    @Query(value = "SELECT\n" +
            "    distinct t.id AS taskId, ri.id as rightId, t.task_date AS minDate, ADDDATE(t.task_date, 2) AS maxDate, t.entity_id AS entityId, t.task_type_id AS taskTypeId, t.task_date AS taskDate, c.name AS cropName, v.name AS varietyName,\n" +
            "    cci.crop_area AS cropArea, cci.farmer_given_sowing_week AS sowingWeek, cci.farmer_given_sowing_year AS sowingYear, ss.name AS seedName, cci.seeds_sample_received AS seedsSampleReceived,\n" +
            "    cci.seeds_rates AS seedsRates, cci.spacing_row AS spacingRow, cci.spacing_plant AS spacingPlant, cfd.irrigation_source_id AS irrigationSourceId,\n" +
            "    cfd.irrigation_method_id AS irrigationMethodId, cfd.number_of_irrigation AS numberOfIrrigation, cfd.week_of_irrigation AS weekOfIrrigation, cfd.year_of_irrigation AS yearOfIrrigation, f.name AS fertilizerName,\n" +
            "    fam.name AS fertilizerTypeOfApplication, cfd.fertilizer_dose AS fertilizerDose, cfd.fertilizer_split_dose AS fertilizerSplitDose, cfd.fertilizer_week_of_applcation AS fertilizerWeekOfApplcation,\n" +
            "    cfd.fertilizer_year_of_applcation AS fertilizerYearOfApplcation, cfd.seed_treatment AS seedTreatment, cfd.seed_treatment_agent_id AS seedTreatmentAgentId, cfd.seed_dose AS seedTreatmentDose,\n" +
            "    a.name AS agrochemicalName, ab.name AS agrochemicalBrandName, cfd.agrochemical_dose AS agrochemicalDose, farmer.farmer_name as farmerName,\n" +
            "    cfd.agrochemical_week_of_applcation AS agrochemicalWeekOfApplcation, cfd.agrochemical_year_of_applcation AS agrochemicalYearOfApplcation, farmer.primary_mob_number AS farmerPrimaryMobNumber, farmer.alternative_mob_number AS farmerAlternativeMobNumber,\n" +
            "    fc.nmd AS nextMonitoringDate,vill.name as villageName, r.name as regionName, s.name as stateName, teh.name as TehsilName, cci.harvest_week as harvestWeek, cci.harvest_year as harvestYear,\n" +
            "    (select r.current_quantity as currentQuantity from rights r\n" +
            "     where r.version_number = (select max(rights.version_number) from rights where case_id = cci.case_id)\n" +
            "       and case_id = cci.case_id group by r.current_quantity) as currentQuantity, d.name as districtName, ri.allowable_variance_qty_pos as allowableVarianceQtyPos, ri.allowable_variance_qty_neg as allowableVarianceQtyNeg\n" +
            "FROM    task t\n" +
            "        JOIN case_crop_info cci ON CONVERT( CONVERT( t.entity_id USING UTF8) USING UTF8MB4) = cci.case_id\n" +
            "        JOIN variety v ON cci.variety_id = v.id\n" +
            "        JOIN commodity c ON v.commodity_id = c.id\n" +
            "        JOIN seed_source ss ON cci.seed_source_id = ss.id\n" +
            "        JOIN case_field_details cfd ON CONVERT( t.entity_id USING UTF8) = CONVERT( cfd.case_id USING UTF8)\n" +
            "        JOIN fertilizer f ON cfd.fertilizer_id = f.id\n" +
            "        JOIN fertilizer_application_method fam ON cfd.application_id = fam.id\n" +
            "        JOIN agrochemical_brand ab ON cfd.agrochemical_brand_id = ab.id\n" +
            "        JOIN farm_case fc ON CONVERT( CONVERT( t.entity_id USING UTF8) USING UTF8MB4) = fc.id\n" +
            "        JOIN farmer_farm ff ON fc.farm_id = ff.id\n" +
            "        JOIN farmer ON ff.farmer_id = CONVERT( CONVERT( farmer.id USING UTF8) USING UTF8MB4)\n" +
            "        JOIN agrochemical a ON ab.agrochemical_id = a.id\n" +
            "        Join village vill on vill.id = ff.village_id\n" +
            "        join panchayat p on vill.panchayat_id = p.id\n" +
            "        join tehsil teh on p.tehsil_id = teh.id\n" +
            "        join region r on r.id = vill.region_id\n" +
            "        join state s on s.id = r.state_id\n" +
            "        join district d on d.id = teh.district_id\n" +
            "        join rights ri on ri.case_id = cci.case_id\n" +
            "where t.id =?1", nativeQuery = true)
    Optional<ViewFieldMonitoringDetailVO> getFieldMonitoringByTaskId(String taskId);
}
