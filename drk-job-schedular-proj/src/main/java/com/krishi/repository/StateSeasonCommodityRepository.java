package com.krishi.repository;//package com.krishi.repository;
//
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.krishi.entity.StateSeasonCommodity;
//
//public interface StateSeasonCommodityRepository extends JpaRepository<StateSeasonCommodity, Integer> {
//	
//	@Query("SELECT p.id FROM StateSeason as p WHERE p.stateId =:stateCode AND p.seasonId =:seasonID")
//	public Integer findStateSeasonId(@Param("stateCode") int stateCode ,@Param("seasonID") int seasonId);
//
//	
//	
//		
//}
