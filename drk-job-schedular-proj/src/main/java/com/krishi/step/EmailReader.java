package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.Email;
import com.krishi.repository.EmailRepository;

public class EmailReader implements ItemReader<List<Email>> {
	
	@Autowired
	private EmailRepository emailRepository;
	
	boolean isNew = true;

	/*Read email from email table and send to email sender*/
	@Override
	@Transactional(readOnly = true)
	public List<Email> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if(!isNew) {
			isNew = true;
			return null;
		} else {
			isNew = false;
		}
		
		List<Email> emailList = emailRepository.findNewEmail();
		return emailList;
	}

}
