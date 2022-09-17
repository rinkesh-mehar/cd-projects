package com.drkrishi.usermanagement.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.drkrishi.usermanagement.entity.AuditTrialEntity;

@Repository
public  interface AuditTrialRepository extends CrudRepository<AuditTrialEntity,Integer> {

	AuditTrialEntity findAllById(int i);

}
