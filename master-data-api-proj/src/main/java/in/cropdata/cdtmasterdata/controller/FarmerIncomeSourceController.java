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
import in.cropdata.cdtmasterdata.model.FarmerIncomeSource;
import in.cropdata.cdtmasterdata.service.FarmerIncomeSourceService;

@RestController
@RequestMapping("/farmer/income-source")
public class FarmerIncomeSourceController {
	
	@Autowired
	private FarmerIncomeSourceService farmerIncomeSourceService;
	
	@GetMapping("/list")
	public List<FarmerIncomeSource> getAllFarmerIncomeSource() {
		return farmerIncomeSourceService.getAllFarmerIncomeSource();
	}// getAllFarmerIncomeSource

	@GetMapping("/paginatedList")
	public Page<FarmerIncomeSource> getAllFarmerIncomeSourcePaginated(@RequestParam int page, @RequestParam int size) {
		return farmerIncomeSourceService.getAllFarmerIncomeSourcePaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerIncomeSource(@RequestBody FarmerIncomeSource FarmerIncomeSource) {
		return farmerIncomeSourceService.addFarmerIncomeSource(FarmerIncomeSource);
	}// addAllFarmerIncomeSource

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerIncomeSourceById(@PathVariable int id,
			@RequestBody FarmerIncomeSource FarmerIncomeSource) {
		return farmerIncomeSourceService.updateFarmerIncomeSourceById(id, FarmerIncomeSource);
	}// updateFarmerIncomeSourceById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerIncomeSourceService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerIncomeSourceService.finalApproveById(id);
	}// finalApproveById
	
	   @PutMapping("/{id}/reject")
	   public ResponseMessage rejectById(@PathVariable int id) {
	     return farmerIncomeSourceService.rejectById(id);
	   }// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerIncomeSourceById(@PathVariable int id) {
		return farmerIncomeSourceService.deleteFarmerIncomeSourceById(id);
	}// deleteFarmerIncomeSourceById

	@GetMapping("/{id}")
	public FarmerIncomeSource findFarmerIncomeSourceById(@PathVariable int id) {
		return farmerIncomeSourceService.findFarmerIncomeSourceById(id);
	}// findFarmerIncomeSourceById
	

}
