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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyQualityInfDto;
import in.cropdata.cdtmasterdata.model.AgriVarietyQuality;
import in.cropdata.cdtmasterdata.service.AgriVarietyQualityService;

@RestController
@RequestMapping("/agri/variety-quality")
public class AgriVarietyQualityController {

	@Autowired
	private AgriVarietyQualityService agriVarietyQualityService;

	@GetMapping("/list")
	public List<AgriVarietyQualityInfDto> getAllAgriVarietyQuality() {
		return agriVarietyQualityService.getAllAgriVarietyQuality();
	}// getAllAgriVarietyQuality

	@GetMapping()
	public Page<AgriVarietyQualityInfDto> getAgriVarietyQualityPagination(@RequestParam("page") int page,
																		  @RequestParam("size") int size,
																		  @RequestParam("isValid") int isValid,
																		  @RequestParam(required = false, defaultValue = "") String searchText,
																		  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriVarietyQualityService.getAgriVarietyQualityPagination(page, size, isValid, searchText,missing);
	}// getAgriVarietyQualityPagination

	@PostMapping("/add")
	public ResponseMessage addAgriVarietyQuality(@RequestBody AgriVarietyQuality agriVarietyQuality) {
		return agriVarietyQualityService.addAgriVarietyQuality(agriVarietyQuality);
	}// addAgriVarietyQuality

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriVarietyQualityById(@PathVariable int id,
			@RequestBody AgriVarietyQuality agriVarietyQuality) {
		return agriVarietyQualityService.updateAgriVarietyQualityById(id, agriVarietyQuality);
	}// updateAgriVarietyQualityById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriVarietyQualityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriVarietyQualityService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriVarietyQualityById(@PathVariable int id) {
		return agriVarietyQualityService.deleteAgriVarietyQualityById(id);
	}// deleteAgriVarietyQualityById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriVarietyQualityService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriVarietyQuality findAgriVarietyQualityById(@PathVariable int id) {
		return agriVarietyQualityService.findAgriVarietyQualityById(id);
	}// findAgriVarietyQualityById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriVarietyQualityService.moveToMaster(id);
	}

}
