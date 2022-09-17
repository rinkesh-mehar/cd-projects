package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.StateEntity;

import java.util.List;

public interface StateRepository
  extends JpaRepository<StateEntity, Integer> , QueryByExampleExecutor<StateEntity> {

    /** Rinkesh KM */
    @Query(value = "select distinct id from state", nativeQuery = true)
    List<Integer> getStateIds();
}
