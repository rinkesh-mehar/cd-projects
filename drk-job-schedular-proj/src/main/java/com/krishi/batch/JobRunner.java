package com.krishi.batch;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.krishi.entity.StaticData;
import com.krishi.repository.StaticDataRepository;
import com.krishi.service.JobExecutionService;

@Component
@RestController
@RequestMapping("job")
public class JobRunner {

	private static final Logger LOGGER = LogManager.getLogger(JobRunner.class);

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("sendEmail")
	Job job;

/*	@Autowired
	@Qualifier("cropdatasynch")
	private Job datasynchjob;*/

	@Autowired
	@Qualifier("sendSms")
	private Job sendSmsJob;

	@Autowired
	@Qualifier("userValidation")
	private Job userValidationJob;

	@Autowired
	@Qualifier("unlockUserJob")
	private Job unlockUserJob;

	@Autowired
	@Qualifier("fileTransffer")
	private Job fileTransferJob;

	@Autowired
	@Qualifier("taskGeneratorJob")
	private Job taskGeneratorJob;

	@Autowired
	@Qualifier("rights")
	private Job rightsJob;

	@Autowired
	@Qualifier("taskPending")
	private Job taskPendingJob;

	@Autowired
	@Qualifier("pennyDropStatusUpdate")
	private Job pennyDropStatusUpdateJob;

	/*@Autowired
	@Qualifier("androidSyncJob")
	private Job androidSyncJob;*/

	@Autowired
	@Qualifier("simpleNdviSyncJob")
	private Job simpleNdviSyncJob;

	@Autowired
	@Qualifier("flsTaskJob")
	private Job flsTaskJob;

	@Autowired
	@Qualifier("gstmDataProducerJob")
	private Job gstmDataProducerJob;

	@Autowired
	private JobExecutionService jobExecutionService;

	@Autowired
	private StaticDataRepository staticDataRepository;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * @Autowired JobLogsService jobLogsService;
	 */

	private static final String sendEmail = "SENDEMAIL";
	private static final String getMasterDataSynch = "GETMASTERDATASYNCH";
	private static final String sendSms = "SENDSMS";
	private static final String validateUser = "VALIDATEUSER";
	private static final String checkUrl = "CHECKURL";
	private static final String unlockUser = "UNLOCKUSER";
	private static final String fileTransfer = "FILETRANSFFER";
	private static final String taskCreater = "TASKCREATER";
	private static final String mbep = "MBEP";
	private static final String rights = "RIGHT";
	private static final String taskPending = "TASKPENDING";
	private static final String pennyDropStatus = "PENNYDROPSTATUSUPDATE";
	private static final String androidSync = "ANDROIDSYNC";
	private static final String gstmDataProducer = "GSTMDATAPRODUCER";
	private static final String flsTask = "FLSTASK";
	private static final String simpleNdviSync = "SIMPLENDVISYNC";


	/*Pick Email from email table and send to respective user*/
	@Scheduled(cron = "#{@getSendEmailCronValue}")
	public void perform() {

		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();

		try {
			jobLauncher.run(job, params);
			jobExecutionService.saveExecutionDatails(sendEmail);

			LOGGER.info("INFO: job save sendEmail successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();

			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*call masterdata api and update the respective master data table*/
	/*@Scheduled(cron = "#{@getSynchMasterData}")
	public void cropDataApi() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();

		try {
			jobLauncher.run(datasynchjob, params);
			jobExecutionService.saveExecutionDatails(getMasterDataSynch);
			LOGGER.info("INFO: job save getMasterDataSynch successfully");
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}*/

	/*pick sms from sms table and send to respective user*/
	/*@Scheduled(cron = "#{@getSendSmsCronValue}")
	public void sendSmsJobRunner() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(sendSmsJob, params);
			jobExecutionService.saveExecutionDatails(sendSms);
			LOGGER.info("INFO: job save sendSms successfully");
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}*/

	@Scheduled(cron = "#{@getUserValidationCronValue}")
	public void userValidationJobRunner() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(userValidationJob, params);
			jobExecutionService.saveExecutionDatails(validateUser);
			LOGGER.info("INFO: job  validateUser successfully");
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	@Scheduled(cron = "#{@getCheckUrlCronValue}")
	public void checkUrlJobRunner() {
		try {
			StaticData data = staticDataRepository.findByKey("checkUrl");
			if (data != null && data.getValue() != null && !data.getValue().isBlank()) {
				restTemplate.getForObject(data.getValue(), String.class);
			}
			jobExecutionService.saveExecutionDatails(checkUrl);
			LOGGER.info("INFO: job  checkUrl successfully");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*Unlock locked user*/
	@Scheduled(cron = "#{@getUnlockUserCronValue}")
	public void unlockUserJobRunner() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(unlockUserJob, params);
			jobExecutionService.saveExecutionDatails(unlockUser);
			LOGGER.info("INFO: job  unlockUser successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*pick data from farmer bank account table and generate penny drop file*/
	@Scheduled(cron = "#{@getFileTransfer}")
	public void fileTransffer() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(fileTransferJob, params);
			jobExecutionService.saveExecutionDatails(fileTransfer);
			LOGGER.info("INFO: job  fileTransfer successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*Generate task in the task table for newly created farmer*/
	/*@Scheduled(cron = "#{@getTaskCreaterCronValue}")
	public void taskGeneratorJob() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(taskGeneratorJob, params);
			jobExecutionService.saveExecutionDatails(taskCreater);
			LOGGER.info("INFO: job  taskCreater successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}*/


	/* Send verified rights to blockchain*/
//	@Scheduled(cron = "#{@getRightsTransffer}")
	@RequestMapping(path = "/right-transfer", method = RequestMethod.GET, produces = "application/json")
	public void rightsTransffer() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(rightsJob, params);
			jobExecutionService.saveExecutionDatails(rights);
			LOGGER.info("INFO: job  rights successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*Validate the pending task and send back to respective user*/
	/*@Scheduled(cron = "#{@getTaskPendingCronValue}")
	public void taskPending() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(taskPendingJob, params);
			jobExecutionService.saveExecutionDatails(taskPending);
			LOGGER.info("INFO: job  taskPending successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}*/

	/*pick penny drop response file and update the database*/
	@Scheduled(cron = "#{@getPennyDropStatusUpdateCronValue}")
	public void pennyDropStatusUpdate() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(pennyDropStatusUpdateJob, params);
			jobExecutionService.saveExecutionDatails(pennyDropStatus);
			LOGGER.info("INFO: job  pennyDropStatus successfully");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// jobLogsService.saveJobLogs(e.getMessage());
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*download and insert simple ndvi info into database*/
	@Scheduled(cron = "#{@getSimpleNdviSyncCronValue}")
	public void simpleNdviSync() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(simpleNdviSyncJob, params);
			jobExecutionService.saveExecutionDatails(simpleNdviSync);
			LOGGER.info("INFO: job  simple ndvi sync successful");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*process json file and update the database*/
	/*@Scheduled(cron = "#{@getAndroidSyncCronValue}")
	public void androidSync() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(androidSyncJob, params);
			jobExecutionService.saveExecutionDatails(androidSync);
			LOGGER.info("INFO: job  android sync successful");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}*/

	/*Priorities the task and insert into fls_task table*/
	@Scheduled(cron = "#{@getFlsTaskCronValue}")
	public void flsTask() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(flsTaskJob, params);
			jobExecutionService.saveExecutionDatails(flsTask);
			LOGGER.info("INFO: job  android sync successful");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

	/*send benchmark spot to gstm api*/
	@Scheduled(cron = "#{@getGstmDataProducerCronValue}")
	public void gstmDataProducer() {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			jobLauncher.run(gstmDataProducerJob, params);
			jobExecutionService.saveExecutionDatails(gstmDataProducer);
			LOGGER.info("INFO: job  gstmDataProducer successful");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("::", e.fillInStackTrace());
		}
	}

}
