package com.krishi.step;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.batch.BatchApplication;
import com.krishi.entity.Task;
import com.krishi.repository.TaskRepository;

public class TaskPendingReader implements ItemReader<List<Task>> {

	private static final Logger LOGGER = LogManager.getLogger(BatchApplication.class);

	@Autowired
	TaskRepository taskRepository;

	/*Read all pending task*/
	@Override
	public List<Task> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		List<Task> taskList = taskRepository.taskPending();
		return taskList.size() > 0 ? taskList : null;
	}

}
