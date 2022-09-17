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
import in.cropdata.cdtmasterdata.model.FarmerInsuranceType;
import in.cropdata.cdtmasterdata.service.FarmerInsuranceTypeService;

@RestController
@RequestMapping("/farmer/insurance-type")
public class FarmerInsuranceTypeController {

	@Autowired
	private FarmerInsuranceTypeService farmerInsuranceTypeService;

	@GetMapping("/list")
	public List<FarmerInsuranceType> getAllFarmerInsurance() {
		return farmerInsuranceTypeService.getAllFarmerInsurance();
	}// getAllFarmerInsurance
	
	@GetMapping("/paginatedList")
	public Page<FarmerInsuranceType> getFarmerInsuranceTypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerInsuranceTypeService.getFarmerInsuranceTypeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerInsurance(@RequestBody FarmerInsuranceType farmerInsuranceType) {
		return farmerInsuranceTypeService.addFarmerInsurance(farmerInsuranceType);
	}// addAllFarmerInsurance

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerInsuranceById(@PathVariable int id,
			@RequestBody FarmerInsuranceType farmerInsuranceType) {
		return farmerInsuranceTypeService.updateFarmerInsuranceById(id, farmerInsuranceType);
	}// updateFarmerInsuranceById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerInsuranceTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerInsuranceTypeService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerInsuranceTypeService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerInsuranceById(@PathVariable int id) {
		return farmerInsuranceTypeService.deleteFarmerInsuranceById(id);
	}// deleteFarmerInsuranceById

	@GetMapping("/{id}")
	public FarmerInsuranceType findFarmerInsuranceById(@PathVariable int id) {
		return farmerInsuranceTypeService.findFarmerInsuranceById(id);
	}// findFarmerInsuranceById

}// FarmerInsuranceController
