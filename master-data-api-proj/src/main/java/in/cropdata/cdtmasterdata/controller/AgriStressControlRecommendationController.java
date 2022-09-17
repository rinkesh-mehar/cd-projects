package in.cropdata.cdtmasterdata.controller;

import java.util.List;

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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriQuantityLossChartInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlRecommendationInfDto;
import in.cropdata.cdtmasterdata.model.AgriStressControlRecommendation;
import in.cropdata.cdtmasterdata.service.AgriStressControlRecommendationService;

@RestController
@RequestMapping("/agri/stress-control-recommendation")
public class AgriStressControlRecommendationController {

	@Autowired
	private AgriStressControlRecommendationService agriStressControlRecommendationService;

	@GetMapping("/list")
	public List<AgriStressControlRecommendationInfDto> getAllStressControlRecommendation() {
		return agriStressControlRecommendationService.getAllAgriStressControlRecommendation();
	}// getAllAgriActivity

	@GetMapping()
	public Page<AgriStressControlRecommendationInfDto> getAllAgriStressControlRecommendationPaginated(@RequestParam("page") int page,
																									  @RequestParam("size") int size,
																									  @RequestParam("isValid") int isValid,
																									  @RequestParam(required = false, defaultValue = "") String searchText,
																									  @RequestParam(required = false,defaultValue = "0") String missing,
																									  @RequestParam(required = false, defaultValue = "") String stateCode,
																									  @RequestParam(required = false, defaultValue = "") String districtCode,
																									  @RequestParam(required = false, defaultValue = "") String commodityId,
																									  @RequestParam(required = false, defaultValue = "") String controlMeasureId,
																									  @RequestParam(required = false, defaultValue = "") String stressId,
																									  @RequestParam(required = false, defaultValue = "") String recomendationID,
																									  @RequestParam(required = false, defaultValue = "") String agrochemicalId,
																									  @RequestParam(required = false, defaultValue = "") String filter)
	{
		return agriStressControlRecommendationService.getAllAgriStressControlRecommendationPaginated(page, size, searchText, isValid,missing, stateCode,districtCode,commodityId,controlMeasureId,stressId,recomendationID,agrochemicalId,filter);
	}// getAllAgriStressControlRecommendationPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriStressControlRecommendation(
			@RequestBody AgriStressControlRecommendation agriStressControlRecommendation) {
		return agriStressControlRecommendationService
				.addAgriStressControlRecommendation(agriStressControlRecommendation);
	}// addAllAgriActivity
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressControlRecommendationService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressControlRecommendationService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressControlRecommendationById(@PathVariable int id,
			@RequestBody AgriStressControlRecommendation agriStressControlRecommendation) {
		return agriStressControlRecommendationService.updateAgriStressControlRecommendationById(id,
				agriStressControlRecommendation);
	}// updateAgriActivityById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressControlRecommendationById(@PathVariable int id) {
		return agriStressControlRecommendationService.deleteAgriStressControlRecommendationById(id);
	}// deleteAgriActivityById

	@GetMapping("/{id}")
	public AgriStressControlRecommendation findAgriStressControlRecommendationById(@PathVariable int id) {
		return agriStressControlRecommendationService.findAgriStressControlRecommendationById(id);
	}// findAgriActivityById


	/**
	 * @param id //rejected the stress control recommendation base on id
	 * @return // return response
	 */
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id)
	{
		return agriStressControlRecommendationService.rejectById(id);
	}
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriStressControlRecommendationService.moveToMaster(id);
	}

}// AgriActivityController
