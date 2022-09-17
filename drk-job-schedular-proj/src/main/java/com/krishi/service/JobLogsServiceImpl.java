package com.krishi.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.JobLogs;
import com.krishi.repository.JobLogsRepository;

//@Service
public class JobLogsServiceImpl implements JobLogsService{

	@Autowired
	JobLogsRepository jobLogsRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveJobLogs(String jobError) {
		// TODO Auto-generated method stub
		JobLogs jobLogs=new JobLogs();
		jobLogs.setCreatedDate(new Date(System.currentTimeMillis()));
		jobLogs.setErrorDetails(jobError);
		jobLogsRepository.save(jobLogs);
	}

}
