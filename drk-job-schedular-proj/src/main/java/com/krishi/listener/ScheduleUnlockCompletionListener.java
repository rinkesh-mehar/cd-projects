package com.krishi.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class ScheduleUnlockCompletionListener extends JobExecutionListenerSupport {
	
	private static final Logger LOGGER = LogManager.getLogger(ScheduleUnlockCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("Schedule unlock service completed successfully.");
		}
	}
}
