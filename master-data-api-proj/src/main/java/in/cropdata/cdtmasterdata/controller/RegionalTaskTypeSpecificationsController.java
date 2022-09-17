package in.cropdata.cdtmasterdata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.TaskTypeSpecificationsInfDto;
import in.cropdata.cdtmasterdata.model.RegionalSeason;
import in.cropdata.cdtmasterdata.model.TaskTypeSpecifications;
import in.cropdata.cdtmasterdata.service.RegionalTaskTypeSpecificationsService;

@RestController
@RequestMapping("/regional/task-type-specification")
public class RegionalTaskTypeSpecificationsController {
	
	@Autowired
	private RegionalTaskTypeSpecificationsService regTaskTypeSpecificationsService;
	
	
	@GetMapping("/all-list")
	public List<Map<String,Object>> getAllRegionalTaskTypeSpecification(){
		return regTaskTypeSpecificationsService.getAllRegionTaskTypeSpecification();
	}// getAllRegionalTaskTypeSpecification


	@GetMapping("/paginatedList")
	public Page<TaskTypeSpecificationsInfDto> getAllRegionalTaskTypeSpecificationPaginated(@RequestParam("page") int page,
																						   @RequestParam("size") int size,
																						   @RequestParam("isValid") int isValid,
																						   @RequestParam(required = false, defaultValue = "") String searchText,
																						   @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return regTaskTypeSpecificationsService.getAllRegionalTaskTypeSpecificationPaginated(page, size, searchText, isValid,missing);
	} //getAllRegionalTaskTypeSpecificationPaginated
	
	@PostMapping("/add")
	public ResponseMessage addRegionSeason(@RequestBody TaskTypeSpecifications taskTypeSpecifications) {
		return regTaskTypeSpecificationsService.addTaskTypeSpecifications(taskTypeSpecifications);
	}// addAllRegionSeason
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionSeasonById(@PathVariable int id, @RequestBody TaskTypeSpecifications taskTypeSpecifications) {
		return regTaskTypeSpecificationsService.updateTaskTypeSpecificationsById(id, taskTypeSpecifications);
	}// updateRegionSeasonById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regTaskTypeSpecificationsService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regTaskTypeSpecificationsService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regTaskTypeSpecificationsService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionSeasonById(@PathVariable int id) {
		return regTaskTypeSpecificationsService.deleteTaskTypeSpecificationsById(id);
	}// deleteRegionSeasonById

	@GetMapping("/{id}")
	public TaskTypeSpecifications findRegionSeasonById(@PathVariable int id) {
		return regTaskTypeSpecificationsService.findTaskTypeSpecificationsById(id);
	}//findRegionSeasonById
	
	@GetMapping("/getStateList")
	public List<Map<String,Object>> getStateList(){
		return regTaskTypeSpecificationsService.getStateList();
	}//getStateList
	
	@GetMapping("/getSeasonList")
	public List<Map<String,Object>> getSeasonList(){
		return regTaskTypeSpecificationsService.getSeasonList();
	}//getSeasonList
	
	@GetMapping("/getCommodityList")
	public List<Map<String,Object>> getCommodityList(){
		return regTaskTypeSpecificationsService.getCommodityList(); 
	}//getCommodityList
	
	@GetMapping("/getVarietyList")
	public List<Map<String,Object>> getVarietyList(@RequestParam("commodityID") Integer commodityID){
		return regTaskTypeSpecificationsService.getVarietyList(commodityID);
	}//getVarietyList
	
	@GetMapping("/getPhenophaseList")
	public List<Map<String,Object>> getPhenophaseList(@RequestParam("commodityID") Integer commodityID, @RequestParam("varietyID") Integer varietyID){
		return regTaskTypeSpecificationsService.getPhenophaseList(commodityID, varietyID);
	}//getPhenophaseList
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regTaskTypeSpecificationsService.moveToMaster(id);
	}
	
}
