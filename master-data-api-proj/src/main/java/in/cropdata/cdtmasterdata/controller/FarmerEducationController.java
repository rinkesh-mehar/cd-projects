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
import in.cropdata.cdtmasterdata.model.FarmerEducation;
import in.cropdata.cdtmasterdata.service.FarmerEducationService;

@RestController
@RequestMapping("/farmer/education")
public class FarmerEducationController {

	@Autowired
	private FarmerEducationService farmerEducationService;

	@GetMapping("/list")
	public List<FarmerEducation> getAllFarmerEducation() {
		return farmerEducationService.getAllFarmerEducation();
	}// getAllFarmerEducation
	
	@GetMapping("/paginatedList")
	public Page<FarmerEducation> getFarmarEducationListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerEducationService.getFarmarEducationListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerEducation(@RequestBody FarmerEducation farmerEducation) {
		return farmerEducationService.addFarmerEducation(farmerEducation);
	}// addFarmerEducation

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerEducationById(@PathVariable int id,
			@RequestBody FarmerEducation farmerEducation) {
		return farmerEducationService.updateFarmerEducationById(id, farmerEducation);
	}// updateFarmerEducationById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerEducationService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerEducationService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerEducationService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerEducationById(@PathVariable int id) {
		return farmerEducationService.deleteFarmerEducationById(id);
	}// deleteFarmerEducationById

	@GetMapping("/{id}")
	public FarmerEducation findFarmerEducationById(@PathVariable int id) {
		return farmerEducationService.findFarmerEducationById(id);
	}// findFarmerEducationById

}// FarmerEducationController
