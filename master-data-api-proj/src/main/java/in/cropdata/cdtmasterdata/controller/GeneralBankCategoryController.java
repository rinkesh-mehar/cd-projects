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
import in.cropdata.cdtmasterdata.model.GeneralBankCategory;
import in.cropdata.cdtmasterdata.service.GeneralBankCategoryService;

@RestController
@RequestMapping("/general/bank-category")
public class GeneralBankCategoryController {

	@Autowired
	private GeneralBankCategoryService generalBankCategoryService;

	@GetMapping("/list")
	public List<GeneralBankCategory> getAllGeneralBankCategory() {
		return generalBankCategoryService.getAllGeneralBankCategory();
	}// getAllGeneralBankCategory

	@GetMapping("/paginatedList")
	public Page<GeneralBankCategory> getAllGeneralBankCategoryPaginated(@RequestParam int page, @RequestParam int size) {
		return generalBankCategoryService.getAllGeneralBankCategoryPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralBankCategory(@RequestBody GeneralBankCategory generalBankCategory) {
		return generalBankCategoryService.addGeneralBankCategory(generalBankCategory);
	}// addAllGeneralBankCategory

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralBankCategoryById(@PathVariable int id, @RequestBody GeneralBankCategory generalBankCategory) {
		return generalBankCategoryService.updateGeneralBankCategoryById(id, generalBankCategory);
	}// updateGeneralBankCategoryById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalBankCategoryService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalBankCategoryService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralBankCategoryById(@PathVariable int id) {
		return generalBankCategoryService.deleteGeneralBankCategoryById(id);
	}// deleteGeneralBankCategoryById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalBankCategoryService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralBankCategory findGeneralBankCategoryById(@PathVariable int id) {
		return generalBankCategoryService.findGeneralBankCategoryById(id);
	}// findGeneralBankCategoryById

}
