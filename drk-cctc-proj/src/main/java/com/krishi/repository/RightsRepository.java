package com.krishi.repository;

import com.krishi.entity.Rights;
import com.krishi.entity.RightsCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author RinkeshKM
 * @Date 27/07/21
 */

@Repository
public interface RightsRepository extends JpaRepository<Rights, String> {

}
