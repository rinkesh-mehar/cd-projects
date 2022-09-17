package com.krishi.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.JobExecutionDetails;
import com.krishi.entity.ScheduleJobmaster;
import com.krishi.repository.JobExecutionDetailsRepository;
import com.krishi.repository.ScheduleJobmasterRepository;
@Service
public class JobExecutionServiceImpl  implements JobExecutionService{

	@Autowired
	JobExecutionDetailsRepository jobExecutionDetailsRepository;
	@Autowired
	ScheduleJobmasterRepository scheduleJobmasterRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveExecutionDatails(String jobName) {
		JobExecutionDetails jobExecutionDetails=new JobExecutionDetails();
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(jobName);
		jobExecutionDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		jobExecutionDetails.setExecutionDateTime(new Timestamp(System.currentTimeMillis()));
		jobExecutionDetails.setJobId(scheduleJobmaster.getId());
		jobExecutionDetailsRepository.save(jobExecutionDetails);
	}

}
