package com.krishi.service;

import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author cropdata-ujwal
 * @package com.krishi.service
 * @date 17/12/21
 * @time 11:53 AM
 */
public interface SimpleNdviSyncService
{
    public RepeatStatus SimpleNdvISyncProcess();
}
