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
import in.cropdata.cdtmasterdata.model.FarmerMobileType;
import in.cropdata.cdtmasterdata.service.FarmerMobileTypeService;

@RestController
@RequestMapping("/farmer/mobile-type")
public class FarmerMobileTypeController {
	
	@Autowired
	private FarmerMobileTypeService farmerMobileTypeService;

	@GetMapping("/list")
	public List<FarmerMobileType> getAllFarmerMobileType() {
		return farmerMobileTypeService.getAllFarmerMobileType();
	}// getAllFarmerMobileType
	
	@GetMapping("/paginatedList")
	public Page<FarmerMobileType> getFarmerMobileTypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerMobileTypeService.getFarmerMobileTypeListByPagenation(page, size, searchText);
	}


	@PostMapping("/add")
	public ResponseMessage addFarmerMobileType(@RequestBody FarmerMobileType farmerMobileType) {
		return farmerMobileTypeService.addFarmerMobileType(farmerMobileType);
	}// addAllFarmerMobileType

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerMobileTypeById(@PathVariable int id,
			@RequestBody FarmerMobileType farmerMobileType) {
		return farmerMobileTypeService.updateFarmerMobileTypeById(id, farmerMobileType);
	}// updateFarmerMobileTypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerMobileTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerMobileTypeService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerMobileTypeService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerMobileTypeById(@PathVariable int id) {
		return farmerMobileTypeService.deleteFarmerMobileTypeById(id);
	}// deleteFarmerMobileTypeById

	@GetMapping("/{id}")
	public FarmerMobileType findFarmerMobileTypeById(@PathVariable int id) {
		return farmerMobileTypeService.findFarmerMobileTypeById(id);
	}// findFarmerMobileTypeById
}
