package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.DrKrishiUserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface UserRolesRepository extends CrudRepository<DrKrishiUserRoleEntity, Integer>,
QueryByExampleExecutor<DrKrishiUserRoleEntity> {
	
	@Query("SELECT d FROM DrKrishiUserRoleEntity AS d where d.roleId = 13 and d.userId = :id")
	List<DrKrishiUserRoleEntity> findPRSRoleByUserId(@Param(value ="id") int id);
	

}
