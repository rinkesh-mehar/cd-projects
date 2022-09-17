package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.Email;
import com.krishi.repository.EmailRepository;

public class EmailWriter implements ItemWriter<List<Email>>{

	@Autowired
	private EmailRepository emailRepository;

	@Override
	public void write(List<? extends List<Email>> items) throws Exception {
			for(List<Email> emails : items) {
				updateEmail(emails);
			}
	}
	
	/*update the email sent status into the database*/
	@Transactional(propagation = Propagation.REQUIRED)
	private void updateEmail(List<Email> emails) {
		for(Email email:emails) {
			emailRepository.saveAndFlush(email);
		}
	}

}
