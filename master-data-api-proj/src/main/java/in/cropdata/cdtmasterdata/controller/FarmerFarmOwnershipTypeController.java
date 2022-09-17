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
import in.cropdata.cdtmasterdata.model.FarmerFarmOwnershipType;
import in.cropdata.cdtmasterdata.service.FarmerFarmOwnershipTypeService;

@RestController
@RequestMapping("/farmer/farm-ownership-type")
public class FarmerFarmOwnershipTypeController {

	@Autowired
	private FarmerFarmOwnershipTypeService farmerFarmOwnershipTypeService;

	@GetMapping("/list")
	public List<FarmerFarmOwnershipType> getAllFarmerFarmOwnershipType() {
		return farmerFarmOwnershipTypeService.getAllFarmerFarmOwnershipType();
	}// getAllFarmerFarmOwnershipType
	
	@GetMapping("/paginatedList")
	public Page<FarmerFarmOwnershipType> getFarmerFarmOwnershipTypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerFarmOwnershipTypeService.getFarmerFarmOwnershipTypeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerFarmOwnershipType(@RequestBody FarmerFarmOwnershipType farmerFarmOwnershipType) {
		return farmerFarmOwnershipTypeService.addFarmerFarmOwnershipType(farmerFarmOwnershipType);
	}// addFarmerFarmOwnershipType

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerFarmOwnershipTypeById(@PathVariable int id,
			@RequestBody FarmerFarmOwnershipType farmerFarmOwnershipType) {
		return farmerFarmOwnershipTypeService.updateFarmerFarmOwnershipTypeById(id, farmerFarmOwnershipType);
	}// updateFarmerFarmOwnershipTypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerFarmOwnershipTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerFarmOwnershipTypeService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerFarmOwnershipTypeById(@PathVariable int id) {
		return farmerFarmOwnershipTypeService.deleteFarmerFarmOwnershipTypeById(id);
	}// deleteFarmerFarmOwnershipTypeById

	@GetMapping("/{id}")
	public FarmerFarmOwnershipType findFarmerFarmOwnershipTypeById(@PathVariable int id) {
		return farmerFarmOwnershipTypeService.findFarmerFarmOwnershipTypeById(id);
	}// findFarmerFarmOwnershipTypeById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerFarmOwnershipTypeService.rejectById(id);
	}// finalApproveById

}// FarmerFarmOwnershipTypeController
