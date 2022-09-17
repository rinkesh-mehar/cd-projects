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
import in.cropdata.cdtmasterdata.dto.interfaces.FarmerInsuranceCompanyInfDto;
import in.cropdata.cdtmasterdata.model.FarmerInsuranceCompany;
import in.cropdata.cdtmasterdata.service.FarmerInsuranceCompanyService;

@RestController
@RequestMapping("/farmer/insurance-company")
public class FarmerInsuranceCompanyController {

	@Autowired
	private FarmerInsuranceCompanyService farmerInsuranceCompanyService;

	@GetMapping("/list")
	public List<FarmerInsuranceCompany> getAllFarmerInsuranceCompany() {
		return farmerInsuranceCompanyService.getAllFarmerInsuranceCompany();
	}// getAllFarmerInsuranceCompany

	@GetMapping()
	public Page<FarmerInsuranceCompanyInfDto> getAllInsuranceCompany(@RequestParam("page") int page,
																	 @RequestParam("size") int size,
																	 @RequestParam("isValid") int isValid,
																	 @RequestParam(required = false, defaultValue = "") String searchText)
	{
		return farmerInsuranceCompanyService.getAllFarmerLoanSourcePaginated(page, size, searchText, isValid);
	}// getAllInsuranceCompany

	@PostMapping("/add")
	public ResponseMessage addFarmerInsuranceCompany(@RequestBody FarmerInsuranceCompany farmerInsuranceCompany) {
		return farmerInsuranceCompanyService.addFarmerInsuranceCompany(farmerInsuranceCompany);
	}// addAllFarmerInsuranceCompany

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerInsuranceCompanyById(@PathVariable int id,
			@RequestBody FarmerInsuranceCompany farmerInsuranceCompany) {
		return farmerInsuranceCompanyService.updateFarmerInsuranceCompanyById(id, farmerInsuranceCompany);
	}// updateFarmerInsuranceCompanyById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerInsuranceCompanyService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerInsuranceCompanyService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerInsuranceCompanyService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerInsuranceCompanyById(@PathVariable int id) {
		return farmerInsuranceCompanyService.deleteFarmerInsuranceCompanyById(id);
	}// deleteFarmerInsuranceCompanyById

	@GetMapping("/{id}")
	public FarmerInsuranceCompany findFarmerInsuranceCompanyById(@PathVariable int id) {
		return farmerInsuranceCompanyService.findFarmerInsuranceCompanyById(id);
	}// findFarmerInsuranceCompanyById

}// FarmerInsuranceCompanyController
