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
import in.cropdata.cdtmasterdata.dto.interfaces.FarmerLoanSourceInfo;
import in.cropdata.cdtmasterdata.model.FarmerLoanSource;
import in.cropdata.cdtmasterdata.service.FarmerLoanSourceService;

@RestController
@RequestMapping("/farmer/loan-source")
public class FarmerLoanSourceController {

	@Autowired
	private FarmerLoanSourceService farmerLoanSourceService;

	@GetMapping("/list")
	public List<FarmerLoanSource> getAllFarmerLoanSource() {
		return farmerLoanSourceService.getAllFarmerLoanSources();
	}// getAllFarmerLoanSource

	@GetMapping()
	public Page<FarmerLoanSourceInfo> getAllFarmerLoanSourcePaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return farmerLoanSourceService.getAllFarmerLoanSourcePaginated(page, size,searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerLoanSource(@RequestBody FarmerLoanSource farmerLoanSource) {
		return farmerLoanSourceService.addFarmerLoanSource(farmerLoanSource);
	}// addAllFarmerLoanSource

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerLoanSourceById(@PathVariable int id,
			@RequestBody FarmerLoanSource farmerLoanSource) {
		return farmerLoanSourceService.updateFarmerLoanSourceById(id, farmerLoanSource);
	}// updateFarmerLoanSourceById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerLoanSourceService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerLoanSourceService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerLoanSourceService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerLoanSourceById(@PathVariable int id) {
		return farmerLoanSourceService.deleteFarmerLoanSourceById(id);
	}// deleteFarmerLoanSourceById

	@GetMapping("/{id}")
	public FarmerLoanSource findFarmerLoanSourceById(@PathVariable int id) {
		return farmerLoanSourceService.findFarmerLoanSourceById(id);
	}// findFarmerLoanSourceById

}// FarmerLoanSourceController
