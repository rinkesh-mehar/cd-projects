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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalBankInfDto;
import in.cropdata.cdtmasterdata.model.RegionalBank;
import in.cropdata.cdtmasterdata.service.RegionalBankService;

@RestController
@RequestMapping("/regional/bank")
public class RegionalBankController {

	@Autowired
	private RegionalBankService regionalBankService;

	@GetMapping("/list")
	public List<RegionalBank> getAllRegionalBank() {
		return regionalBankService.getAllAgriRegionalBank();
	}// getAllRegionalBank

	@GetMapping()
	public Page<RegionalBankInfDto> getAllRegionalBankPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size,@RequestParam("isValid") int isValid, @RequestParam(required = false,defaultValue = "0") String missing,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return regionalBankService.getAllRegionalBankPaginated(page, size,missing,searchText, isValid);
	}// getAllRegionalLanguagePaginated
	
	
	@PostMapping("/add")
	public ResponseMessage addRegionalBank(@RequestBody RegionalBank regionalBank) {
		return regionalBankService.addRegionalBank(regionalBank);
	}// addRegionalBank

	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionalBankById(@PathVariable int id,
			@RequestBody RegionalBank regionalBank) {
		return regionalBankService.updateRegionalBankById(id, regionalBank);
	}// updateRegionalBankById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionalBankService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionalBankService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionalBankService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionalBankById(@PathVariable int id) {
		return regionalBankService.deleteRegionalBankById(id);
	}// deleteRegionalBankById

	@GetMapping("/{id}")
	public RegionalBank findRegionalBankById(@PathVariable int id) {
		return regionalBankService.findRegionalBankById(id);
	}// findRegionalBankById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionalBankService.moveToMaster(id);
	}

}//getAllRegionalBank
