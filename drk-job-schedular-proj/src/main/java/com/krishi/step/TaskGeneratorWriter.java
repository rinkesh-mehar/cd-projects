package com.krishi.step;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.Task;
import com.krishi.repository.TaskRepository;

public class TaskGeneratorWriter implements ItemWriter<List<Task>> {
	
	@Autowired
	private TaskRepository taskRepository;

	/*Save the task in the database*/
	@Override
	@Transactional
	public void write(List<? extends List<Task>> tasks) throws Exception {
			tasks.get(0).forEach(t -> {
				System.out.println("new task generated : "+t.getId());
				taskRepository.saveAndFlush(t);
			});
	}

}
