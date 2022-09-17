package com.drkrishi.iqa.dao.repository;

import com.drkrishi.iqa.entity.TaskSpotHealthImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotHealthImageRepository extends JpaRepository<TaskSpotHealthImage, String> {
    @Query(value = "select if((select count(status) from task_spot_health_image where status= 'Pending' and task_spot_id = ?1) > 0 , true, false) as status\n" +
            "from task_spot_health_image tshi\n" +
            "where task_spot_id = ?1 group by task_spot_id", nativeQuery = true)
    int checkHealthImages(String spotId);
}
