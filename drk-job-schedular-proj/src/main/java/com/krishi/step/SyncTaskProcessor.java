package com.krishi.step;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.entity.Email;
import com.krishi.entity.EmailTemplate;
import com.krishi.entity.Farmer;
import com.krishi.entity.FarmerCropInfo;
import com.krishi.entity.JobSyncDetails;
import com.krishi.entity.JobSyncErrorLogs;
import com.krishi.entity.PrsTask;
import com.krishi.entity.Rights;
import com.krishi.entity.StaticData;
import com.krishi.model.EntityModel;
import com.krishi.model.SyncDataModel;
import com.krishi.repository.EmailTemplateRepository;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.JobSyncDetailsRepository;
import com.krishi.repository.RightsRepository;
import com.krishi.repository.StaticDataRepository;

public class SyncTaskProcessor implements Tasklet {
	
	private static final Logger LOGGER = LogManager.getLogger(SyncTaskProcessor.class);

	@Autowired
	private JobSyncDetailsRepository jobSyncDetailsRepository;

	@Autowired
	private StaticDataRepository staticDataRepository;

	@PersistenceContext
	private EntityManager em;

	private StaticData staticData;

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;


//	@Value("${spring.profiles.active}")
	private String env="dev"; //default value

	@Autowired
	private RightsRepository rightRepo;
	
	@Autowired
	private SyncTaskProcessor processor;
	
	@Autowired
	private FarmerRepository farmerRepo;

	/*Process the json file coming from android and insert into database*/
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

		try {
 			staticData = staticDataRepository.findByKey("syncJsonPath");
			StaticData pageSize = staticDataRepository.findByKey("jsonFileSyncLimitPerExecution");
			int limit = 5;
			if(pageSize != null) {
				try {
					limit = Integer.parseInt(pageSize.getValue());
				} catch(Exception e) {
					limit = 5;
				}
			}
			Pageable pageable = PageRequest.of(0, limit);
			List<JobSyncDetails> list = jobSyncDetailsRepository.findByprocessedDateNull(pageable);
			list.forEach(this::execute);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				em.close();
			} catch(Exception e) {}
		}

		return RepeatStatus.FINISHED;
	}

	/*Process the json file coming from android and insert into database*/
	private void execute(JobSyncDetails l) {
		try {
			try {
				SyncDataModel model = getDataModel(l, staticData.getValue());
				if (model != null) {
					processor.saveDataModel(model, l);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e);
				processor.updateErrorStatus(l, e);
			}

		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
		}
	}

	/*update the success status in the database*/
	private void updateSuccessStatus(JobSyncDetails l) {
		l.setProcessedDate(new Timestamp(System.currentTimeMillis()));
		l.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		l.setProcessedStatus((byte)1);
		em.merge(l);
	}
	
	/*update the error status in the database*/
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateErrorStatus(JobSyncDetails l, Exception e) {
		l.setProcessedDate(new Timestamp(System.currentTimeMillis()));
		l.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		l.setProcessedStatus((byte)0);
		em.merge(l);
		JobSyncErrorLogs log = new JobSyncErrorLogs();
		log.setJobSyncDetailsId(l.getId());
		log.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		log.setErrorDetail(e.getMessage());
		em.persist(log);
		sendErrorEmail("AndroidSyncJsonFailed", l.getFileName(), e);
		em.flush();
		em.clear();
	}

	/*process the json file and convert into java object*/
	private SyncDataModel getDataModel(JobSyncDetails details, String fileBasePath) {
		try {
			String basePath = fileBasePath + "/" + details.getUserId();
			File file = new File(basePath, details.getFileName());
			if (!file.exists()) {
				throw new RuntimeException("File not found! : " + details.getFileName());
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(file, SyncDataModel.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*insert or update the json data in the database*/
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveDataModel(SyncDataModel dataModel, JobSyncDetails l) {
			save(dataModel.getCaseCoordinates());
			save(dataModel.getCaseCropInfo());
			save(dataModel.getCaseKml());
			save(dataModel.getCasefieldDetails());
			save(dataModel.getFarmCase());
			save(dataModel.getFarmer());
//			checkexistingFarmer(dataModel.getFarmer(), dataModel.getFarmerCropInfo());
			save(dataModel.getFarmerBankAccount());
			save(dataModel.getFarmerCroping());
			save(dataModel.getFarmerFarm());
			save(dataModel.getFarmerGenInfo());
			save(dataModel.getFarmerKyc());
			save(dataModel.getFarmerLandInfo());
			save(dataModel.getFarmerLoanInfo());
			save(dataModel.getFarmerMachinery());
			save(dataModel.getFarmerPolyhouse());
			save(dataModel.getFarmerinsuranceInfo());
			save(dataModel.getHealthPhotos());
			save(dataModel.getHealthTaskDetails());
			saveRight(dataModel.getRights());
			save(dataModel.getStressTaskDetails());
			save(dataModel.getTask());
			save(dataModel.getTaskHistory());
			save(dataModel.getTaskSpot());
			save(dataModel.getTaskTransaction());
			save(dataModel.getGovtOfficial());
			save(dataModel.getVillage());
			save(dataModel.getVip());
			save(dataModel.getPoi());
			save(dataModel.getVillageTask());
			save(dataModel.getVillageAdditionalInfo());
			save(dataModel.getTaskAerialPhotos());
			save(dataModel.getTaskRecommendation());
			/** added farmer crop info task sync processor - CDT-Ujwal - Start */

			save(dataModel.getFarmerCropInfo());

			/** added farmer crop info task sync processor - CDT-Ujwal - End */
			updateSuccessStatus(l);
	}
	/*insert or update the json data in the database*/
	private <T> void save(List<T> list) {
		if (list != null && list.size() > 0) {
			if(list.get(0).getClass().equals(PrsTask.class)) {
				for(T t: list) {
					Object existRecored = em.find(PrsTask.class, ((PrsTask)t).getTaskId());
					if(existRecored != null) {
						em.merge(t);
					} else {
						em.persist(t);
					}
					em.flush();
				}
			} else {
				for(T t: list) {
					Object existRecored = em.find(list.get(0).getClass(), ((EntityModel)t).getId());
					if(existRecored != null) {
						em.merge(t);
					} else {
						em.persist(t);
					}
					em.flush();
				}
			}
			em.clear();
		}

	}
	
	/** It is check farmer are already registered -CDT-Ujwal*/ 
//	private void checkexistingFarmer(List<Farmer> farmerList, List<FarmerCropInfo> farmerCropInfoList) {
//		if (farmerList != null) {
//			for (Farmer farmer : farmerList) {
//				String farmerId = farmer.getFarmerId();
//				Farmer existFarmer = farmerRepo.findByPrimaryMobile(farmer.getPrimaryMobNumber());
//				if (existFarmer != null) {
//
//					farmer.setFarmerId(existFarmer.getFarmerId());
//
//					if (farmerCropInfoList != null) {
//   
//						for (FarmerCropInfo farmerCropInfo : farmerCropInfoList) {
//
//							if (farmerCropInfo.getFarmerId().equals(farmerId)) {
//
//								farmerCropInfo.setFarmerId(existFarmer.getFarmerId());
//							}
//						}
//						
//					}
//				}	
//			}
//		
//			save(farmerList);
//			save(farmerCropInfoList);
//		}
//	}
	
	/*Update the right record in the database*/
	private void saveRight(List<Rights> list) {
		if(list!=null && list.size()>0) {
		for (Rights right : list) {
			Rights existingRight = rightRepo.findTop1ByIdOrderByVersionNumberDesc(right.getId());
			if (existingRight!=null) {
				
					right.setVersionNumber(existingRight.getVersionNumber()+1);
					rightRepo.saveAndFlush(right);

				
			}else {
				rightRepo.saveAndFlush(right);
			}
		}
		}
	}

	/*send the json exception logs to support team*/
	private void sendErrorEmail(String templateName, String fileName, Exception e) {
		EmailTemplate template = emailTemplateRepository.findByNameAndIsActive(templateName, true);
		if (template != null) {
			StaticData fromemail = staticDataRepository.findByKey("cdtfromemail");
			StaticData supportToEmail = staticDataRepository.findByKey("cdttoemail");
			if (template != null && fromemail != null && supportToEmail != null) {
				String envName = null;

				switch (env) {
				case "prod":
					envName = "Production";
					break;
				case "dev":
					envName = "Development";
					break;
				case "qa":
					envName = "Testing";
					break;
				case "azureuat":
					envName = "UAT";
					break;
				default:
					envName = env;
				}

				Email email = new Email();
				email.setCreatedBy("System");
				email.setBody(template.getBody());
				email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				email.setStatus(1);
				email.setEmailDate(new Timestamp(System.currentTimeMillis()));
				email.setFromAddress(fromemail.getValue());
				email.setFromMailDisplay(fromemail.getValue());
				email.setToAddress(supportToEmail.getValue());
				email.setSubject(template.getSubject());

				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				email.setBody(email.getBody().replace("[timestamp]", FORMAT.format(System.currentTimeMillis())));
				email.setBody(email.getBody().replace("[error]", exceptionAsString));
				email.setBody(email.getBody().replace("[filename]", fileName));
				email.setBody(email.getBody().replace("[env]", envName));
				email.setSubject(email.getSubject().replace("[env]", envName));
				em.persist(email);
			}
		}
	}

	
	
	
}
