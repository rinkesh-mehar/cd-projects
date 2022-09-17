package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.krishi.entity.JobExecutionDetails;

public interface JobExecutionDetailsRepository  extends JpaRepository<JobExecutionDetails, Integer> {

}
