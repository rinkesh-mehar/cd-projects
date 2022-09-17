package com.krishi.service;

import com.krishi.entity.*;
import com.krishi.model.KafkaRightsModel;
import com.krishi.properties.AppProperties;
import com.krishi.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

/*Kafka response process service*/
@Service
public class KafkaRightsServiceImpl implements KafkaRightsService {
	private static final Logger LOGGER = LogManager.getLogger(KafkaRightsServiceImpl.class);

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private RightsRepository rightsRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private SmsTemplateRepository smsTemplateRepository;

	@Autowired
	private SmsRepository smsRepository;

	@Autowired
	private FarmCaseRepository farmCaseRepository;

	@Autowired
	private SystemEmailService systemEmailService;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;

	/*generate primary key for database*/
	private String generateKey(String entityName) {
		Properties properties = new Properties();
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("entity-code.properties");
			properties.load(resourceStream);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int fixLenght = Integer.parseInt(properties.getProperty("FIX_LENGHT"));
		String entityValue = properties.getProperty(entityName);
		String id = String.valueOf(properties.getProperty("USER_ID"));
		int prefixZero = fixLenght - id.length();
		StringBuffer sb = new StringBuffer(entityValue);
		for (int i = 0; i < prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	@Transactional
	private void saveTask(Integer taskTypeId, String caseId) {
		Task task = new Task();
		String taskId = generateKey("TASK");
		task.setId(taskId);
		task.setAssigneeId(0);
		task.setStatus(0);
		task.setTaskDate(new Date(System.currentTimeMillis()));
		task.setEntityId(caseId);
		task.setEntityTypeId(1);
		task.setTaskTypeId(taskTypeId);
		taskRepository.saveAndFlush(task);
	}

	@Transactional
	private void updateDueAmount(String caseId, KafkaRightsModel model, Integer status) {
		Farmer farmer = farmerRepository.findByCaseId(caseId);
		Optional<FarmCase> farmcase = farmCaseRepository.findById(caseId);
		if (status == 1) {
			farmcase.get().setAmountCollected(model.getSettlementAmount());
			farmCaseRepository.save(farmcase.get());
		}

		else if (status == 2) {
			farmer.setDueAmount(0F);
			farmerRepository.save(farmer);
			if (model.getSettlementAmount() != null) {
				farmcase.get().setAmountCollected(model.getSettlementAmount());
				farmCaseRepository.save(farmcase.get());
			} else if (status == 3) {
				farmcase.get().setAmountCollected(model.getSettlementAmount());
				farmCaseRepository.save(farmcase.get());
			} else if (status == 4) {
				farmcase.get().setAmountCollected(model.getSettlementAmount());
				farmCaseRepository.save(farmcase.get());
			} else if (status == 5) {
				farmcase.get().setAmountCollected(model.getAmountCollected());
				farmCaseRepository.save(farmcase.get());
			}

		}
	}

	private String getPrimaryMobileNo(String caseId) {
		Farmer farmer = farmerRepository.findByCaseId(caseId);

		return farmer.getPrimaryMobNumber();
	}

	@Transactional
	private Rights setteled(KafkaRightsModel model, Rights right) throws CloneNotSupportedException {
		if (model.getDueSettled() == 1) {
			saveTask(20, right.getCaseId());
			right.setAmountCollected(model.getSettlementAmount());
			updateDueAmount(right.getCaseId(), model, model.getDueSettled());
			right.setStatus("LOT_SETTLED");
		} else if (model.getDueSettled() == 2) {
							if (right.getLotId() != null) {
								right.setAmountCollected(model.getSettlementAmount());
								updateDueAmount(right.getCaseId(), model, model.getDueSettled());
								right.setLotId(model.getLotId());
								right.setTransactionId("lotid changed");
								right.setStatus("LOT_CHANGED");
							} else {
								right.setLotId(model.getLotId());
							}
		} else if (model.getDueSettled() == 3) {
			right.setContractedPrice(model.getSettlementAmount());
			right.setStatus("CONTRACTED");
			updateDueAmount(right.getCaseId(), model, model.getDueSettled());
		} else if (model.getDueSettled() == 4) {
			right.setAmountCollected((model.getSettlementAmount()));
			right.setStatus("MATURED");
			updateDueAmount(right.getCaseId(), model, model.getDueSettled());

		} else if (model.getDueSettled() == 5) {

			updateDueAmount(right.getCaseId(), model, model.getDueSettled());

		}

		else {
			saveTask(20, right.getCaseId());
		}
		return right;
	}

	/*process the kafka response and update into database*/
	@Transactional
	@Override
	public void updateRights(KafkaRightsModel model) throws Exception {

		Rights right = rightsRepository.findTop1ByIdOrderByVersionNumberDesc(model.getRightsId());

		if (right == null) {
			throw new RuntimeException("Right not found! For Right Id -> " + model.getRightsId());
		}

		/*
		 * if (model.getErrorCode() != null) { right.setStatus(model.getErrorCode()); }
		 * right.setStatusReceivingDate(new Date(System.currentTimeMillis()));
		 */

		if (model.getErrorCode() != null) {
			switch (model.getErrorCode()) {
			case "NULL_ERROR": {
				Rights newRight = generateNewRight(right, "NULL_ERROR", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}
			case "MISSING_VALUES": {
				Rights newRight = generateNewRight(right, "MISSING_VALUES", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}
			case "CLEARED": {
				Rights newRight = generateNewRight(right, "CLEARED", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}

			case "THRESHOLD_BREACH": {
				saveTask(16, right.getCaseId());
				Rights newRight = generateNewRight(right, "THRESHOLD_BREACH", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}
			case "MBEP_RENEGOTIATION": {
				saveTask(17, right.getCaseId());
//				triggerSms(right, "MbepNegotation");
				Rights newRight = generateNewRight(right, "MBEP_RENEGOTIATION", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}
			case "BUYER_DEFAULT": {
				saveTask(18, right.getCaseId());
				model.setDueSettled(5);
//				triggerSms(right, "BuyerDefault");
				Rights newRight = generateNewRight(right, "BUYER_DEFAULT", model);
				newRight.setAmountCollected(model.getAmountCollected());
//				newRight = setteled(model, newRight);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;
			}
			case "REJECT_EXTRA_YIELD": {
				saveTask(19, right.getCaseId());
//				triggerSms(right, "RejectExtrayield");
				Rights newRight = generateNewRight(right, "REJECT_EXTRA_YIELD", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);

				break;
			}
			/*
			 * case "ONBOARDED": { triggerSms(right.getCaseId(), "RightsOnboarded"); break;
			 * }
			 */

			case "CONTRACTED": {
				model.setDueSettled(3);
				Rights newRights = generateNewRight(right, "CONTRACTED", model);
//				newRights = setteled(model, newRights);
				rightsRepository.saveAndFlush(newRights);
				this.saveEmail(newRights);
				this.saveSms(newRights, model);
				break;
			}
			case "HARVESTED": {
				Rights newRight = generateNewRight(right, "HARVESTED", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;

			}
			case "MATURED": {
				model.setDueSettled(4);
				Rights newRight = generateNewRight(right, "MATURED", model);
//				newRight = setteled(model, newRight);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;

			}
			case "ONBOARDED": {
				Rights newRight = generateNewRight(right, "ONBOARDED", model);
				rightsRepository.saveAndFlush(newRight);
				this.saveEmail(newRight);
				this.saveSms(newRight, model);
				break;

			} default: {
					Rights newRight = generateNewRight(right, model.getErrorCode(), model);
					rightsRepository.saveAndFlush(newRight);
					/**	Send Email For Update In Right Status */
					this.saveEmail(newRight);
					this.saveSms(newRight, model);
				}

			}
		}
		else {
			Rights newRight = null;
			newRight = generateNewRight(right, model.getErrorCode(), model);
			/*
			if (model.getDueSettled() == 1) {
				newRight = generateNewRight(right, "LOT_SETTLED");
				this.sendEmail(newRight);
			} else if (model.getDueSettled() == 2) {
				newRight = generateNewRight(right, "IN_LOT");
				this.sendEmail(newRight);
			} else if (model.getDueSettled() == 3) {
				newRight = generateNewRight(right, "CONTRACTED");
				this.sendEmail(newRight);
			} else if (model.getDueSettled() == 4) {
				newRight = generateNewRight(right, "MATURED");
				this.sendEmail(newRight);
			}
			*/

//			newRight = setteled(model, newRight);
			rightsRepository.saveAndFlush(newRight);
//
//			/**	Send Email For Update In Right Status */
//			this.sendEmail(newRight);
		}

	}

	private void triggerSms(Rights right, String templateName) {
		SmsTemplate message = smsTemplateRepository.findByNameAndIsActive(templateName, true);
		String smsMessage = message.getMessage();

		if ("Contracted".equals(templateName)) {
			smsMessage = java.text.MessageFormat.format(message.getMessage(), right.getId(),
					right.getContractedPrice());
		}
		if ("onboarded".equals(templateName)) {
			smsMessage = java.text.MessageFormat.format(message.getMessage(), right.getId());
		}

		Sms sms = new Sms();
		sms.setMobileNumber(getPrimaryMobileNo(right.getCaseId()));
		sms.setStatus(false);
		sms.setCreatedBy("System");
		sms.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		if (message != null) {
			sms.setMessage(smsMessage);
			smsRepository.save(sms);
		}
	}

	@Transactional
	Rights generateNewRight(Rights right, String status, KafkaRightsModel model) {

		try {
			Rights newright = right.clone();

			// newright.setId(generateKey("RIGHT"));
			newright.setStatusReceivingDate(new Date(System.currentTimeMillis()));
//			if (status.equalsIgnoreCase("ONBOARDED")) {
//				newright.setVersionNumber(right.getVersionNumber() + 2);
//			} else {
			if (model.getLotId() == null){
				newright.setBlockchainStatus("Recieved");
				newright.setStatus(status);
				newright.setVersionNumber(right.getVersionNumber() + 1);
			} else {
				newright.setLotId(model.getLotId());
			}
//			}
			rightsRepository.save(newright);
			return newright;

		} catch (CloneNotSupportedException e) {
			return null;

		}
	}

    public void saveEmail(Rights newRight){

		EmailTemplate template = emailTemplateRepository.findByNameAndIsActive("BlockchainRightUpdate", true);

		/*if (template.getBody() != null && !template.getBody().isEmpty()) {

			String text = template.getBody().replace("<ID>", newRight.getId() == null? "" : newRight.getId())
					.replace("<Status>", newRight.getStatus() == null? "" : newRight.getStatus())
					.replace("<Stage>", newRight.getStage() == null? "" : newRight.getStage())
					.replace("<LotId>", newRight.getLotId() == null? "" : newRight.getLotId());

			try {
				systemEmailService.sendSimpleMessage(appProperties.getMailTo(), "Blockchain Right Update!", text);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("exception in sending email for right insert");
			}
		} else {
			LOGGER.info("Mail body is empty");
		}*/

		if (template.getBody() != null && !template.getBody().isEmpty()) {
			Email email = new Email();
			email.setCreatedBy("System");
			email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			email.setStatus(1);
			email.setEmailDate(new Timestamp(System.currentTimeMillis()));
			email.setSubject(template.getSubject());

			email.setBody(template.getBody().replace("<ID>", newRight.getId() == null? "" : newRight.getId())
					.replace("<Status>", newRight.getStatus() == null? "" : newRight.getStatus())
					.replace("<Stage>", newRight.getStage() == null? "" : newRight.getStage())
					.replace("<LotId>", newRight.getLotId() == null? "" : newRight.getLotId()));

			emailRepository.saveAndFlush(email);
		}
	}

	public void saveSms(Rights right, KafkaRightsModel model){

		SmsTemplate template = smsTemplateRepository.findByNameAndIsActive("RightStatus", true);

		if (template.getName() != null && !template.getMessage().isEmpty() && right.getCaseId() != null) {

			Sms sms = new Sms();
			sms.setMobileNumber(getPrimaryMobileNo(right.getCaseId()));
			sms.setStatus(false);
			sms.setCreatedBy("System");
			sms.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			if (template.getMessage() != null) {
				sms.setMessage(template.getMessage()
						.replace("[rightId]", right.getId())
//						.replace("[fromStatus]", right.getStatus())
						.replace("[toStatus]", model.getErrorCode()));
				smsRepository.save(sms);
			}
		}
	}
}
