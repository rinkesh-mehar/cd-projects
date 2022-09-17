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
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.AgriActivity;
import in.cropdata.cdtmasterdata.service.AgriActivityService;

@RestController
@RequestMapping("/agri/allied-activity")
public class AgriActivityController {

	@Autowired
	private AgriActivityService agriActivityService;

	@GetMapping("/list")
	public List<AgriActivity> getAllAgriActivity() {
		return agriActivityService.getAllAgriActivity();
	}// getAllAgriActivity
	
	@GetMapping("/paginatedList")
	public Page<AgriActivity> getActivityListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriActivityService.getActivityListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriActivity(@RequestBody AgriActivity agriActivity) {
		return agriActivityService.addAgriActivity(agriActivity);
	}// addAllAgriActivity

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriActivityById(@PathVariable int id, @RequestBody AgriActivity agriActivity) {
		return agriActivityService.updateAgriActivityById(id, agriActivity);
	}// updateAgriActivityById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriActivityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriActivityService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriActivityService.rejectById(id);
	}// rejectById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriActivityById(@PathVariable int id) {
		return agriActivityService.deleteAgriActivityById(id);
	}// deleteAgriActivityById

	@GetMapping("/{id}")
	public AgriActivity findAgriActivityById(@PathVariable int id) {
		return agriActivityService.findAgriActivityById(id);
	}// findAgriActivityById

}// AgriActivityController
