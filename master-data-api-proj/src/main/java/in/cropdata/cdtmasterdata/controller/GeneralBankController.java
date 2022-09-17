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
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankInfDto;
import in.cropdata.cdtmasterdata.model.GeneralBank;
import in.cropdata.cdtmasterdata.service.GeneralBankService;


@RestController
@RequestMapping("/general/bank")
public class GeneralBankController {
	
	@Autowired
	private GeneralBankService generalBankService;

	/**
	 *
	 * @return	List of all banks with order ascending
	 */
	@GetMapping("/list")
	public List<GeneralBankInfDto> getAllGeneralBank(){
		return generalBankService.getAllGeneralBank();
	}//getAllGeneralBank

	/**
	 *
	 * @return List of all activated General Banks with order ascending
	 */
	@GetMapping("/active")
	public List<GeneralBankInfDto> getAllActiveBank(){
		return generalBankService.getAllActiveBank();
	}//getAllActiveBank

	@GetMapping()
	public Page<GeneralBankInfDto> getAllGeneralBankPaginated(@RequestParam("page") int page,
															  @RequestParam("size") int size,
															  @RequestParam("isValid") int isValid,
															  @RequestParam(required = false, defaultValue = "") String searchText)
	{
		return generalBankService.getAllGeneralBankPaginated(page, size, searchText,isValid);
	}// getAllGeneralBankPaginated
	
	@PostMapping("/add")
	public ResponseMessage addGeneralBank(@RequestBody GeneralBank generalBank) {
		return generalBankService.addGeneralBank(generalBank);
	}//addGeneralBank
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralBankById(@PathVariable int id, @RequestBody GeneralBank generalBank) {
		return generalBankService.updateGeneralBankById(id, generalBank);
	}//updateGeneralBankById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalBankService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalBankService.finalApproveById(id);
	}// finalApproveById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralBankById(@PathVariable int id) {
		return generalBankService.deleteGeneralBankById(id);
	}//deleteGeneralBankById
	
	@GetMapping("/{id}")
	public GeneralBank getGeneralBankById(@PathVariable int id) {
		return generalBankService.findGeneralBankById(id);
	}//getGeneralBankById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectGeneralBankById(@PathVariable int id) {
		return generalBankService.rejectGeneralBankById(id);
	}

}
