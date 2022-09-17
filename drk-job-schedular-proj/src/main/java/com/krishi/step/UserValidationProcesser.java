package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.krishi.entity.DrKrishiUserCredientials;

public class UserValidationProcesser implements ItemProcessor<List<DrKrishiUserCredientials>, List<DrKrishiUserCredientials>>{
	
//	@Autowired
//	private SystemEmailService systemEmailService;
//	
//	@Autowired
//	private DrKrishiUsersRepository drKrishiUsersRepository;
	
	//private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");

	@Override
	public List<DrKrishiUserCredientials> process(List<DrKrishiUserCredientials> item) throws Exception {
		
//		Calendar startCalendar = Calendar.getInstance();
//	    startCalendar.setTime(new java.util.Date());
//	    startCalendar.set(Calendar.HOUR, 0);
//	    startCalendar.set(Calendar.MINUTE, 0);
//	    startCalendar.set(Calendar.SECOND, 0);
//	    
//		for(DrKrishiUserCredientials user: item) {
//			if(user.getPasswordResetDate().after(new Timestamp(startCalendar.getTimeInMillis()))) {
//				user.setSystemPasswordExpired(1);
//			} else {
//				//send mail
//				DrKrishiUsers drKrishiUser = drKrishiUsersRepository.findById(user.getUserId()).get();
//				Email email = new Email();
//				email.setToAddress(drKrishiUser.getEmailAddress());
//				Map<String, String> placeholders = new HashMap<String, String>();
//				placeholders.put("name", drKrishiUser.getFirstName());
//				placeholders.put("expiredate", dateFormat.format(user.getPasswordResetDate()));
//				systemEmailService.saveEmail("PASSWORDEXPIRE", email, placeholders);
//			}
//		}
		for(DrKrishiUserCredientials user:item) {
			user.setIsForcedPwdChange(1);
		}
		return item;
	}

}
