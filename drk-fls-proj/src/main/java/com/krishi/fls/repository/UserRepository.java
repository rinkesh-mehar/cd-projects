package com.krishi.fls.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.Users;

public interface UserRepository  extends CrudRepository<Users, Integer>,
QueryByExampleExecutor<Users> {
  

}
