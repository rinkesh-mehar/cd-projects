package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerGovtOfficialInfDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.FarmerGovtOfficialDesignation;
import in.cropdata.cdtmasterdata.service.FarmerGovtOfficialDesignationService;

@RestController
@RequestMapping("/farmer/govt-official-designation")
public class FarmerGovtOfficialDesignationController {

	@Autowired
	private FarmerGovtOfficialDesignationService farmerGovtOfficialDesignationService;
	
	@GetMapping("/list")
	public List<FarmerGovtOfficialDesignation> getAllFarmerGovtOfficialDesignation(){
		return farmerGovtOfficialDesignationService.getAllFarmerGovtOfficialDesignation();
	}//getAllFarmerGovtOfficialDesignation

	@GetMapping("/paginatedList")
	public Page<FarmerGovtOfficialInfDto> getAllFarmerGovtOfficialDesignationPaginated(
			@RequestParam("page") int page,
			@RequestParam("isValid") int isValid,
			@RequestParam("size") int size,
			@RequestParam(required = false, defaultValue = "") String searchText)
	{
		return farmerGovtOfficialDesignationService.getAllFarmerGovtOfficialDesignationPaginated(page, size, searchText, isValid);
	}
	
	@PostMapping("/add")
	public ResponseMessage addFarmerGovtOfficialDesignation(@RequestBody FarmerGovtOfficialDesignation farmerGovtOfficialDesignation) {
		return farmerGovtOfficialDesignationService.addFarmerGovtOfficialDesignation(farmerGovtOfficialDesignation);
	}//addFarmerGovtOfficialDesignation
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerGovtOfficialDesignationById(@RequestBody FarmerGovtOfficialDesignation farmerGovtOfficialDesignation, @PathVariable int id) {
		return farmerGovtOfficialDesignationService.updateFarmerGovtOfficialDesignationById(id, farmerGovtOfficialDesignation);
	}//updateFarmerGovtOfficialDesignationById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerGovtOfficialDesignationService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerGovtOfficialDesignationService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage deleteFarmerGovtOfficialDesignationById(@PathVariable int id) {
		return farmerGovtOfficialDesignationService.deleteFarmerGovtOfficialDesignationById(id);
	}//deleteFarmerGovtOfficialDesignationById
	
	@GetMapping("/{id}")
	public FarmerGovtOfficialDesignation findFarmerGovtOfficialDesignationById(@PathVariable int id) {
		return farmerGovtOfficialDesignationService.findFarmerGovtOfficialDesignationById(id);
	}//findFarmerGovtOfficialDesignationById
}//FarmerGovtOfficialDesignationController
