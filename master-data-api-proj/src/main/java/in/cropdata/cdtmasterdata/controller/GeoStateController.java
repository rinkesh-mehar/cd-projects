package in.cropdata.cdtmasterdata.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.GeoState;
import in.cropdata.cdtmasterdata.service.GeoStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/geo/state")
public class GeoStateController {

	@Autowired
	private GeoStateService geoStateService;

	/**
	 * @return only Active states
	 */
	@GetMapping("/list")
	public List<GeoStateInfDto> getApprovedGeoState() {
		return geoStateService.getActiveGeoState();
	}// getAllGeoState

	/**
	 * @return return all state's
	 */
	@GetMapping("/total/list")
	public List<GeoStateInfDto> getAllGeoState() {

		return geoStateService.getAllGeoState();
	}
	
	@GetMapping("/paginatedList")
	public Page<GeoStateInfDto> getStateListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return geoStateService.getStateListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addGeoState(@RequestBody GeoState geoState) {
		return geoStateService.addGeoState(geoState);
	}// addAllGeoState

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoStateById(@PathVariable int id, @RequestBody GeoState geoState) {
		return geoStateService.updateGeoStateById(id, geoState);
	}// updateGeoStateById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoStateService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoStateService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoStateService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoStateById(@PathVariable int id) {
		return geoStateService.deleteGeoStateById(id);
	}// deleteGeoStateById

	@GetMapping("/{id}")
	public GeoState findGoeStateById(@PathVariable int id) {
		return geoStateService.findGeoStateById(id);

	}// findGoeStateById

	/**
	 * This API is used to get all State alias data.
	 * 
	 * @return <code>Map</code> the response data in map
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias")
	public Map<String, Object> getStateAlias() {
		return geoStateService.getStateAlias();
	}

	/**
	 * This API is used to get all State alias data with pagination.
	 * 
	 * @return <code>List</code> the response data in pageable list
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias/page")
	public List<Object> getCommodityAliasWithPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return geoStateService.getCommodityAliasWithPage(page, size);
	}

	/**
	 * This API is used to tag State with respective alias.
	 * 
	 * @return <code>ResponseMessage</code> the response data in
	 *         {@link ResponseMessage}
	 * 
	 * @author PranaySK
	 */
	@PostMapping("/alias/tag")
	public ResponseMessage tagStateAlias(@RequestBody Map<String, Object> stateAlias) {
		return geoStateService.tagStateAlias(stateAlias);
	}

	@GetMapping("/list/{countryCode}")
	public List<GeoState> getStatesByCountryCode(@PathVariable Integer countryCode){
		return geoStateService.getStatesByCountryCode(countryCode);
	}

}// GeoStateController
