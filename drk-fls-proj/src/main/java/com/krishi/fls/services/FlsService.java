package com.krishi.fls.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.krishi.fls.entity.Farmer;
import com.krishi.fls.model.FlsDataSynch;
import com.krishi.fls.model.HistoricalData;
import com.krishi.fls.model.Response;
import com.krishi.fls.model.RightsData;
import com.krishi.fls.model.ScheduledTask;
import org.springframework.web.multipart.MultipartFile;

@Component
@Service
public interface FlsService {
 public ScheduledTask getFlsTaskData(int userId);
 public void syschFlsTaskData(Integer userId);
 public Response syschFlsCollectedData(FlsDataSynch dataSynch);
 public HistoricalData history(Integer userId);
 public Response saveOnFile(String dayEndSynch, Integer userId);
 
 public RightsData getRightsInfo(String caseId);
 public Response checkFarmerExist(Farmer farmer);

 public Map<String, Object> getScoutData(Integer userId) throws JsonProcessingException;

 public Map<String, Object> unzipFile(MultipartFile file) throws IOException;


 public ScheduledTask getFlsTask(int userId);

}
