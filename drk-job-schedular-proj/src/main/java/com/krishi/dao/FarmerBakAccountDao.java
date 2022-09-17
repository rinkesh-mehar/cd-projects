package com.krishi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krishi.entity.FarmerBankAccount;
import com.krishi.repository.FarmerBankAccountRepository;

@Service
public class FarmerBakAccountDao {

	private static final Logger LOGGER = LogManager.getLogger(FarmerBakAccountDao.class);
	
	@Autowired
	FarmerBankAccountRepository repository;

	public void updatePennyDropStatus(FarmerBankAccount account)

	{
		repository.save(account);
		LOGGER.info("INFO:update of PennyDrop Status  successfully");		
		
	}
}
