package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.TehsilEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface TehsilRepository extends CrudRepository<TehsilEntity, Integer>,
QueryByExampleExecutor<TehsilEntity> {
	@Query("SELECT t FROM TehsilEntity AS t where t.districtId = :districtCode")
	List<TehsilEntity> findByDistrictCode(@Param(value = "districtCode") int districtCode);

}
