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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFertilizerInfDto;
import in.cropdata.cdtmasterdata.model.AgriFertilizer;
import in.cropdata.cdtmasterdata.service.AgriFertilizerService;

@RestController
@RequestMapping("/agri/fertilizer")
public class AgriFertilizerController {

	@Autowired
	private AgriFertilizerService agriFertilizerService;

	@GetMapping("/list")
	public List<AgriFertilizerInfDto> getAllAgriFertilizer() {
		return agriFertilizerService.getAllAgriFertilizer();
	}// getAllAgriFertilizer

	@GetMapping()
	public Page<AgriFertilizerInfDto> getAllAgriFertilizerPaginated(@RequestParam("page") int page,
																	@RequestParam("size") int size,
																	@RequestParam("isValid") int isValid,
																	@RequestParam(required = false, defaultValue = "") String searchText,
																	@RequestParam(required = false,defaultValue = "0") String missing,
																	  @RequestParam(required = false, defaultValue = "") String stateCode,
																	  @RequestParam(required = false, defaultValue = "") String seasonId,
																	  @RequestParam(required = false, defaultValue = "") String doseFactorId,
																	  @RequestParam(required = false, defaultValue = "") String commodityId,
																	  @RequestParam(required = false, defaultValue = "") String name,
																	  @RequestParam(required = false, defaultValue = "") String filter)
	{
		return agriFertilizerService.getAllAgriFertilizerPaginated(page, size, searchText, isValid,missing,stateCode,seasonId,doseFactorId,commodityId,name,filter);
	}// getAllAgriFertilizerPaginated
	
//	@GetMapping()
//	public Page<AgriFertilizerInfDto> getAllAgriFertilizerPaginated(@RequestParam int page, @RequestParam int size,@RequestParam String searchText) {
//		return agriFertilizerService.getAllAgriFertilizerPaginated(page, size,searchText);
//	}// getAllAgriFertilizerPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriFertilizer(@RequestBody AgriFertilizer agriFertilizer) {
		return agriFertilizerService.addAgriFertilizer(agriFertilizer);
	}// addAgriFertilizer

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriFertilizerById(@PathVariable int id, @RequestBody AgriFertilizer agriFertilizer) {
		return agriFertilizerService.updateAgriFertilizerById(id, agriFertilizer);
	}// updateAgriFertilizerById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriFertilizerService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriFertilizerService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriFertilizerById(@PathVariable int id) {
		return agriFertilizerService.deleteAgriFertilizerById(id);
	}// deleteAgriFertilizerById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriFertilizerService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriFertilizer findAgriFertilizerById(@PathVariable int id) {
		return agriFertilizerService.findAgriFertilizerById(id);
	}// findAgriFertilizerById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriFertilizerService.moveToMaster(id);
	}

}// AgriFertilizerController
