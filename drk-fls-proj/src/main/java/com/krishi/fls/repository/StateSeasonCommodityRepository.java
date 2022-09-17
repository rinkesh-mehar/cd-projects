package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.StateSeasonCommodity;

public interface StateSeasonCommodityRepository extends CrudRepository<StateSeasonCommodity, Integer>,
QueryByExampleExecutor<StateSeasonCommodity> {

	@Query("select distinct ssc from Users as u inner join StateSeason as ss on u.stateId = ss.stateId "
			+ " inner join StateSeasonCommodity as ssc on ss.id = ssc.stateSeasonId "
			+ " where u.id = :userId ")
	public List<StateSeasonCommodity> findCommodities(@Param(value ="userId")Integer userId);
}
