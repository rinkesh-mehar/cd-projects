package com.krishi.step;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import com.krishi.constants.TaskTypeConstants;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.Farmer;
import com.krishi.entity.Task;
import com.krishi.repository.FarmerRepository;

public class TaskGeneratorReader implements ItemReader<List<Task>> {
	
	@Autowired
	private FarmerRepository farmerRepository;
	
	boolean isNew = true;
	
	private String generateKey(String entityName) {
		Properties properties = new Properties();
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("entity-code.properties");
			properties.load(resourceStream);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int fixLenght = Integer.parseInt(properties.getProperty("FIX_LENGHT"));
		String entityValue = properties.getProperty(entityName);
		String id = String.valueOf(properties.getProperty("USER_ID"));
		int prefixZero = fixLenght - id.length();
		StringBuffer sb = new StringBuffer(entityValue);
		for( int i=0; i<prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	/*read all newly created farmer from farmer table, create task object and send to Writer*/
	@Override
	@Transactional
	public List<Task> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if(!isNew) {
			isNew = true;
			return null;
		} else {
			isNew = false;
		}
		
		List<Task> taskList = new ArrayList<Task>();
		List<Farmer> farmers = farmerRepository.findNewFarmer();
	
		farmers.forEach(f -> {
			Task t = new Task();
			t.setId(generateKey("TASK"));
			t.setAssigneeId(0);
			t.setTaskDate(new Date(System.currentTimeMillis()));
			t.setEntityId(f.getFarmerId());
			t.setEntityTypeId(4);
			t.setStatus(0);
			t.setTaskTypeId(TaskTypeConstants.LEAD_CALLING);
			taskList.add(t);
		});
		return taskList;
	}

}
