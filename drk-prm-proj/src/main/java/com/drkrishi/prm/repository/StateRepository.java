package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.StateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface StateRepository  extends CrudRepository<StateEntity, Integer>,
QueryByExampleExecutor<StateEntity> {
	
}
