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
import in.cropdata.cdtmasterdata.model.AgriHealthParameter;
import in.cropdata.cdtmasterdata.service.AgriHealthParameterService;

@RestController
@RequestMapping("/agri/health-parameter")
public class AgriHealthParameterController {
	
	@Autowired
	private AgriHealthParameterService agriHealthParameterService;
	
	@GetMapping("/list")
	public List<AgriHealthParameter> getAllAgriHealthParameter(){
		return agriHealthParameterService.getAllAgriAgriHealthParameter();
	}//getAllAgriHealthParameter
	
	@GetMapping("/paginatedList")
	public Page<AgriHealthParameter> getHealthParameterListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriHealthParameterService.getHealthParameterListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseMessage addAgriHealthParameter(@RequestBody AgriHealthParameter agriHealthParameter) {
		return agriHealthParameterService.addAgriHealthParameter(agriHealthParameter);
	}//addAgriHealthParameter
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriHealthParameterById(@PathVariable int id,@RequestBody AgriHealthParameter agriHealthParameter) {
		return agriHealthParameterService.updateAgriHealthParameterById(id, agriHealthParameter);
	}//updateAgriHealthParameterById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriHealthParameterService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriHealthParameterService.finalApproveById(id);
	}// finalApproveById

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriHealthParameterById(@PathVariable int id) {
		return agriHealthParameterService.deleteAgriHealthParameterById(id);
	}//deleteAgriHealthParameterById
	
	@GetMapping("/{id}")
	public AgriHealthParameter findAgriHealthParametereById(@PathVariable int id) {
		return agriHealthParameterService.findAgriHealthParameterById(id);
	}//findAgriHealthParametereById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectAgriHealthParameterById(@PathVariable int id) {
		return agriHealthParameterService.rejectAgriHealthParameterById(id);
	}

}//AgriHealthParameterController
