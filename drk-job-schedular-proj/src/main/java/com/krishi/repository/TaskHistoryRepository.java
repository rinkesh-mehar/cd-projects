package com.krishi.repository;

import com.krishi.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cropdata-ujwal
 * @package com.krishi.repository
 * @date 18/10/21
 * @time 1:21 PM
 */
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, String>
{
}
