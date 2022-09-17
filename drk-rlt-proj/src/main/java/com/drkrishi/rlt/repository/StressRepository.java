package com.drkrishi.rlt.repository;

import com.drkrishi.rlt.entity.Stress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StressRepository extends JpaRepository<Stress, Integer> {

//    @Query(value = "select s.id as sI, s.name as sN, st.id as sTI, st.name as sTN from Stress as s\n" +
//            "    inner join stress_type as st on s.stress_type_id = st.id where st.name = 'Disease Infection'", nativeQuery = true)
//    List<StressDetailsVO> getStressDetail();


}
