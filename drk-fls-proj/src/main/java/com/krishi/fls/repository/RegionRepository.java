package com.krishi.fls.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.Region;

public interface RegionRepository extends CrudRepository<Region, Integer>,
QueryByExampleExecutor<Region> {

}
