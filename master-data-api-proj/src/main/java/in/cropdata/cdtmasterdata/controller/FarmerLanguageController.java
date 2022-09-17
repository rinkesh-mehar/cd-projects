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
import in.cropdata.cdtmasterdata.model.FarmerLanguage;
import in.cropdata.cdtmasterdata.service.FarmerLanguageService;

@RestController
@RequestMapping("/farmer/language")
public class FarmerLanguageController {

	@Autowired
	private FarmerLanguageService farmerLanguageService;

	@GetMapping("/list")
	public List<FarmerLanguage> getAllFarmerLanguage() {
		return farmerLanguageService.getAllFarmerLanguage();
	}// getAllFarmerLanguage

	@GetMapping()
	public Page<FarmerLanguage> getAllFarmerLanguagePaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return farmerLanguageService.getAllFarmerLanguagePaginated(page, size);
	}// getAllFarmerLanguagePaginated

	@PostMapping("/add")
	public ResponseMessage addFarmerLanguage(@RequestBody FarmerLanguage farmerLanguage) {
		return farmerLanguageService.addFarmerLanguage(farmerLanguage);
	}// addAllFarmerLanguage

	@PutMapping("/{id}/update")
	public ResponseMessage updateFarmerLanguageById(@PathVariable int id, @RequestBody FarmerLanguage farmerLanguage) {
		return farmerLanguageService.updateFarmerLanguageById(id, farmerLanguage);
	}// updateFarmerLanguageById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return farmerLanguageService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return farmerLanguageService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return farmerLanguageService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteFarmerLanguageById(@PathVariable int id) {
		return farmerLanguageService.deleteFarmerLanguageById(id);
	}// deleteFarmerLanguageById

	@GetMapping("/{id}")
	public FarmerLanguage findFarmerLanguageById(@PathVariable int id) {
		return farmerLanguageService.findFarmerLanguageById(id);
	}// findFarmerLanguageById

}// FarmerLanguageController
