package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.AuditTrial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AuditTrialRepository extends CrudRepository<AuditTrial, Integer>, QueryByExampleExecutor<AuditTrial> {

}
