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
import in.cropdata.cdtmasterdata.model.FarmerVipDesignation;
import in.cropdata.cdtmasterdata.service.FarmerVipDesignationService;


@RestController
@RequestMapping("/farmer/vip-designation")
public class FarmerVipDesignationController {

	@Autowired
	private FarmerVipDesignationService farmerVipDesignationService;

	@GetMapping("/list")
	public List<FarmerVipDesignation> getAllFarmerVipDesignation() {
		return farmerVipDesignationService.getAllFarmerVipDesignations();
	}// getAllFarmerVipDesignation

	@GetMapping("/paginatedList")
	public Page<FarmerVipDesignation> getAllFarmerVipDesignationPaginated(@RequestParam int page, @RequestParam int size) {
		return farmerVipDesignationService.getAllFarmerVipDesignationPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerVipDesignation(@RequestBody FarmerVipDesignation farmerVipDesignation) {
		return farmerVipDesignationService.addFarmerVipDesignation(farmerVipDesignation);
	}// addAllFarmerVipDesignation

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerVipDesignationById(@PathVariable int id,
			@RequestBody FarmerVipDesignation farmerVipDesignation) {
		return farmerVipDesignationService.updateFarmerVipDesignationById(id, farmerVipDesignation);
	}// updateFarmerVipDesignationById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerVipDesignationService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerVipDesignationService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerVipDesignationService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerVipDesignationById(@PathVariable int id) {
		return farmerVipDesignationService.deleteFarmerVipDesignationById(id);
	}// deleteFarmerVipDesignationById

	@GetMapping("/{id}")
	public FarmerVipDesignation findFarmerVipDesignationById(@PathVariable int id) {
		return farmerVipDesignationService.findFarmerVipDesignationById(id);
	}// findFarmerVipDesignationById

}// FarmerVipDesignationController