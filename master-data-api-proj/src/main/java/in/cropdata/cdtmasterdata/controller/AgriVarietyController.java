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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyInfDto;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.AgriVariety;
import in.cropdata.cdtmasterdata.service.AgriVarietyService;

@RestController
@RequestMapping("/agri/variety")
public class AgriVarietyController {

	@Autowired
	private AgriVarietyService agriVarietyService;

	@GetMapping("/list")
	public List<AgriVarietyInfDto> getAllAgriVariety() {
		return agriVarietyService.getAllAgriVariety();
	}// getAllAgriVariety

	@GetMapping("/{commodityId}/list")
	public List<AgriVarietyInfDto> getAllAgriVarietyByCommodity(@PathVariable int commodityId) {
		return agriVarietyService.getAllAgriVarietyByCommodityId(commodityId);
	}// getAllAgriVariety

	/**
	 * This API is used to get all Variety list according to the given state code
	 * and commodity Id.
	 * 
	 * @param stateCode   the state code for which the variety list to be fetched
	 * @param commodityId the commodity id for which the variety list to be fetched
	 * 
	 * @return the response data in list of {@link AgriVarietyInfDto}
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/{stateCode}/{commodityId}/list")
	public List<AgriVarietyInfDto> getAllAgriVarietyByStateAndCommodity(@PathVariable int stateCode,
			@PathVariable int commodityId) {
		if (stateCode <= 0 || commodityId <= 0) {
			throw new InvalidDataException("State Code or Commodity Id can not be zero or less than zero");
		}

		return agriVarietyService.getAllAgriVarietyByStateAndCommodity(stateCode, commodityId);
	}

	@GetMapping("/{stateCode}/{districtCode}/{seasonId}/{commodityId}/list")
	public List<AgriVarietyInfDto> getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(@PathVariable int stateCode,@PathVariable int districtCode,
			@PathVariable int seasonId,@PathVariable int commodityId) {
		return agriVarietyService.getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(stateCode,districtCode,seasonId,commodityId);
	}
	
	@GetMapping()
	public Page<AgriVarietyInfDto> getAllAgriVarietyPaginated(@RequestParam("page") int page,
															  @RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText,
															  @RequestParam("isValid") int isValid,
															  @RequestParam(required = false, defaultValue = "0") String missing,
															  @RequestParam(required = false, defaultValue = "") String commodityId,
															  @RequestParam(required = false, defaultValue = "") String hsCodeId,
															  @RequestParam(required = false, defaultValue = "") String domesticRestrictions,
															  @RequestParam(required = false, defaultValue = "") String internationalRestrictions,
															  @RequestParam(required = false, defaultValue = "") String filter)
	{
		return agriVarietyService.getAllAgriVarietyPaginated(page, size, searchText, missing, isValid, commodityId, hsCodeId, domesticRestrictions, internationalRestrictions,filter);
	}// getAllAgriVarietyPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriVariety(@RequestBody AgriVariety agriVariety) {
		return agriVarietyService.addAgriVariety(agriVariety);
	}// addAgriVariety

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriVarietyById(@PathVariable int id, @RequestBody AgriVariety agriVariety) {
		return agriVarietyService.updateAgriVarietyById(id, agriVariety);
	}// updateAgriVarietyById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriVarietyService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriVarietyService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriVarietyById(@PathVariable int id) {
		return agriVarietyService.deleteAgriVarietyById(id);
	}// deleteAgriVarietyById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriVarietyService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriVariety findAgriVarietyById(@PathVariable int id) {
		return agriVarietyService.findAgriVarietyById(id);
	}// findAgriVarietyById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriVarietyService.moveToMaster(id);
	}

}// AgriVarietyController
