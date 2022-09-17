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
import in.cropdata.cdtmasterdata.model.AgriIrrigationMethod;
import in.cropdata.cdtmasterdata.service.AgriIrrigationMethodService;

@RestController
@RequestMapping("/agri/irrigation-method")
public class AgriIrrigationMethodController {

	@Autowired
	private AgriIrrigationMethodService agriIrrigationMethodService;

	@GetMapping("/list")
	public List<AgriIrrigationMethod> getAllAgriIrrigationMethod() {
		return agriIrrigationMethodService.getAllAgriIrrigationMethod();
	}// getAllAgriIrrigationMethod
	
	@GetMapping("/paginatedList")
	public Page<AgriIrrigationMethod> getIrrigationMethodListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriIrrigationMethodService.getIrrigationMethodListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriIrrigationMethod(@RequestBody AgriIrrigationMethod agriIrrigationMethod) {
		return agriIrrigationMethodService.addAgriIrrigationMethod(agriIrrigationMethod);
	}// addAgriIrrigationMethod

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriIrrigationMethodById(@PathVariable int id,
			@RequestBody AgriIrrigationMethod agriIrrigationMethod) {
		return agriIrrigationMethodService.updateAgriIrrigationMethodById(id, agriIrrigationMethod);
	}// updateAgriIrrigationMethodById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriIrrigationMethodService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriIrrigationMethodService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriIrrigationMethodById(@PathVariable int id) {
		return agriIrrigationMethodService.deleteAgriIrrigationMethodById(id);
	}// deleteAgriIrrigationMethodById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriIrrigationMethodService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriIrrigationMethod findAgriIrrigationMethodById(@PathVariable int id) {
		return agriIrrigationMethodService.findAgriIrrigationMethodById(id);
	}// findAgriIrrigationMethodById

}// AgriIrrigationMethodController
