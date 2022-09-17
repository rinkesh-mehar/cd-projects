package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.DrKrishiUsers;
import com.krishi.repository.DrKrishiUsersRepository;
import com.krishi.service.SystemEmailService;

public class ScheduleUnlockUserProcessor implements ItemProcessor<List<DrKrishiUsers>, List<DrKrishiUsers>> {
	
	@Autowired
	private DrKrishiUsersRepository drKrishiUsersRepository;

	@Autowired
	private SystemEmailService systemEmailService;
	
	@Override
	public List<DrKrishiUsers> process(List<DrKrishiUsers> item) throws Exception {
		/*
		 * for(DrKrishiUserCredientials user:item) { DrKrishiUsers drKrishiUser =
		 * drKrishiUsersRepository.findById(user.getUserId()).get(); Email email = new
		 * Email(); email.setToAddress(drKrishiUser.getEmailAddress()); Map<String,
		 * String> placeholders = new HashMap<String, String>();
		 * placeholders.put("name", drKrishiUser.getFirstName());
		 * systemEmailService.saveEmail("AUTOUSERUNLOCK", email, placeholders); }
		 */
		return item;
	}

}
