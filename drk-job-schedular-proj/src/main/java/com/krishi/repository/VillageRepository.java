package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.Commodity;
import com.krishi.entity.Tehsil;
import com.krishi.entity.Village;

import java.util.Optional;


public interface VillageRepository
  extends JpaRepository<Village, Integer> , QueryByExampleExecutor<Village> {

    @Query(value = "select v.region_id from village v where v.id = ?1 ", nativeQuery = true)
    String findRegionById(Integer villageId);

    @Query("select v.aczId from Village v where v.id=:villageId")
    Integer getVillage(@Param("villageId") Integer villageId);
}