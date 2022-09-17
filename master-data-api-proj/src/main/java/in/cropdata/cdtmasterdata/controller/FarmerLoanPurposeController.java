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
import in.cropdata.cdtmasterdata.model.FarmerLoanPurpose;
import in.cropdata.cdtmasterdata.service.FarmerLoanPurposeService;

@RestController
@RequestMapping("/farmer/loan-purpose")
public class FarmerLoanPurposeController {

	@Autowired
	private FarmerLoanPurposeService farmerLoanPurposeService;

	@GetMapping("/list")
	public List<FarmerLoanPurpose> getAllFarmerLoanPurpose() {
		return farmerLoanPurposeService.getAllFarmerLoanPurpose();
	}// getAllFarmerLoanPurpose
	
	@GetMapping("/paginatedList")
	public Page<FarmerLoanPurpose> getFarmerLoanPurposeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerLoanPurposeService.getFarmerLoanPurposeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerLoanPurpose(@RequestBody FarmerLoanPurpose farmerLoanPurpose) {
		return farmerLoanPurposeService.addFarmerLoanPurpose(farmerLoanPurpose);
	}// addAllFarmerLoanPurpose

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerLoanPurposeById(@PathVariable int id,
			@RequestBody FarmerLoanPurpose farmerLoanPurpose) {
		return farmerLoanPurposeService.updateFarmerLoanPurposeById(id, farmerLoanPurpose);
	}// updateFarmerLoanPurposeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerLoanPurposeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerLoanPurposeService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerLoanPurposeService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerLoanPurposeById(@PathVariable int id) {
		return farmerLoanPurposeService.deleteFarmerLoanPurposeById(id);
	}// deleteFarmerLoanPurposeById

	@GetMapping("/{id}")
	public FarmerLoanPurpose findFarmerLoanPurposeById(@PathVariable int id) {
		return farmerLoanPurposeService.findFarmerLoanPurposeById(id);
	}// findFarmerLoanPurposeById

}// FarmerLoanPurposeController
