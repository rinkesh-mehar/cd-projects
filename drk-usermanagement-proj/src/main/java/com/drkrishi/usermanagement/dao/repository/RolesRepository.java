package com.drkrishi.usermanagement.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.drkrishi.usermanagement.entity.AuditTrialEntity;
import com.drkrishi.usermanagement.entity.Roles;

@Repository
public  interface RolesRepository extends CrudRepository<Roles,Integer> {
	
	Roles findByReportingTo(Integer reportingTo);

}
