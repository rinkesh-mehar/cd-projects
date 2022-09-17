package com.krishi.batch;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.krishi.service.MasterdataSyncImpl;

@EntityScan(basePackages = { "com.krishi.entity" })
@EnableJpaRepositories(basePackages = { "com.krishi.repository" })
@SpringBootApplication(scanBasePackages = { "com.krishi.entity", "com.krishi.repository", "com.krishi.batch",
		"com.krishi.config", "com.krishi.service", "com.krishi.dao", "com.krishi.controller", "com.krishi.properties"})
@EnableScheduling
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BatchApplication {

	private static final Logger LOGGER = LogManager.getLogger(BatchApplication.class);

	@Autowired
	@Qualifier("sendSms")
	Job datasynchjob;

	@Autowired
	JobLauncher jobLauncher;

	/*
	 * @PostConstruct public void test() { JobParameters params = new
	 * JobParametersBuilder().addString("JobID",
	 * String.valueOf(System.currentTimeMillis())) .toJobParameters(); try {
	 * jobLauncher.run(datasynchjob, params); } catch
	 * (JobExecutionAlreadyRunningException | JobRestartException |
	 * JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
	 * e.printStackTrace(); } }
	 */
	
	
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
		LOGGER.info("Application started");
	}

}
