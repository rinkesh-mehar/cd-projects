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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalVarietyInfDto;
import in.cropdata.cdtmasterdata.model.RegionalVariety;
import in.cropdata.cdtmasterdata.service.RegionalVarietyService;

@RestController
@RequestMapping("/regional/variety")
public class RegionalVarietyController {

	@Autowired
	private RegionalVarietyService regionVarietyService;

	@GetMapping("/list")
	public List<RegionalVarietyInfDto> getAllRegionVariety() {
		return regionVarietyService.getAllRegionVariety();
	}// getAllRegionVariety

	@GetMapping()
	public Page<RegionalVarietyInfDto> getAllRegionalVarietyPaginated(@RequestParam("page") int page,
																	  @RequestParam("size") int size,
																	  @RequestParam(required = false,defaultValue = "0") String missing,
																	  @RequestParam("isValid") int isValid,
																	  @RequestParam(required = false, defaultValue = "") String searchText,
																	  @RequestParam(required = false, defaultValue = "") String stateCode,
																	  @RequestParam(required = false, defaultValue = "") String seasonId,
																	  @RequestParam(required = false, defaultValue = "") String commodityId,
																	  @RequestParam(required = false, defaultValue = "") String varietyId,
																	  @RequestParam(required = false, defaultValue = "") String filter)
	{
		return regionVarietyService.getAllRegionalVarietyPaginated(page, size,missing, searchText, isValid,stateCode,seasonId,commodityId,varietyId,filter);

	}// getAllRegionalVarietyPaginated

	@PostMapping("/add")
	public ResponseMessage addRegioVariety(@RequestBody RegionalVariety regionVariety) {
		return regionVarietyService.addRegionVariety(regionVariety);
	}// addRegioVariety

	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionVarietyById(@PathVariable int id, @RequestBody RegionalVariety regionVariety) {
		return regionVarietyService.updateRegionVarietyById(id, regionVariety);
	}// updateRegionVarietyById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionVarietyService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionVarietyService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionVarietyService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionVarietyById(@PathVariable int id) {
		return regionVarietyService.deleteRegionVerietyById(id);
	}// deleteRegionVarietyById

	@GetMapping("/{id}")
	public RegionalVariety findRegionVariety(@PathVariable int id) {
		return regionVarietyService.findregioVarietyById(id);
	}// findRegionVariety
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionVarietyService.moveToMaster(id);
	}

}// RegionVarietyController
