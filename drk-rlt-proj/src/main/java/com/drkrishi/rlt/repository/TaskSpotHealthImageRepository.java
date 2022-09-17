package com.drkrishi.rlt.repository;

import com.drkrishi.rlt.entity.TaskSpotHealthImage;
import com.drkrishi.rlt.model.vo.TaskSpotHealthImageVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author RinkeshKM
 * @Date 05/08/21
 */

@Repository
public interface TaskSpotHealthImageRepository extends JpaRepository<TaskSpotHealthImage, String> {

    @Query(value = "select\n" +
            "group_concat(if(tshi.side = 'F' /*and `tshi`.`status` = 'Verified'*/, tshi.image_url, null)) AS frontPhoto,\n" +
            "group_concat(if(tshi.side = 'L' /*and `tshi`.`status` = 'Verified'*/, tshi.image_url, null)) AS leftPhoto,\n" +
            "group_concat(if(tshi.side = 'R' /*and `tshi`.`status` = 'Verified'*/, tshi.image_url, null)) AS rightPhoto\n" +
            "from task_spot_health_image tshi where tshi.task_spot_id = ?1", nativeQuery = true)
    List<TaskSpotHealthImageVo> getHealthDetails(String spotId);
}
