package com.krishi.step;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.DrKrishiUsers;
import com.krishi.repository.DrKrishiUsersRepository;

public class ScheduleUnlockUserReader implements ItemReader<List<DrKrishiUsers>> {
	
	@Autowired
	private DrKrishiUsersRepository drKrishiUsersRepository;

	/*Pull all locked user from database and send to processor for process*/
	@Override
	@Transactional(readOnly = true)
	public List<DrKrishiUsers> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		List<DrKrishiUsers> users = drKrishiUsersRepository.findByStatus(2);
		return users.size() > 0 ? users : null;
	}

}
