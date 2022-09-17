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
import in.cropdata.cdtmasterdata.model.GeneralDropReason;
import in.cropdata.cdtmasterdata.service.GeneralDropReasonService;

@RestController
@RequestMapping("/general/drop-reason")
public class GeneralDropReasonController {
	
	@Autowired
	private GeneralDropReasonService generalDropReasonService;
	
	@GetMapping("/list")
	public List<GeneralDropReason> getAllGeneralDropReason() {
		return generalDropReasonService.getAllGeneralDropReason();
	}// getAllGeneralDropReason

	@GetMapping("/paginatedList")
	public Page<GeneralDropReason> getAllGeneralDropReasonPaginated(@RequestParam int page, @RequestParam int size) {
		return generalDropReasonService.getAllGeneralDropReasonPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralDropReason(@RequestBody GeneralDropReason generalDropReason) {
		return generalDropReasonService.addGeneralDropReason(generalDropReason);
	}// addAllGeneralDropReason

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralDropReasonById(@PathVariable int id,
			@RequestBody GeneralDropReason generalDropReason) {
		return generalDropReasonService.updateGeneralDropReasonById(id, generalDropReason);
	}// updateGeneralDropReasonById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalDropReasonService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalDropReasonService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralDropReasonById(@PathVariable int id) {
		return generalDropReasonService.deleteGeneralDropReasonById(id);
	}// deleteGeneralDropReasonById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalDropReasonService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralDropReason findGeneralDropReasonById(@PathVariable int id) {
		return generalDropReasonService.findGeneralDropReasonById(id);
	}// findGeneralDropReasonById
	

}

