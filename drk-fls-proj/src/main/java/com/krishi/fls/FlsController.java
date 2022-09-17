package com.krishi.fls;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krishi.fls.utility.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.krishi.fls.entity.Farmer;
import com.krishi.fls.model.Error;
import com.krishi.fls.model.FlsDataSynch;
import com.krishi.fls.model.HistoricalData;
import com.krishi.fls.model.Response;
import com.krishi.fls.model.RightsData;
import com.krishi.fls.model.ScheduledTask;
import com.krishi.fls.services.FileManagementService;
import com.krishi.fls.services.FlsService;
import com.krishi.fls.services.TaskAllocationService;

import io.swagger.annotations.ApiOperation;

@RestController
@ComponentScan(basePackages = { "com.krishi.fls.dao,com.krishi.fls.services" })
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
//@RequestMapping("/fls-dev")
public class FlsController {

	private static final Logger LOGGER = LogManager.getLogger(FlsController.class);

	@Autowired
	private FlsService flsService;

	@Autowired
	private TaskAllocationService taskAllocationService;
	
	@Autowired
	private FileManagementService fileManagementService;

	@Autowired
	private FileUtility fileUtility;

	@ApiOperation(value = "Task creation for FLS")
	@RequestMapping(path = "/setTaskAllocation", method = RequestMethod.GET, produces = "application/json")
	public void doTaskAllocation() {
		LOGGER.info("API stated, no request parameter");
		taskAllocationService.setTask();
		LOGGER.info("API end, no response");
	}

	@ApiOperation(value = "Task list for FLS ScheduledTask")
	@RequestMapping(path = "/getAssigment/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ScheduledTask getAssigment(@PathVariable("userId") Integer userId) {
		LOGGER.info("API stated, request userid : " + userId);
		ScheduledTask scheduledTask = flsService.getFlsTaskData(userId);

		LOGGER.info("API end, response ScheduledTask : " + scheduledTask.toString());
		return scheduledTask;
	}

//	@RequestMapping(path = "/flssynch/{userId}", method = RequestMethod.GET, produces = "application/json")
//  public void getFLSsynch(@PathVariable("userId") Integer userId) {
//     flsService.syschFlsTaskData(userId);
//  }

	@ApiOperation(value = "Day end sync for FLS")
	@RequestMapping(path = "/flssynch/{userId}", method = RequestMethod.POST, produces = "application/json")
	public Response getFLSdayendsynch(@RequestBody FlsDataSynch dayEndSynch) {
		LOGGER.info("API stated, no request parameter");
		Response response=new Response();
		try {
		flsService.syschFlsCollectedData(dayEndSynch);
		response.setSuccess("Data Synced Successfully.");
		}
		catch(Exception exception)
		{
			
			Error error=new Error();
			error.setErrorMesg("Transaction roll back due to some error");
			response.setError(error);
		
		}
		LOGGER.info("API end, no response");
		return response;
		
	}
	
	
	@ApiOperation(value = "History for FLS")
	@RequestMapping(path = "/flshistory/{userId}", method = RequestMethod.GET, produces = "application/json")
	public HistoricalData getFLSHistoryData(@PathVariable("userId") Integer userId) {
		LOGGER.info("API stated, no request parameter");
		return flsService.history(userId);
		
	}
	
	@RequestMapping(value="/upload", method = RequestMethod.POST,  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response saveFile(@RequestParam("file") MultipartFile file) {
		LOGGER.info("API stated");
		return fileManagementService.saveFile(file);
	}
	
	
	@ApiOperation(value = "Day end sync for FLS and FLS")
	@RequestMapping(path = "/dayEndSync/{userId}", method = RequestMethod.POST, produces = "application/json")
	public Response getDayEndSync(@RequestBody String dayEndSync, @PathVariable("userId") Integer userId) {
		LOGGER.info("API stated, no request parameter");
		Response response = flsService.saveOnFile(dayEndSync, userId);
		LOGGER.info("API end, no response");
		return response;
		
	}
	
	@ApiOperation(value = "Rights info")
	@RequestMapping(value="/rightInfo/{caseId}", method = RequestMethod.GET, produces = "application/json")
	public RightsData getRightInfo(@PathVariable("caseId") String caseId) {
		LOGGER.info("API stated, no request parameter");
		RightsData data = flsService.getRightsInfo(caseId);
		return data;
	}
	
	/** Find Farmer already exist -CDT-Ujwal*/
	@ApiOperation(value = "Farmer already exist")
	@RequestMapping(path = "/farmerExist", method = RequestMethod.POST, produces = "application/json")
	public Response checkDrkrishiFarmer(@RequestBody Farmer farmer) {
		LOGGER.info("Get farmer App Farmer");
		return flsService.checkFarmerExist(farmer);
		
	}

	@ApiOperation(value = "get scout data")
	@RequestMapping(path = "/user-data-fls/{userId}", method = RequestMethod.GET, produces = "application/json")
	public Map<String, Object> getScoutData(@PathVariable("userId") Integer userId) throws JsonProcessingException {
		return flsService.getScoutData(userId);
	}

	@RequestMapping(path="/store-zip-file", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> getZipFile(@RequestParam("file") MultipartFile file) throws IOException {
		return flsService.unzipFile(file);

	}

	/*@RequestMapping(path = "/part-mmpk-file", method = RequestMethod.GET, produces = "application/json")
	public void getMmpkFile(){
			fileUtility.splitFile();
	}*/
}
