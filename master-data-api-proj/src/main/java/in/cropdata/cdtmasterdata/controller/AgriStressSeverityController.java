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
import in.cropdata.cdtmasterdata.model.AgriStressSeverity;
import in.cropdata.cdtmasterdata.service.AgriStressSeverityService;

@RestController
@RequestMapping("/agri/stress-severity")
public class AgriStressSeverityController {

	@Autowired
	private AgriStressSeverityService agriStressSeverityService;

	@GetMapping("/list")
	public List<AgriStressSeverity> findAllAgriStressServerity() {
		return agriStressSeverityService.getAllAgriStressServerity();
	}// findAllAgriStressServerity
	
	@GetMapping("/paginatedList")
	public Page<AgriStressSeverity> getPeginatedStressSeverityList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriStressSeverityService.getPeginatedStressSeverityList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAllAgriStressServerity(@RequestBody AgriStressSeverity agriStressServerity) {
		return agriStressSeverityService.addAllAgriStressServerity(agriStressServerity);
	}// addAllAgriStressServerity

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressSeverityById(@PathVariable int id,
			@RequestBody AgriStressSeverity agriStressServerity) {
		return agriStressSeverityService.updateAgriStressSeverityById(id, agriStressServerity);
	}// updateAgriStressSeverityById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressSeverityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressSeverityService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressSeverityById(@PathVariable int id) {
		return agriStressSeverityService.deleteAgriStressServerityById(id);
	}// deleteAgriStressSeverityById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStressSeverityService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriStressSeverity findAgriStressSeverityById(@PathVariable int id) {
		return agriStressSeverityService.findAgriStressSeverityById(id);
	}// findAgriStressSeverityById
}// AgriStressSeverityController
