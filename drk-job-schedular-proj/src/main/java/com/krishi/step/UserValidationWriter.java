package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.DrKrishiUserCredientials;
import com.krishi.repository.DrKrishiUserCredientialsRepository;

public class UserValidationWriter implements ItemWriter<List<DrKrishiUserCredientials>>{
	
	@Autowired
	private DrKrishiUserCredientialsRepository drKrishiUserCredientialsRepository;

	@Override
	public void write(List<? extends List<DrKrishiUserCredientials>> items) throws Exception {
		
		for(List<DrKrishiUserCredientials> users : items) {
			updateUser(users);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void updateUser(List<DrKrishiUserCredientials> users) {
		
		for(DrKrishiUserCredientials user: users) {
				drKrishiUserCredientialsRepository.saveAndFlush(user);
		}
	}



}
