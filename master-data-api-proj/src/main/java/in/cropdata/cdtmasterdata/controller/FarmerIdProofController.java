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
import in.cropdata.cdtmasterdata.model.FarmerIdProof;
import in.cropdata.cdtmasterdata.service.FarmerIdProofService;

@RestController
@RequestMapping("/farmer/idproof")
public class FarmerIdProofController {

	@Autowired
	private FarmerIdProofService farmerIdProofService;

	@GetMapping("/list")
	public List<FarmerIdProof> getAllFarmerIdProof() {
		return farmerIdProofService.getAllFarmerIdProof();
	}// getAllFarmerIdProof
	
	@GetMapping("/paginatedList")
	public Page<FarmerIdProof> getFarmerIdProofListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerIdProofService.getFarmerIdProofListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerIdProof(@RequestBody FarmerIdProof farmerIdProof) {
		return farmerIdProofService.addFarmerIdProof(farmerIdProof);
	}// addFarmerIdProof

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerIdProofById(@PathVariable int id, @RequestBody FarmerIdProof farmerIdProof) {
		return farmerIdProofService.updateFarmerIdProofById(id, farmerIdProof);
	}// updateFarmerIdProofById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerIdProofService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerIdProofService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerIdProofService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerIdProofById(@PathVariable int id) {
		return farmerIdProofService.deleteFarmerIdProofById(id);
	}// deleteFarmerIdProofById

	@GetMapping("/{id}")
	public FarmerIdProof findFarmerIdProofById(@PathVariable int id) {
		return farmerIdProofService.findFarmerIdProofById(id);
	}// findFarmerIdProofById

}// FarmerIdProofController
