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
import in.cropdata.cdtmasterdata.model.FarmerVipStatus;
import in.cropdata.cdtmasterdata.service.FarmerVipStatusService;

@RestController
@RequestMapping("/farmer/vip-status")
public class FarmerVipStatusController {
	
	@Autowired
	private FarmerVipStatusService farmerVipStatusService;
	
	@GetMapping("/list")
	public List<FarmerVipStatus> getAllFarmerVipStatus() {
		return farmerVipStatusService.getAllFarmerVipStatus();
	}// getAllFarmerVipStatus
	
	@GetMapping("/paginatedList")
	public Page<FarmerVipStatus> getFarmerVipStatusListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerVipStatusService.getFarmerVipStatusListByPagenation(page, size, searchText);
	}


	@PostMapping("/add")
	public ResponseMessage addFarmerVipStatus(@RequestBody FarmerVipStatus farmerVipStatus) {
		return farmerVipStatusService.addFarmerVipStatus(farmerVipStatus);
	}// addAllFarmerVipStatus

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerVipStatusById(@PathVariable int id,
			@RequestBody FarmerVipStatus farmerVipStatus) {
		return farmerVipStatusService.updateFarmerVipStatusById(id, farmerVipStatus);
	}// updateFarmerVipStatusById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerVipStatusService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerVipStatusService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerVipStatusService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerVipStatusById(@PathVariable int id) {
		return farmerVipStatusService.deleteFarmerVipStatusById(id);
	}// deleteFarmerVipStatusById

	@GetMapping("/{id}")
	public FarmerVipStatus findFarmerVipStatusById(@PathVariable int id) {
		return farmerVipStatusService.findFarmerVipStatusById(id);
	}// findFarmerVipStatusById

}
