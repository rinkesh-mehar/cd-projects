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
import in.cropdata.cdtmasterdata.model.FarmerProxyRelationType;
import in.cropdata.cdtmasterdata.service.FarmerProxyRelationTypeService;

@RestController
@RequestMapping("/farmer/proxy-relation-type")
public class FarmerProxyRelationTypeControllere {
	
	@Autowired
	private FarmerProxyRelationTypeService farmerProxyRelationTypeService;

	@GetMapping("/list")
	public List<FarmerProxyRelationType> getAllFarmerProxyRelationType() {
		return farmerProxyRelationTypeService.getAllFarmerProxyRelationType();
	}// getAllFarmerProxyRelationType
	
	@GetMapping("/paginatedList")
	public Page<FarmerProxyRelationType> getFarmerProxyRelationTypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return farmerProxyRelationTypeService.getFarmerProxyRelationTypeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerProxyRelationType(@RequestBody FarmerProxyRelationType farmerProxyRelationType) {
		return farmerProxyRelationTypeService.addFarmerProxyRelationType(farmerProxyRelationType);
	}// addAllFarmerProxyRelationType

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerProxyRelationTypeById(@PathVariable int id,
			@RequestBody FarmerProxyRelationType farmerProxyRelationType) {
		return farmerProxyRelationTypeService.updateFarmerProxyRelationTypeById(id, farmerProxyRelationType);
	}// updateFarmerProxyRelationTypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerProxyRelationTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerProxyRelationTypeService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerProxyRelationTypeService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerProxyRelationTypeById(@PathVariable int id) {
		return farmerProxyRelationTypeService.deleteFarmerProxyRelationTypeById(id);
	}// deleteFarmerProxyRelationTypeById

	@GetMapping("/{id}")
	public FarmerProxyRelationType findFarmerProxyRelationTypeById(@PathVariable int id) {
		return farmerProxyRelationTypeService.findFarmerProxyRelationTypeById(id);
	}// findFarmerProxyRelationTypeById
}
