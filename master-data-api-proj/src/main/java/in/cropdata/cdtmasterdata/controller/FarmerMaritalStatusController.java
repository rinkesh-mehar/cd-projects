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
import in.cropdata.cdtmasterdata.model.FarmerMaritalStatus;
import in.cropdata.cdtmasterdata.service.FarmerMaritalStatusService;

@RestController
@RequestMapping("/farmer/marital-status")
public class FarmerMaritalStatusController {
	
	@Autowired
	private FarmerMaritalStatusService farmerMaritalStatusService;

	@GetMapping("/list")
	public List<FarmerMaritalStatus> getAllFarmerMaritalStatus() {
		return farmerMaritalStatusService.getAllFarmerMaritalStatus();
	}// getAllFarmerMaritalStatus
	
	@GetMapping("/paginatedList")
	public Page<FarmerMaritalStatus> getFarmerMaritalStatusListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerMaritalStatusService.getFarmerMaritalStatusListByPagenation(page, size, searchText);
	}


	@PostMapping("/add")
	public ResponseMessage addFarmerMaritalStatus(@RequestBody FarmerMaritalStatus farmerMaritalStatus) {
		return farmerMaritalStatusService.addFarmerMaritalStatus(farmerMaritalStatus);
	}// addAllFarmerMaritalStatus

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerMaritalStatusById(@PathVariable int id,
			@RequestBody FarmerMaritalStatus farmerMaritalStatus) {
		return farmerMaritalStatusService.updateFarmerMaritalStatusById(id, farmerMaritalStatus);
	}// updateFarmerMaritalStatusById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerMaritalStatusService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerMaritalStatusService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerMaritalStatusService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerMaritalStatusById(@PathVariable int id) {
		return farmerMaritalStatusService.deleteFarmerMaritalStatusById(id);
	}// deleteFarmerMaritalStatusById

	@GetMapping("/{id}")
	public FarmerMaritalStatus findFarmerMaritalStatusById(@PathVariable int id) {
		return farmerMaritalStatusService.findFarmerMaritalStatusById(id);
	}// findFarmerMaritalStatusById

}
