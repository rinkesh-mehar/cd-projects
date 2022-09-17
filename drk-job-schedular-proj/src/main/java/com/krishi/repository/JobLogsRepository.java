package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import com.krishi.entity.JobLogs;

public interface JobLogsRepository  extends JpaRepository<JobLogs, Integer> {

}
