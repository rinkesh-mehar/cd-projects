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
import in.cropdata.cdtmasterdata.model.FarmerGovtDepartment;
import in.cropdata.cdtmasterdata.service.FarmerGovtDepartmentService;

@RestController
@RequestMapping("/farmer/govt-department")
public class FarmerGovtDepartmentController {

	@Autowired
	private FarmerGovtDepartmentService farmerGovtDepartmentService;

	@GetMapping("/list")
	public List<FarmerGovtDepartment> getAllFarmerGovtDepartment() {
		return farmerGovtDepartmentService.GetAllFarmerGovtDepartment();
	}// getAllFarmerGovtDepartment

	@GetMapping("/paginatedList")
	public Page<FarmerGovtDepartment> getAllFarmerGovtDepartmentPaginated(@RequestParam int page,
			@RequestParam int size) {
		return farmerGovtDepartmentService.getAllFarmerGovtDepartmentPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addFarmerGovtDepartment(@RequestBody FarmerGovtDepartment FarmerGovtDepartment) {
		return farmerGovtDepartmentService.addFarmerGovtDepartment(FarmerGovtDepartment);
	}// addAllFarmerGovtDepartment

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerGovtDepartmentById(@PathVariable int id,
			@RequestBody FarmerGovtDepartment FarmerGovtDepartment) {
		return farmerGovtDepartmentService.updateFarmerGovtDepartmentById(id, FarmerGovtDepartment);
	}// updateFarmerGovtDepartmentById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerGovtDepartmentService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerGovtDepartmentService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerGovtDepartmentService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerGovtDepartmentById(@PathVariable int id) {
		return farmerGovtDepartmentService.deleteFarmerGovtDepartmentById(id);
	}// deleteFarmerGovtDepartmentById

	@GetMapping("/{id}")
	public FarmerGovtDepartment findFarmerGovtDepartmentById(@PathVariable int id) {
		return farmerGovtDepartmentService.findFarmerGovtDepartmentById(id);
	}// findFarmerGovtDepartmentById

}
