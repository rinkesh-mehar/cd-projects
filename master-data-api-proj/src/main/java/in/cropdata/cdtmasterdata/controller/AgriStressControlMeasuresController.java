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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlMeasuresInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriStressControlMeasures;
import in.cropdata.cdtmasterdata.service.AgriStressControlMeasuresService;

@RestController
@RequestMapping("/agri/stress-control-measures")
public class AgriStressControlMeasuresController {

	@Autowired
	private AgriStressControlMeasuresService agriRecommendationService;

	@GetMapping("/list")
	public List<AgriStressControlMeasuresInfDto> getAgriStressControlMeasures() {
		return agriRecommendationService.getAgriStressControlMeasures();
	}// getAllAgriActivity

	@GetMapping()
	public Page<AgriStressControlMeasuresInfDto> getAllAgriStressControlMeasuresPaginated(@RequestParam("page") int page,
																			@RequestParam("size") int size,
																			@RequestParam("isValid") int isValid,
																			@RequestParam(required = false, defaultValue = "") String searchText,
																			@RequestParam(required = false,defaultValue = "0") String missing,
																			@RequestParam(required = false, defaultValue = "") String commodityId,
																			@RequestParam(required = false, defaultValue = "") String stressId,
																			@RequestParam(required = false, defaultValue = "") String stressSeverityId,
																			@RequestParam(required = false, defaultValue = "") String controlMeasureId,
																			@RequestParam(required = false, defaultValue = "") String filter)
	{
		return agriRecommendationService.getAllAgriStressControlMeasuresPaginated(page, size, searchText,isValid,missing,commodityId,stressId,stressSeverityId,controlMeasureId,filter);
	}// getAllAgriRecommendationPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriStressControlMeasure(@RequestBody AgriStressControlMeasures agriRecommendation) {
		return agriRecommendationService.addAgriStressControlMeasure(agriRecommendation);
	}// addAllAgriActivity
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriRecommendationService.primaryApproveById(id);
	}// primaryApproveById
	
	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriRecommendationService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressControlMeasuresById(@PathVariable int id,
			@RequestBody AgriStressControlMeasures agriRecommendation) {
		return agriRecommendationService.updateAgriStressControlMeasuresById(id, agriRecommendation);
	}// updateAgriActivityById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressControlMeasuresById(@PathVariable int id) {
		return agriRecommendationService.deleteAgriStressControlMeasuresById(id);
	}// deleteAgriActivityById

	@GetMapping("/{id}")
	public AgriStressControlMeasures findAgriStressControlMeasuresById(@PathVariable int id) {
		return agriRecommendationService.findAgriStressControlMeasuresById(id);
	}// findAgriActivityById
	
	@GetMapping("/getStressByCommodity")
	public List<AgriDistrictCommodityStressInfDto> getStressByCommodity(@RequestParam int commodityId) {
		return agriRecommendationService.getStressByCommodity(commodityId);
	}// getStressByCommodity

	/**
	 * @param id Base on id reject the recommendation
	 * @return response
	 */
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id)
	{
		return agriRecommendationService.rejectById(id);
	}

	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriRecommendationService.moveToMaster(id);
	}
	
	
}// AgriActivityController
