package com.krishi.fls.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.Village;

public interface VillageRepository  extends CrudRepository<Village, Integer>,
QueryByExampleExecutor<Village> {

}
