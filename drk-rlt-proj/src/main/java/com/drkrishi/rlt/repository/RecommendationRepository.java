package com.drkrishi.rlt.repository;

import com.drkrishi.rlt.entity.Recommendation;
import com.drkrishi.rlt.model.vo.RecommendationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {

    Recommendation findByName(String name);

    @Query(value = "select IF((cr.control_measure_id=1), ar.Name, CONCAT(ifnull(aaat.name,''), ' of ', ifnull(aa.name,''), ' @ ',\n" +
            "ifnull(cr.dose,''), ' ', ifnull(gu1.name,''), ' in ', ifnull(cr.water,''), ' ', ifnull(gu2.name,''),\n" +
            "'.',ifnull(aai.name,''))) AS Recommendation, cr.id as RecommendationId\n" +
            "from drkrishi_s1.stress_recommendation cr\n" +
            "         LEFT JOIN drkrishi_s1.recommendation AS ar ON cr.recommendation_id = ar.id\n" +
            "         left join drkrishi_s1.agrochemical aa on cr.agrochemical_id=aa.id\n" +
            "         left join drkrishi_s1.agri_agrochemical_instructions aai on cr.agrochemical_instruction_id=aai.id\n" +
            "         LEFT JOIN drkrishi_s1.agrochemical_application_type AS aaat ON aaat.ID = cr.agrochem_application_type_id\n" +
            "         LEFT JOIN drkrishi_s1.unit_of_measurement AS gu1 ON gu1.ID = cr.dose_uom_id\n" +
            "         LEFT JOIN drkrishi_s1.unit_of_measurement AS gu2 ON gu2.ID = cr.water_uom_id\n" +
            "where cr.commodity_id=?1 and cr.stress_id=?2 and cr.control_measure_id in (?3) and (?5) between cr.sowing_week_start and\n" +
            "cr.sowing_week_end and cr.acz_id =?4", nativeQuery = true)
    List<RecommendationVO> getRecommendation(Integer commodityId, Integer stressId, List<Integer> controlMeasureId, int aczId, int correctedSowingWeek);
}
