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
import in.cropdata.cdtmasterdata.model.GeneralCompany;
import in.cropdata.cdtmasterdata.service.GeneralCompanyService;

//public class GeneralCompanyController {
//
//}

@RestController
@RequestMapping("/general/company")
public class GeneralCompanyController {

	@Autowired
	private GeneralCompanyService generalCompanyService;

	@GetMapping("/list")
	public List<GeneralCompany> getAllGeneralCompany() {
		return generalCompanyService.getAllGeneralCompany();
	}// getAllGeneralCompany

	@GetMapping("/paginatedList")
	public Page<GeneralCompany> getAllGeneralCompanyPaginated(@RequestParam int page, @RequestParam int size) {
		return generalCompanyService.getAllGeneralCompanyPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralCompany(@RequestBody GeneralCompany generalCompany) {
		return generalCompanyService.addGeneralCompany(generalCompany);
	}// addAllGeneralCompany

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralCompanyById(@PathVariable int id, @RequestBody GeneralCompany generalCompany) {
		return generalCompanyService.updateGeneralCompanyById(id, generalCompany);
	}// updateGeneralCompanyById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalCompanyService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalCompanyService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralCompanyById(@PathVariable int id) {
		return generalCompanyService.deleteGeneralCompanyById(id);
	}// deleteGeneralCompanyById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalCompanyService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralCompany findGeneralCompanyById(@PathVariable int id) {
		return generalCompanyService.findGeneralCompanyById(id);
	}// findGeneralCompanyById

}