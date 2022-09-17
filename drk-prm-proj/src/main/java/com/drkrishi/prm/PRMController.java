package com.drkrishi.prm;

import com.drkrishi.prm.model.*;
import com.drkrishi.prm.service.PRMService;
import com.drkrishi.prm.service.PR_Village_AssigmentService;
import com.drkrishi.prm.service.SearchPrsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@ComponentScan({ "com.drkrishi.prm.service,com.drkrishi.prm.dao" })
//@RequestMapping("/prm-dev")
public class PRMController {

	private static final Logger LOGGER = LogManager.getLogger(PRMController.class);

	@Autowired
	private PRMService prmService;

	@Autowired
	private PR_Village_AssigmentService assigmentService;

	@Autowired
	private SearchPrsServiceImpl searchPrsServiceImpl;
	
	//Assign village task to PRS

	@ApiOperation(value = "Assign village task to PRS", response = ResponseModel.class)
	@RequestMapping(value = "/assignVillages", method = RequestMethod.POST, consumes = "application/json")
	public ResponseModel assignVillagesToPRS(@RequestBody PRModel assigment) {

		LOGGER.info("API stated, request PRModel : " + assigment.toString());
		ResponseModel flag = assigmentService.save_Village_AssigmentService(assigment);
		LOGGER.info("API end, response ResponseModel : " + flag.toString());
		return flag;
	}
	
	@ApiOperation(value = "Get the list of assigned list for perticular week and year", response = ResponseModel.class)
	@RequestMapping(value = "/getAssignedVillage/{week}/{year}", method = RequestMethod.GET)
	public List<Integer> getAssignedVillageByWeek(@PathVariable("week") int week  , @PathVariable("year") int year){
		return assigmentService.getAssignedVillageByWeek(week , year);
	}
	

	//Get list of assignment done PRS by PRM for 5 weeks
	@ApiOperation(value = "Get list of assignment done PRS by PRM for 5 weeks")
	@RequestMapping(value = "/getassignmentlist/{prmId}", method = RequestMethod.GET)
	public List<Villagetask> getAssignmentList(@PathVariable("prmId") int prmId) {

		LOGGER.info("API stated, request prmId : " + prmId);
		System.out.println("API stated, request prmId::::::::::::::::::::::; : " + prmId);
		List<Villagetask> villageTaskList = assigmentService.getAssignmentListByPrmId(prmId);

		villageTaskList.forEach(villageTask -> {
			System.out.println(villageTask.toString());
		});

		LOGGER.info(
				"API end, response Village task count : " + (villageTaskList != null ? villageTaskList.size() : null));

		return villageTaskList;
	}

	
	//Get all assignment list
	@ApiOperation(value = "Get all assignment list")
	@RequestMapping(value = "/getAllAssignmentlist", method = RequestMethod.GET)
	public List<PR_Village_Assigment> getAllAssignmentList() {

		LOGGER.info("API stated, no request parameter");
		List<PR_Village_Assigment> prVillageAssigment = assigmentService.getPR_Village_AllAssigmentByPRMId();
		LOGGER.info("API end, response Village task count : "
				+ (prVillageAssigment != null ? prVillageAssigment.size() : null));
		return prVillageAssigment;
	}

	//get list of all states
	@ApiOperation(value = "get list of all states")
	@RequestMapping(value = "/getStates", method = RequestMethod.GET)
	public List<State> getStates() {
		LOGGER.info("API stated, no request parameter");
		List<State> state = prmService.getStates();
		LOGGER.info("API end, response state count : " + (state != null ? state.size() : null));
		return state;
	}

	//get villages based on villages
	@ApiOperation(value = "get villages based on villages")
	@RequestMapping(value = "/getVillages/{talukaCode}", method = RequestMethod.GET)
	public List<Village> getVillages(@PathVariable("talukaCode") int talukaCode) {
		LOGGER.info("API stated, request taluka code : " + talukaCode);
		List<Village> villages = prmService.findVillagesBytalika(talukaCode);
		LOGGER.info("API end, response village count : " + (villages != null ? villages.size() : null));
		return villages;
	}

	//get village info based on village
	@ApiOperation(value = "get village info based on village")
	@RequestMapping(value = "/getVillageInfo/{villageCode}", method = RequestMethod.GET)
	public List<Village_Info> getVillageInfo(@PathVariable("villageCode") int villageCode) {
		LOGGER.info("API stated, request village code : " + villageCode);
		List<Village_Info> villageInfo = prmService.findVillagesInfoByVillage(villageCode);
		LOGGER.info(
				"API end, response village inforamtion count : " + (villageInfo != null ? villageInfo.size() : null));
		return villageInfo;
	}

	//Get PR Scouts based on PRM and region
	@ApiOperation(value = "Get PR Scouts based on PRM and region")
	@RequestMapping(value = "/getScoutNames/{userId}/{regionId}", method = RequestMethod.GET)
	public List<DRKrishiUser> getScoutNames(@PathVariable("userId") int userId,
			@PathVariable("regionId") int regionId) {
		LOGGER.info("API stated, request userid : " + userId + " regionid: " + regionId);

		List<DRKrishiUser> users = prmService.findScoutByReportingId(userId, regionId);
		LOGGER.info("API end, response user count : " + (users != null ? users.size() : null));
		return users;
	}

	//Get PR Scouts based on PRM
	@ApiOperation(value = "Get PR Scouts based on PRM")
	@RequestMapping(value = "/getScoutNames/{userId}", method = RequestMethod.GET)
	public List<DRKrishiUser> getScoutNamesByPRMID(@PathVariable("userId") int userId) {
		LOGGER.info("API stated, request userid : " + userId);

		List<DRKrishiUser> users = prmService.findScoutByPRmId(userId);
		LOGGER.info("API end, response user count : " + (users != null ? users.size() : null));
		return users;
	}

	//Get villages by district
	@ApiOperation(value = "Get villages by district")
	@RequestMapping(value = "/getVillagesByDistrict/{district}", method = RequestMethod.GET)
	public List<Village> getVillagesbyDistrict(@PathVariable("district") int districtCode) {
		LOGGER.info("API stated, request district code : " + districtCode);
		List<Village> villages = prmService.findVillagesbyDistrict(districtCode);
		LOGGER.info("API end, response villages count : " + (villages != null ? villages.size() : null));
		return villages;
	}

	//Get region based on state
	@ApiOperation(value = "Get region based on state")
	@RequestMapping(value = "/getRegions/{stateCode}", method = RequestMethod.GET)
	public List<Region> getRegionByState(@PathVariable("stateCode") int stateCode) {
		LOGGER.info("API stated, request state code : " + stateCode);
		List<Region> region = prmService.findRegionByState(stateCode);
		LOGGER.info("API end, response region count : " + (region != null ? region.size() : null));
		return region;
	}

	//Get villages based on tile id
	@ApiOperation(value = "Get villages based on tile id")
	@RequestMapping(value = "/getvillagesByTileId/{tileId}", method = RequestMethod.GET)
	public List<Village> getvillagesByTileId(@PathVariable("tileId") int tileId) {
		LOGGER.info("API stated, request tileId : " + tileId);
		List<Village> villages = prmService.findVillagesByTileId(tileId);
		LOGGER.info("API end, response villages count : " + (villages != null ? villages.size() : null));
		return villages;
	}
	
	//Get list of assignment done PRS by PRM for 5 weeks
	@ApiOperation(value = "Get list of assignment done PRS by PRM for 5 weeks")
	@RequestMapping(value = "/getAssignmentListFromSearch", method = RequestMethod.POST, consumes = "application/json")
	public List<Villagetask> searchPrsAssignmentDetails(@RequestBody SearchPrmAssigntask search) {

		List<Villagetask> villageTaskList = searchPrsServiceImpl.getAssignmentListByPrmIdSearch(search);

		villageTaskList.forEach(villageTask -> {
		});

		LOGGER.info(
				"API end, response Village task count : " + (villageTaskList != null ? villageTaskList.size() : null));

		return villageTaskList;
	}

	@RequestMapping(path = "/getAssignmentTaskForEdit/{assgnmentId}", method = RequestMethod.GET)
	public PrsEditResponseModel getAssignmentDetails(@PathVariable("assgnmentId") int assgnmentId) {
		return assigmentService.getAssignmentDetailsForEdit(assgnmentId);

	}

	@RequestMapping(path = "/deleteAssignmentTask/{assgnmentId}", method = RequestMethod.DELETE)
	public ResponseModel deleteAssignmentTask(@PathVariable("assgnmentId") int assgnmentId) {
		return assigmentService.deleteAssignmentTask(assgnmentId);
	}
	
	@ApiOperation(value = "Get PR Scouts Name list based on stateid and regionid")
	@RequestMapping(value = "/getScoutNamesList/{userId}/{stateId}/{regionId}", method = RequestMethod.GET)
	public ResponseMessage getScoutNamesList(@PathVariable("userId") int userId,@PathVariable("stateId") int stateId,
			@PathVariable("regionId") int regionId) {
		return searchPrsServiceImpl.getScoutNamesListByStateidAndRegionid(userId,stateId, regionId);

	}
}
