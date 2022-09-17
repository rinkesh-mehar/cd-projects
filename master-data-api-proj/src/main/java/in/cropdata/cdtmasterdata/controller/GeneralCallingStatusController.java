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
import in.cropdata.cdtmasterdata.model.GeneralCallingStatus;
import in.cropdata.cdtmasterdata.service.GeneralCallingStatusService;

@RestController
@RequestMapping("/general/calling-status")
public class GeneralCallingStatusController {
	
	@Autowired
	private GeneralCallingStatusService generalCallingStatusService;
	@GetMapping("/list")
	public List<GeneralCallingStatus> getAllGeneralCallingStatus() {
		return generalCallingStatusService.getAllGeneralCallingStatus();
	}// getAllGeneralCallingStatus

	@GetMapping("/paginatedList")
	public Page<GeneralCallingStatus> getAllGeneralCallingStatusPaginated(@RequestParam int page, @RequestParam int size) {
		return generalCallingStatusService.getAllGeneralCallingStatusPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralCallingStatus(@RequestBody GeneralCallingStatus GeneralCallingStatus) {
		return generalCallingStatusService.addGeneralCallingStatus(GeneralCallingStatus);
	}// addAllGeneralCallingStatus

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralCallingStatusById(@PathVariable int id,
			@RequestBody GeneralCallingStatus GeneralCallingStatus) {
		return generalCallingStatusService.updateGeneralCallingStatusById(id, GeneralCallingStatus);
	}// updateGeneralCallingStatusById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalCallingStatusService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalCallingStatusService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralCallingStatusById(@PathVariable int id) {
		return generalCallingStatusService.deleteGeneralCallingStatusById(id);
	}// deleteGeneralCallingStatusById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalCallingStatusService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralCallingStatus findGeneralCallingStatusById(@PathVariable int id) {
		return generalCallingStatusService.findGeneralCallingStatusById(id);
	}// findGeneralCallingStatusById
	

}

