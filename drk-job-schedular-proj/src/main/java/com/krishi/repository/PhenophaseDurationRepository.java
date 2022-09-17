package com.krishi.repository;

import com.krishi.model.DataInsertionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.PhenophaseDuration;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhenophaseDurationRepository extends JpaRepository<PhenophaseDuration, Integer> {

    @Query(value = "select id, state_id as stateCode, commodity_id as commodityId, variety_id as varietyId, phenophase_id as phenophaseId, start_das as startDas, end_das as endDas, acz_id as aczId, sowing_start_week as sowingStartWeek, sowing_end_week as sowingEndWeek, harvest_start_week as harvestStartWeek, harvest_end_week as harvestEndWeek, status as Status from phenophase_duration where state_id=?1", nativeQuery = true)
    List<DataInsertionModel> getPhenophaseDurationRepository(Integer stateId);
}
