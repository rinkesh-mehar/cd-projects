package com.krishi.fls.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.State;

public interface StateRepository
  extends CrudRepository<State, Integer> , QueryByExampleExecutor<State> {
}
