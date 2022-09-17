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
import in.cropdata.cdtmasterdata.model.GeneralTypeOfService;
import in.cropdata.cdtmasterdata.service.GeneralTypeOfServiceService;

@RestController
@RequestMapping("/general/type-of-service")
public class GeneralTypeOfServiceController {
	
	@Autowired
	private GeneralTypeOfServiceService generalTypeOfServiceService;
	
	@GetMapping("/list")
	public List<GeneralTypeOfService> getAllGeneralTypeOfService() {
		return generalTypeOfServiceService.getAllGeneralTypeOfService();
	}// getAllGeneralTypeOfService

	@GetMapping("/paginatedList")
	public Page<GeneralTypeOfService> getGeneralTypeOfServiceListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return generalTypeOfServiceService.getGeneralTypeOfServiceListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralTypeOfService(@RequestBody GeneralTypeOfService GeneralTypeOfService) {
		return generalTypeOfServiceService.addGeneralTypeOfService(GeneralTypeOfService);
	}// addAllGeneralTypeOfService

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralTypeOfServiceById(@PathVariable int id,
			@RequestBody GeneralTypeOfService GeneralTypeOfService) {
		return generalTypeOfServiceService.updateGeneralTypeOfServiceById(id, GeneralTypeOfService);
	}// updateGeneralTypeOfServiceById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalTypeOfServiceService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalTypeOfServiceService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralTypeOfServiceById(@PathVariable int id) {
		return generalTypeOfServiceService.deleteGeneralTypeOfServiceById(id);
	}// deleteGeneralTypeOfServiceById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalTypeOfServiceService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralTypeOfService findGeneralTypeOfServiceById(@PathVariable int id) {
		return generalTypeOfServiceService.findGeneralTypeOfServiceById(id);
	}// findGeneralTypeOfServiceById
	

}

