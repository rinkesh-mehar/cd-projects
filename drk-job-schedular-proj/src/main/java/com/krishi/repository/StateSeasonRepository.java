package com.krishi.repository;//package com.krishi.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.QueryByExampleExecutor;
//
//import com.krishi.entity.StateSeason;
//
//public interface StateSeasonRepository
//		extends JpaRepository<StateSeason, Integer>, QueryByExampleExecutor<StateSeason> {
//	
//	
//	  @Query("SELECT id FROM StateSeason t where t.stateId = :stateId AND t.seasonId=:seasonId") 
//	    Optional<Integer> findByStateIdAndSeasonId(@Param("stateId") int stateId,@Param("seasonId") int seasonId);
//
//}
