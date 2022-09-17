package com.krishi.config;

import java.util.List;

import com.krishi.entity.*;
import com.krishi.model.ViewRightsModel;
import com.krishi.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.krishi.listener.ScheduleUnlockCompletionListener;
import com.krishi.model.PennyDrop;
import com.krishi.repository.ScheduleJobmasterRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private static final String sendEmail = "SENDEMAIL";
	private static final String getMasterDataSynch = "GETMASTERDATASYNCH";
	private static final String sendSms = "SENDSMS";
	private static final String validateUser = "VALIDATEUSER";
	private static final String checkUrl = "CHECKURL";
	private static final String unlockUser = "UNLOCKUSER";
	private static final String fileTransffer="FILETRANSFFER";
	private static final String taskCreater = "TASKCREATER";
	private static final String rights = "RIGHT";
	private static final String taskPending = "TASKPENDING";
	private static final String pennyDropStatusUpdate ="PENNYDROPSTATUSUPDATE";
	private static final String androidSync = "ANDROIDSYNC";
	private static final String gstmDataProducer = "GSTMDATAPRODUCER";
	private static final String flsTask = "FLSTASK";
	private static final String simpleNdviSync = "SIMPLENDVISYNC";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ScheduleJobmasterRepository scheduleJobmasterRepository;

	@Bean
	public String getSendEmailCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(sendEmail);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

//	@Bean
//	public String getSynchMasterData() {
//		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(getMasterDataSynch);
//		// return "0 */2 * * * ?";
//		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
//	}

/*	@Bean
	public String getSendSmsCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(sendSms);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}*/

	@Bean
	public String getUserValidationCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(validateUser);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	@Bean
	public String getCheckUrlCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(checkUrl);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	@Bean
	public String getUnlockUserCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(unlockUser);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	@Bean
	public String getFileTransfer() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(fileTransffer);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	/*@Bean
	public String getTaskCreaterCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(taskCreater);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}*/

	/*@Bean
	public String getTaskPendingCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(taskPending);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}
*/
	@Bean
	public String getPennyDropStatusUpdateCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(pennyDropStatusUpdate);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	/*@Bean
	public String getAndroidSyncCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(androidSync);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}*/

	@Bean
	public String getSimpleNdviSyncCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(simpleNdviSync);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	@Bean
	public String getFlsTaskCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(flsTask);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}

	@Bean
	public String getGstmDataProducerCronValue() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(gstmDataProducer);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";
	}
	/***
	 * Cropdata master synching
	 *
	 * @return
	 */

	/*@Bean
	public MasterDataSyncProcessor getMasterDataSyncProcessor() {
		return new MasterDataSyncProcessor();
	}*/

	/*@Bean(name = "cropdatasynch")
	public Job cropdataApiJob() {
		return jobBuilderFactory.get("cropdatasynchJob").incrementer(new RunIdIncrementer())
				.flow(cropdataApiJobStep()).end().build();
	}*/

	/*@Bean
	public Step cropdataApiJobStep() {
		  return stepBuilderFactory.get("cropdatasync").tasklet(getMasterDataSyncProcessor()).build();
	}*/

	// ends crop data synch


	//TaskGenerator
	@Bean
	public TaskGeneratorReader getTaskGeneratorReader() {
		return new TaskGeneratorReader();
	}

	@Bean
	public TaskGeneratorWriter getTaskGeneratorWriter() {
		return new TaskGeneratorWriter();
	}
	@Bean(name = "taskGeneratorJob")
	public Job taskGeneratorJob() {
		return jobBuilderFactory.get("taskGeneratorJob").incrementer(new RunIdIncrementer())
				.flow(taskGeneratorStep()).end().build();
	}

	@Bean
	public Step taskGeneratorStep() {
		return stepBuilderFactory.get("taskGeneratorStep").<List<Task>, List<Task>>chunk(1)
				.reader(getTaskGeneratorReader())
				.writer(getTaskGeneratorWriter()).build();
	}
	//TaskGenerator

	@Bean
	FileTransfferReader getFileTransffer() {
		return new FileTransfferReader();
	}

	@Bean
	public FileTransfferWritter getFileWriter() {
		return new FileTransfferWritter();
	}


	@Bean(name = "fileTransffer")
	public Job fileTransfferJob() {
		return jobBuilderFactory.get("fileTransffer").incrementer(new RunIdIncrementer())
				.flow(fileTransfferStep()).end().build();
	}

	@Bean
	public Step fileTransfferStep() {
		return stepBuilderFactory.get("fileTransffer_1").<List<PennyDrop>, List<PennyDrop>> chunk(1)
				.reader(getFileTransffer()).writer(getFileWriter()).build();
	}


	//IcICI bank file transffer




	//File Transsfer ends here

	@Bean
	public EmailReader getReader() {
		return new EmailReader();
	}

	@Bean
	EmailSender getSender() {
		return new EmailSender();
	}

	@Bean
	public EmailWriter getWriter() {
		return new EmailWriter();
	}

	@Bean
	public JobExecutionListener emailSendListener() {
		return new ScheduleUnlockCompletionListener();
	}

	@Bean(name = "sendEmail")
	public Job sendEmailJob() {
		return jobBuilderFactory.get("sendEmailJob").incrementer(new RunIdIncrementer()).listener(emailSendListener())
				.flow(sendEmailStep1()).end().build();
	}

	@Bean
	public Step sendEmailStep1() {
		return stepBuilderFactory.get("sendEmailStep").<List<Email>, List<Email>>chunk(1).reader(getReader())
				.processor(getSender()).writer(getWriter()).build();
	}


	//Send sms job
	@Bean
	public SmsReader getSmsReader() {
		return new SmsReader();
	}

	@Bean
	public SmsSender getSmsSender() {
		return new SmsSender();
	}

	@Bean
	public SmsWriter getSmsWriter() {
		return new SmsWriter();
	}
	@Bean(name = "sendSms")
	public Job sendSmsJob() {
		return jobBuilderFactory.get("sendSmsJob").incrementer(new RunIdIncrementer())
				.flow(sendSmsStep1()).end().build();
	}

	@Bean
	public Step sendSmsStep1() {
		return stepBuilderFactory.get("sendSmsStep").<List<Sms>, List<Sms>>chunk(1).reader(getSmsReader())
				.processor(getSmsSender()).writer(getSmsWriter()).build();
	}
	//send sms job end


	//validate user
	@Bean
	public UserValidationReader getUserValidationReader() {
		return new UserValidationReader();
	}

	@Bean
	public UserValidationProcesser getUserValidationProcesser() {
		return new UserValidationProcesser();
	}

	@Bean
	public UserValidationWriter getUserValidationWriter() {
		return new UserValidationWriter();
	}
	@Bean(name = "userValidation")
	public Job userValidationJob() {
		return jobBuilderFactory.get("userValidationJob").incrementer(new RunIdIncrementer())
				.flow(userValidationStep()).end().build();
	}

	@Bean
	public Step userValidationStep() {
		return stepBuilderFactory.get("userValidationStep").<List<DrKrishiUserCredientials>, List<DrKrishiUserCredientials>>chunk(1).reader(getUserValidationReader())
				.processor(getUserValidationProcesser()).writer(getUserValidationWriter()).build();
	}
	//validate user


	//unlock user
	@Bean
	public ScheduleUnlockUserReader getScheduleUnlockUserReader() {
		return new ScheduleUnlockUserReader();
	}

	@Bean
	public ScheduleUnlockUserProcessor getScheduleUnlockUserProcessor() {
		return new ScheduleUnlockUserProcessor();
	}

	@Bean
	public ScheduleUnlockUserWriter getScheduleUnlockUserWriter() {
		return new ScheduleUnlockUserWriter();
	}
	@Bean(name = "unlockUserJob")
	public Job unlockUserJob() {
		return jobBuilderFactory.get("unlockUserJob").incrementer(new RunIdIncrementer())
				.flow(unlockUserStep()).end().build();
	}

	@Bean
	public Step unlockUserStep() {
		return stepBuilderFactory.get("unlockUserStep").<List<DrKrishiUsers>, List<DrKrishiUsers>>chunk(1).reader(getScheduleUnlockUserReader())
				.processor(getScheduleUnlockUserProcessor()).writer(getScheduleUnlockUserWriter()).build();
	}
	//unlock usertaskPending

//	
//	@Bean
//	public ResourcelessTransactionManager transactionManager() {
//	    return new ResourcelessTransactionManager();
//	}

	@Bean
	public JobRepository jobRepository() throws Exception {
		ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();
		MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
		mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
		return mapJobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		return simpleJobLauncher;
	}

	/*
	 * @Override public void setDataSource(DataSource dataSource) { }
	 */

	@Bean
	public String getRightsTransffer() {
		ScheduleJobmaster scheduleJobmaster = scheduleJobmasterRepository.findByName(rights);
		return (scheduleJobmaster != null && scheduleJobmaster.getStatus()) ? scheduleJobmaster.getScheduleTime() : "-";


	}


	//Send sms job

	@Bean
	public RightsReader getRightsReader() {
		return new RightsReader();
	}

	@Bean
	public RightsWriter getRightsWriter() {
		return new RightsWriter();
	}

	@Bean(name = "rights")
	public Job sendRightsJob() {
		return jobBuilderFactory.get("Rights").incrementer(new RunIdIncrementer()).flow(sendRightsStep1()).end()
				.build();
	}

	@Bean
	public Step sendRightsStep1() {
		return stepBuilderFactory.get("Rights").<List<ViewRightsModel>, List<ViewRightsModel>>chunk(1).reader(getRightsReader())
				.writer(getRightsWriter()).build();
	}

	//send sms job end

	//task pending
	@Bean
	public TaskPendingReader getTaskPendingReader() {
		return new TaskPendingReader();
	}

	@Bean
	public TaskPendingWriter getTaskPendingWriter() {
		return new TaskPendingWriter();
	}

	@Bean(name = "taskPending")
	public Job taskPending() {
		return jobBuilderFactory.get("taskPending").incrementer(new RunIdIncrementer()).flow(taskPendingStep1()).end()
				.build();
	}

	@Bean
	public Step taskPendingStep1() {
		return stepBuilderFactory.get("TaskPendingStep").<List<Task>, List<Task>>chunk(1).reader(getTaskPendingReader())
				.writer(getTaskPendingWriter()).build();
	}


	// pennyDropstatusUpdate
	@Bean
	public PennyDropStatusUpdateReader getpennyDropstatusUpdateReader() {
		return new PennyDropStatusUpdateReader();
	}

	@Bean
	public PennyDropStatusUpdateWriter getPennyDropStatusUpdateWriter() {
		return new PennyDropStatusUpdateWriter();
	}

	@Bean(name = "pennyDropStatusUpdate")
	public Job pennyDropStatusUpdateWriter() {
		return jobBuilderFactory.get("pennyDropStatusUpdate").incrementer(new RunIdIncrementer()).flow(pennyDropstatusUpdateStep1()).end()
				.build();
	}

	@Bean
	public Step pennyDropstatusUpdateStep1() {
		return stepBuilderFactory.get("pennyDropstatusUpdateStep")
				.<List<PennyDrop>, List<PennyDrop>>chunk(100)
				.reader(getpennyDropstatusUpdateReader())
				.writer(getPennyDropStatusUpdateWriter())
				.build();
	}


	@Bean
	public SyncTaskProcessor getSyncTaskProcessor() {
		return new SyncTaskProcessor();
	}

	/*@Bean(name = "androidSyncJob")
	public Job syncJob() {
		return jobBuilderFactory.get("syncJob").incrementer(new RunIdIncrementer()).flow(syncJobStep()).end()
				.build();
	}*/

/*	@Bean
	public Step syncJobStep() {
		return stepBuilderFactory.get("syncJobStep")
				.tasklet(getSyncTaskProcessor())
				.build();
	}
	*/

	// simple ndvi sync

	@Bean
	public SimpleNdviSyncProcessor getSimpleNdviSyncTaskProcessor() {
		return new SimpleNdviSyncProcessor();
	}

	@Bean(name = "simpleNdviSyncJob")
	public Job simpleNdviSyncJob() {
		return jobBuilderFactory.get("simpleNdviSyncJob").incrementer(new RunIdIncrementer()).flow(simpleNdviSyncJobStep()).end().build();
	}

	@Bean
	public Step simpleNdviSyncJobStep() {
		return stepBuilderFactory.get("simpleNdviSyncJobStep").tasklet(getSimpleNdviSyncTaskProcessor()).build();
	}

	// fls task

	@Bean
	public FlsTaskProcessor getFlsTaskProcessor() {
		return new FlsTaskProcessor();
	}

	@Bean(name = "flsTaskJob")
	public Job flsTask() {
		return jobBuilderFactory.get("flsTaskJob").incrementer(new RunIdIncrementer()).flow(flsTaskStep()).end().build();
	}

	@Bean
	public Step flsTaskStep() {
		return stepBuilderFactory.get("flsTaskJobStep").tasklet(getFlsTaskProcessor()).build();
	}

	@Bean
	public GstmDataProducer getGstmDataProducer() {
		return new GstmDataProducer();
	}

	@Bean(name="gstmDataProducerJob")
	public Job gstmDataProducerJob() {
		return jobBuilderFactory.get("gstmDataProducerJob").incrementer(new RunIdIncrementer())
				.flow(gstmDataProducerStep()).end().build();
	}

	@Bean
	public Step gstmDataProducerStep() {
		return stepBuilderFactory.get("gstmDataProducerStep")
				.tasklet(getGstmDataProducer())
				.build();
	}

}
