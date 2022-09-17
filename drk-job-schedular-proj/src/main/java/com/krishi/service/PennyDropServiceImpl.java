package com.krishi.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.dao.FarmerBakAccountDao;
import com.krishi.dao.Rightsdao;
import com.krishi.entity.Email;
import com.krishi.entity.EmailTemplate;
import com.krishi.entity.Farmer;
import com.krishi.entity.FarmerBankAccount;
import com.krishi.entity.FarmerKyc;
import com.krishi.entity.Rights;
import com.krishi.entity.StaticData;
import com.krishi.model.PennyDrop;
import com.krishi.repository.EmailRepository;
import com.krishi.repository.EmailTemplateRepository;
import com.krishi.repository.FarmerBankAccountRepository;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.StaticDataRepository;

@Service
public class PennyDropServiceImpl {

	@Autowired
	private Rightsdao dao;

	@Autowired
	private FarmerBankAccountRepository farmerBankAccountRepository;

	@Autowired
	private StaticDataRepository staticDataRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private FarmerBakAccountDao bankdao;
	
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
//	@Value("${spring.profiles.active}")
	private String env = "dev";
	
	
	
/*read all newly created bank info from database and create pennydrop file*/
	public List<PennyDrop> pennyDrop() {

		List<PennyDrop> drops = new ArrayList<PennyDrop>();
		List<FarmerBankAccount> farmerbank = farmerBankAccountRepository.findForpennyDrop();
		StaticData instrumentamount = staticDataRepository.findByKey("InstrumentAmount");
		StaticData debitAccontNumber = staticDataRepository.findByKey("DebitAccountNumber");
		StaticData creditNarration = staticDataRepository.findByKey("CreditNarration");
		StaticData debitNarration = staticDataRepository.findByKey("DebitNarration");
		StaticData ifscValidation = staticDataRepository.findByKey("IfscValidation");
		StaticData beneficaryBankcode = staticDataRepository.findByKey("BeneficaryBankCode");
		StaticData basePath = staticDataRepository.findByKey("PennydropBasePath");
		StaticData fileName = staticDataRepository.findByKey("PennyDropFileName");
		StaticData paymentProductCode = staticDataRepository.findByKey("PaymentProductCode");
		
		
		File file = new File(basePath.getValue(), "Failure");
		
		if(file.exists()) {
			StringBuilder builder = new StringBuilder();
			int i = 0;
			for(File f : file.listFiles()) {
				if(f.isFile() && f.getName().startsWith(fileName.getValue())) {
					++i;
					builder.append("\n").append(i).append(". ").append(f.getName());
				}
			}
			if(builder.length() > 0) {
				sendErrorEmail(builder.toString(), "pennydropFailureFileEmail");
			}
			
		}

		for (FarmerBankAccount bankAccount : farmerbank) {
			PennyDrop pennyDrop = new PennyDrop();
			bankAccount.setIsPennydropped(true);
			bankAccount.setPennydropDate(new Date());

			farmerBankAccountRepository.save(bankAccount);
			if (bankAccount.getCaseId() != null){

			}
			//FarmerBankAccount bankdetails = dao.findBankdetails(bankAccount.getFarmerId());

			pennyDrop.setCdtTransactionId(bankAccount.getId());
			/*TODO: replace farmerId to caseId*/
			pennyDrop.setUserEntityCode(bankAccount.getCaseId());
			pennyDrop.setPaymentProductCode(paymentProductCode.getValue());
			Farmer farmers = farmerRepository.findFarmerByCaseId(bankAccount.getCaseId());
			pennyDrop.setFarmerName(farmers.getFarmerName());
			pennyDrop.setInstrumentAmount(instrumentamount.getValue());
			pennyDrop.setPaymentInstructionDate(convertDateFormat());

			pennyDrop.setBeneficiaryBankAccountNumber(bankAccount.getAccNumber());
			pennyDrop.setBeneficaryIFSC(bankAccount.getIfsc());
			pennyDrop.setDebitAccountNo(debitAccontNumber.getValue());
			pennyDrop.setCreditNarration(creditNarration.getValue());
			pennyDrop.setDebitNarration(debitNarration.getValue());
			pennyDrop.setIfscValidation(ifscValidation.getValue());
			pennyDrop.setBeneficaryBankCode(beneficaryBankcode.getValue());
			pennyDrop.setFarmerMobileNumber("" + farmers.getPrimaryMobNumber());
			pennyDrop.setFarmeremailId("");

			if (bankAccount.getPennydropDummy() != null) {
				pennyDrop.setFarmerpennyDummy(bankAccount.getPennydropDummy());
			} else {
				pennyDrop.setFarmerpennyDummy("");
			}
			
			Rights rightsId = dao.findRightsId(farmers.getFarmerId());
			if(rightsId !=null) {
				pennyDrop.setRightId(rightsId.getId());
			} else {
				pennyDrop.setRightId("");
			}
			
			FarmerKyc farmerKyc = dao.findFarmerKyc(farmers.getFarmerId());
			pennyDrop.setFarmerAddress(farmerKyc.getPermanentAddress());
			pennyDrop.setAdharNumber(farmerKyc.getDocNumber());
			pennyDrop.setFarmerPANNumber(farmerKyc.getDocNumber());
			if(basePath.getValue().endsWith("/") || basePath.getValue().endsWith("\\")) {
				pennyDrop.setBasePath(basePath.getValue());
			} else {
				pennyDrop.setBasePath(basePath.getValue()+"/");
			}
			
			pennyDrop.setFileName(fileName.getValue());
			// --------------------------

			drops.add(pennyDrop);

		}

		return drops;
	}

	/*format date*/
	private String convertDateFormat() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		return strDate;
	}
	
	
//PennyDropStatusUpdate
	public void updateBankStatus() {
		StaticData bankresponsefilepath = staticDataRepository.findByKey("BankResponseFilePath");
		StaticData bankresponsefileNamePrefix = staticDataRepository.findByKey("BankResponseFileNamePrefix");
		StaticData BankResponseBackupPath = staticDataRepository.findByKey("BankResponseBackupPath");
		

		File folder = new File(bankresponsefilepath.getValue());
		File[] listOfFiles = folder.listFiles();
		for (File originalFile : listOfFiles) {
			if (originalFile.isFile() && originalFile.getName().startsWith(bankresponsefileNamePrefix.getValue())) {
			File backupFile = new File(BankResponseBackupPath.getValue(), originalFile.getName());
		try {
				readFile(originalFile, bankresponsefilepath, bankresponsefileNamePrefix);
				deleteResponseFile(originalFile, backupFile);
		} catch (Exception exp) {
			deleteResponseFile(originalFile, backupFile);
			sendErrorEmail(backupFile.getAbsolutePath(), "pennydropErrorFileEmail");
			exp.printStackTrace();
		}
		}
		}
	}
	
	/*Remove pennydrop response file after process*/
	private void deleteResponseFile(File originalFile, File backupFile) {
		try {
			if(!backupFile.exists()) {
				Files.copy(Path.of(originalFile.getPath()), Path.of(backupFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
			}
			originalFile.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
/*read pennydrop file*/
	@SuppressWarnings("resource")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void readFile(File file, StaticData bankresponsefilepath, StaticData bankresponsefileNamePrefix) throws Exception {
		String strLine;
			try(BufferedReader br = new BufferedReader(new FileReader(new File(bankresponsefilepath.getValue() , file.getName())))) {
			int i = 0;
			while ((strLine = br.readLine()) != null) {
				++i;
				parseData(strLine, i);
				System.err.println(strLine);

			}
		} catch(Exception e) {
			throw e;
		}
	}

	/*read pennydrop file and update in the database*/
	private void parseData(String str, int i) {
		String[] data = str.split("\\|");
		FarmerBankAccount account=farmerBankAccountRepository.findAllById(data[0]);
		if(account != null && data[17] != null && (data[18] != null || data[17].equalsIgnoreCase("Cancelled"))) {
			account.setCmsReferenceNo(data[16]);
			//account.setImpsStatus(data[17]);
			if(!data[17].equalsIgnoreCase("Cancelled")) {
				Date currentDate = convertDateFromString(data[18]);
				account.setLiquidationDate(new java.sql.Date(currentDate.getTime()));
			}
			account.setPennydropRemarks(data[19]);
			account.setPennydropStatus(data[17]);
			bankdao.updatePennyDropStatus(account);
		} else {
			throw new RuntimeException("error at line no : "+i);
		}
	}

	private Date convertDateFromString(String formateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date scheduleddate = null;
		try {
			scheduleddate = formatter.parse(formateDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return scheduleddate;
	}
	
	/*send error email to support team*/
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void sendErrorEmail(String fileNames, String templateName) {
		EmailTemplate template = emailTemplateRepository.findByNameAndIsActive(templateName, true);
		if(template != null) {
		StaticData fromemail = staticDataRepository.findByKey("cdtfromemail");
		StaticData supportToEmail = staticDataRepository.findByKey("cdttoemail");
		if(template != null && fromemail != null && supportToEmail != null) {
		String envName = null;
		
		switch(env) {
		case "prod":envName="Production";break;
		case "dev":envName="Development";break;
		case "qa":envName="Testing";break;
		case "azureuat":envName="UAT";break;
		default:envName = env;
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
		
		email.setBody(email.getBody().replace("[filename]", fileNames));
		email.setBody(email.getBody().replace("[env]", envName));
		email.setSubject(email.getSubject().replace("[env]", envName));
			emailRepository.saveAndFlush(email);
		}
		}
	
}
}
