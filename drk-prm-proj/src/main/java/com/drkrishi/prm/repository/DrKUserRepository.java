package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.DRKrishiUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;



public interface DrKUserRepository extends CrudRepository<DRKrishiUserEntity, Integer>,
QueryByExampleExecutor<DRKrishiUserEntity> {
	
	
	
	@Query("SELECT d FROM DRKrishiUserEntity AS d where d.reportingTo = :id")
	List<DRKrishiUserEntity> findByReportingId(@Param(value ="id") int id);
	
	
	@Query("SELECT d FROM DRKrishiUserEntity AS d where d.id = :id")
	List<DRKrishiUserEntity> findByuserId(@Param(value ="id") int id);
}
