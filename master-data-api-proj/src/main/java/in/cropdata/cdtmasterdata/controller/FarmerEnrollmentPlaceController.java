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
import in.cropdata.cdtmasterdata.model.FarmerEnrollmentPlace;
import in.cropdata.cdtmasterdata.service.FarmerEnrollmentPlaceService;

@RestController
@RequestMapping("/farmer/enrollment-place")
public class FarmerEnrollmentPlaceController {

	@Autowired
	private FarmerEnrollmentPlaceService farmerEnrollmentPlaceService;

	@GetMapping("/list")
	public List<FarmerEnrollmentPlace> getAllFarmerEnrollmentPlace() {
		return farmerEnrollmentPlaceService.getAllFarmerEnrollmentPlace();
	}// getAllFarmerEnrollmentPlace
	
	@GetMapping("/paginatedList")
	public Page<FarmerEnrollmentPlace> getFarmarEnrollmentPlaceListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerEnrollmentPlaceService.getFarmarEnrollmentPlaceListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerEnrollmentPlace(@RequestBody FarmerEnrollmentPlace farmerEnrollmentPlace) {
		return farmerEnrollmentPlaceService.addFarmerEnrollmentPlace(farmerEnrollmentPlace);
	}// addAllFarmerEnrollmentPlace

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerEnrollmentPlaceById(@PathVariable int id,
			@RequestBody FarmerEnrollmentPlace farmerEnrollmentPlace) {
		return farmerEnrollmentPlaceService.updateFarmerEnrollmentPlaceById(id, farmerEnrollmentPlace);
	}// updateFarmerEnrollmentPlaceById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerEnrollmentPlaceService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerEnrollmentPlaceService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerEnrollmentPlaceService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerEnrollmentPlaceById(@PathVariable int id) {
		return farmerEnrollmentPlaceService.deleteFarmerEnrollmentPlaceById(id);
	}// deleteFarmerEnrollmentPlaceById

	@GetMapping("/{id}")
	public FarmerEnrollmentPlace findFarmerEnrollmentPlaceById(@PathVariable int id) {
		return farmerEnrollmentPlaceService.findFarmerEnrollmentPlaceById(id);
	}// findFarmerEnrollmentPlaceById

}// FarmerEnrollmentPlaceController
