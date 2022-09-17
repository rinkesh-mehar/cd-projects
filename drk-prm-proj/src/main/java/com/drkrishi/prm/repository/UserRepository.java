package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.DRKrishiUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends CrudRepository<DRKrishiUserEntity, Integer>,
QueryByExampleExecutor<DRKrishiUserEntity> {

}
