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
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankBranchInf;
import in.cropdata.cdtmasterdata.model.GeneralBankBranch;
import in.cropdata.cdtmasterdata.service.GeneralBankBranchService;

@RestController
@RequestMapping("/general/bank-branch")
public class GeneralBankBranchController {

	@Autowired
	private GeneralBankBranchService generalBankBranchService;

	@GetMapping("/list")
	public List<GeneralBankBranch> getAllGeneralBank() {
		return generalBankBranchService.getAllGeneralBankBranch();
	}// getAllGeneralBank

	@GetMapping()
	public Page<GeneralBankBranchInf> getAllGeneralBankPaginated(@RequestParam("page") int page,
			                                                     @RequestParam("size") int size,
																 @RequestParam("isValid") int isValid,
																 @RequestParam(required = false, defaultValue = "") String searchText,
																 @RequestParam(required = false,defaultValue = "0") String missing){
		return generalBankBranchService.getAllGeneralBankBranchPaginated(page, size,searchText, isValid,missing);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralBank(@RequestBody GeneralBankBranch GeneralBankBranch) {
		return generalBankBranchService.addGeneralBankBranch(GeneralBankBranch);
	}// addAllGeneralBank

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralBankById(@PathVariable int id,
			@RequestBody GeneralBankBranch GeneralBankBranch) {
		return generalBankBranchService.updateGeneralBankBranchById(id, GeneralBankBranch);
	}// updateGeneralBankById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalBankBranchService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalBankBranchService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralBankById(@PathVariable int id) {
		return generalBankBranchService.deleteGeneralBankBranchById(id);
	}// deleteGeneralBankById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalBankBranchService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralBankBranch findGeneralBankById(@PathVariable int id) {
		return generalBankBranchService.findGeneralBankBranchById(id);
	}// findGeneralBankById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return generalBankBranchService.moveToMaster(id);
	}

}
