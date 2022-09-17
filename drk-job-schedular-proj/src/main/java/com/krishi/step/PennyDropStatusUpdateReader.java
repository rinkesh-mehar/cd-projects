package com.krishi.step;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.model.PennyDrop;
import com.krishi.service.PennyDropServiceImpl;

public class PennyDropStatusUpdateReader implements ItemReader<List<PennyDrop>>{

	
	private static final Logger LOGGER = LogManager.getLogger(PennyDropStatusUpdateReader.class);

	
	@Autowired
	private PennyDropServiceImpl pennyService;
	
	/*pick penny drop response file and update into the database*/
	@Override
	public List<PennyDrop> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		pennyService.updateBankStatus();
		
		LOGGER.info("PennyDrop Status Update Reader Successful ");
		return null;
	}

}
