package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.PrmTilesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrmTilesRepository extends CrudRepository<PrmTilesEntity, Integer> {
	@Query("SELECT p FROM PrmTilesEntity AS p where p.prsAssignmentId.assigment_Id = :assgnmentId and p.status=1")
	List<PrmTilesEntity> findAllByprsAssignmentId(@Param(value = "assgnmentId") int assgnmentId);

	@Query("SELECT p FROM PrmTilesEntity AS p where p.prsAssignmentId.assigment_Id = :assigmentId AND p.tilesId = :tilesId and p.status=1")
	PrmTilesEntity findBytilesIdAndPrsAssignmentId(@Param(value = "assigmentId") Integer assigmentId,
			@Param(value = "tilesId") String tilesId);

	@Transactional

	@Modifying
	@Query("UPDATE PrmTilesEntity pt set pt.status=0 where pt.prsAssignmentId.assigment_Id = :assgnmentId ")
	int updateInActiveTiles(@Param(value = "assgnmentId") int assgnmentId);

	/*
	 * @Transactional
	 * 
	 * @Modifying(clearAutomatically=true)
	 * 
	 * @Query("UPDATE  PrmTilesEntity v SET v.status=0 WHERE v.prsAssignmentId.assigment_Id = :assigmentId and v.tilesId = : newTiles"
	 * ) void updateToInActive( @Param(value="assigmentId")Integer
	 * assigmentId,@Param(value="newTiles")int newTiles);
	 */

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("update  PrmTilesEntity v set v.tilesId =:tiles where v.prsAssignmentId.assigment_Id = :assgnmentId"
	 * ) void saveTiles(@Param(value="tiles")int
	 * tiles, @Param(value="assigmentId")Integer assigmentId);
	 */
}
