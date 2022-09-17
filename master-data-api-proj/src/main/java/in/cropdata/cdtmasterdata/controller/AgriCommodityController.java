package in.cropdata.cdtmasterdata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.AgroCommodityDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.service.AgriCommodityService;
import in.cropdata.cdtmasterdata.website.dto.ProductsAndServicesDTO;

@RestController()
//@CrossOrigin("*")
@RequestMapping("/agri/commodity")
public class AgriCommodityController {

	@Autowired
	private AgriCommodityService agriCommodityService;

	@GetMapping("/list")
	public List<AgriCommodityInfo> getAllAgriCommodities() {
		return agriCommodityService.getAllAgriCommodities();
	}// getAllAgriCommodities

	@GetMapping()
	public Page<AgriCommodityInfo> getAllAgriCommodityPeginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriCommodityService.getAllAgriCommodityPeginated(page, size, searchText);
	}// getAllAgriCommodityPeginated

	@PostMapping("/add")
	public ResponseMessage addAgriCommodity(@ModelAttribute AgroCommodityDTO agroCommodityDTO) {
		return agriCommodityService.addAgriCommodity(agroCommodityDTO);
	}// addAgriCommodity

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriCommodityById(@PathVariable int id, @ModelAttribute AgroCommodityDTO agroCommodityDTO) {
		return agriCommodityService.updateAgriCommodityById(id, agroCommodityDTO);
	}// updateAgriCommodities

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriCommodityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriCommodityService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriCommoditiesById(@PathVariable int id) {
		return agriCommodityService.deleteAgriCommodityById(id);
	}// deleteAgriCommoditiesById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriCommodityService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriCommodity findAgriCommodityById(@PathVariable int id) {
		return agriCommodityService.findAgriCommodityById(id);
	}// findAgriCommodityById

	@GetMapping("/commodity")
	public List<Map<String, Object>> findCommodityBySeasonId(
			@RequestParam(name = "stateCode", required = false, defaultValue = "0") int stateCode,
			@RequestParam(name = "seasonId", defaultValue = "0") int seasonId) {
		if (stateCode > 0) {
			return agriCommodityService.findCommodityByStateCodeAndSeasonId(stateCode, seasonId);
		} else {
			return agriCommodityService.findCommodityBySeasonId(seasonId);
		}

	}// findCommodityBySeasonId

	/**
	 * This API is used to get all Commodities list according to the given state.
	 * 
	 * @param stateCode the state code for which the commodity list to be fetched
	 * 
	 * @return the response data in map
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/{stateCode}/list")
	public List<Map<String, Object>> getCommoditiesByState(@PathVariable int stateCode) {
		if (stateCode <= 0) {
			throw new InvalidDataException("State Code can not be zero or less than zero");
		}

		return agriCommodityService.getCommoditiesByStateCode(stateCode);
	}

	@GetMapping("/commodity/phenophase")
	public List<AgriFieldActivityInfDto> findPhenophaseByCommodity(@RequestParam("commodityId") int commodityId) {
		return agriCommodityService.findPhenophaseByCommodity(commodityId);
	}// findPhenophaseByCommodity

	/**
	 * This API is used to get all Commodity and commodity alias data.
	 * 
	 * @return <code>Map</code> the response data in map
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias")
	public Map<String, Object> getCommodityAlias() {
		return agriCommodityService.getCommodityAlias();
	}

	@GetMapping("/alias/page")
	public List<Object> getCommodityAliasWithPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return agriCommodityService.getCommodityAliasWithPage(page, size);
	}

	/**
	 * This API is used to get tag commodity with respective alias.
	 * 
	 * @return <code>ResponseMessage</code> the response data in
	 *         {@link ResponseMessage}
	 * 
	 * @author PranaySK
	 */
	@PostMapping("/alias/tag")
	public ResponseMessage tagCommodityAlias(@RequestBody Map<String, Object> commodityAlias) {
		return agriCommodityService.tagCommodityAlias(commodityAlias);
	}

}// AgriCommodityController
