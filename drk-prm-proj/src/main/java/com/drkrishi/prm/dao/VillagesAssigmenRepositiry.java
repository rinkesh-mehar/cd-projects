package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.PR_Village_AssigmentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VillagesAssigmenRepositiry extends CrudRepository<PR_Village_AssigmentEntity, Integer>,
		QueryByExampleExecutor<PR_Village_AssigmentEntity> {

	@Query("SELECT p FROM PR_Village_AssigmentEntity AS p where p.drkrishiUser1.id = :id and p.status=1")
	List<PR_Village_AssigmentEntity> findByPrmID(@Param(value = "id") int prmId);
	
	@Query("SELECT p FROM PR_Village_AssigmentEntity AS p where p.drkrishiUser2.id = :id and p.weekNumber =:week and p.year=:year and p.status=1")
	PR_Village_AssigmentEntity findByPRSIdForWeek(@Param(value = "id") int prsId,@Param(value = "week") int week,@Param(value = "year") int year);

	@Query("SELECT p FROM PR_Village_AssigmentEntity AS p where p.assigment_Id = :assigmentId and p.status=1")
	PR_Village_AssigmentEntity findByassigment_Id(@Param(value="assigmentId")Integer assigmentId);

	@Transactional
	@Modifying
	@Query("update PR_Village_AssigmentEntity pr set pr.status = 0 where pr.assigment_Id = :assgnmentId ")
	int update(@Param(value="assgnmentId")int assgnmentId);

}