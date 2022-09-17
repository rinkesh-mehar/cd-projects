package com.krishi.step;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.DrKrishiUserCredientials;
import com.krishi.repository.DrKrishiUserCredientialsRepository;

public class UserValidationReader implements ItemReader<List<DrKrishiUserCredientials>> {
	
	@Autowired
	private DrKrishiUserCredientialsRepository drKrishiUserCredientialsRepository;

	@Override
	@Transactional(readOnly = true)
	public List<DrKrishiUserCredientials> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
	    
	    Calendar endCalendar = Calendar.getInstance();
	    endCalendar.setTime(new java.util.Date());
	    endCalendar.set(Calendar.HOUR, 0);
	    endCalendar.set(Calendar.MINUTE, 0);
	    endCalendar.set(Calendar.SECOND, 0);
	    endCalendar.add(Calendar.MONTH, -3);
	    List<DrKrishiUserCredientials> users = drKrishiUserCredientialsRepository.findByPasswordResetDate(new Date(endCalendar.getTimeInMillis()));
		return users.size() > 0 ? users : null;
	}

}
