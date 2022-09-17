package com.krishi.service;

import com.krishi.constants.TaskTypeConstants;
import com.krishi.entity.Farmer;
import com.krishi.entity.Task;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class TaskGeneratorImpl implements TaskGenerator{

    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private TaskRepository taskRepository;

    boolean isNew = true;

    @Override
    public boolean generateTask() throws Exception {
        return this.write(this.read());

    }

    String generateKey(String entityName) {
        Properties properties = new Properties();
        try {
            InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("entity-code.properties");
            properties.load(resourceStream);
        } catch (IOException e1) {
            e1.printStackTrace();
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
    @Transactional
    List<Task> read() throws Exception {

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
            // update taskTypeId 10 to 1 - 28/09/21
//            t.setTaskTypeId(TaskTypeConstants.LEAD_CALLING);
            t.setTaskTypeId(10);
            taskList.add(t);
        });
        return taskList;
    }

    /*Save the task in the database*/
    @Transactional
    boolean write(List<Task> tasks) throws Exception {
        if (tasks != null)
        {
            tasks.forEach(t -> {
                System.out.println("new task generated : " + t.getId());
                taskRepository.saveAndFlush(t);
            });
            return true;
        } else {
            return false;
        }
    }
}
