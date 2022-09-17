package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.VillagetaskEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface villageTaskRepository
		extends CrudRepository<VillagetaskEntity, Integer>, QueryByExampleExecutor<VillagetaskEntity> {

	@Query("SELECT v.villageEntity.code FROM VillagetaskEntity AS v where v.prVillageAssigment.weekNumber = :week and v.prVillageAssigment.year = :year")
	List<Integer> getAssignedVillageByWeek(int week, int year);
	
	@Query("SELECT v FROM VillagetaskEntity AS v where v.prVillageAssigment.drkrishiUser1.id = :prmId AND v.prVillageAssigment.year = :yearvalue AND v.prVillageAssigment.weekNumber = :weekvalue and v.status=1")
	List<VillagetaskEntity> findVillageTaskBYPrmId(@Param(value = "prmId") int prmId,
			@Param(value = "weekvalue") int weekvalue, @Param(value = "yearvalue") int yearvalue);

	@Query("SELECT v FROM VillagetaskEntity AS v where v.villageEntity.id = :villageId and v.status=1")
	List<VillagetaskEntity> findVillageTaskBYvillageIdId(@Param(value = "villageId") int villageId);

	@Query("SELECT v FROM VillagetaskEntity AS v where v.prVillageAssigment.assigment_Id = :assigmentId and v.status=1")
	List<VillagetaskEntity> findByprVillageAssigment(@Param(value = "assigmentId") Integer assigmentId);

	@Query("SELECT v FROM VillagetaskEntity AS v where v.prVillageAssigment.assigment_Id = :assigmentId and villageEntity.code = :villListCodeData and v.status=1")
	VillagetaskEntity findByprVillageAssigmentAndvillageEntity(@Param(value = "assigmentId") Integer assigmentId,
			@Param(value = "villListCodeData") Integer villListCodeData);

	@Query("SELECT v FROM VillagetaskEntity AS v where v.prVillageAssigment.assigment_Id = :assgnmentId and v.status=1")
	List<VillagetaskEntity> findAllByPrVillageAssigment(@Param(value = "assgnmentId") int assgnmentId);

	@Transactional

	@Modifying

	@Query("UPDATE  VillagetaskEntity v SET v.villageEntity.code =:villCode where v.prVillageAssigment.assigment_Id = :assigmentId")
	void saveTask(@Param(value = "assigmentId") int assigmentId, @Param(value = "villCode") int villCode);

	@Transactional

	@Modifying
	@Query(" UPDATE VillagetaskEntity vt set vt.status=0 where vt.prVillageAssigment.assigment_Id = :assgnmentId")
	int updateTOInActive(@Param(value = "assgnmentId") int assgnmentId);

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("UPDATE  VillagetaskEntity v SET v.status=0 WHERE v.villageEntity.code = :newVillCode and v.prVillageAssigment.assigment_Id = :assigmentId"
	 * ) void update ToInActive(@Param(value="newVillCode")int
	 * newVillCode,@Param(value="assigmentId")Integer assigmentId);
	 */
}
