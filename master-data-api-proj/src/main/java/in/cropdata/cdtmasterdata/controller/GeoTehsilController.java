package in.cropdata.cdtmasterdata.controller;

import java.util.List;
import java.util.Map;

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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoTehsilInfDto;
import in.cropdata.cdtmasterdata.model.GeoTehsil;
import in.cropdata.cdtmasterdata.service.GeoTehsilService;

@RestController
@RequestMapping("/geo/tehsil")
public class GeoTehsilController {

	@Autowired
	private GeoTehsilService geoTehsilService;

	@GetMapping("/list")
	public List<GeoTehsilInfDto> getAllGeoTehsil() {
		return geoTehsilService.getAllGeoTehsil();
	}// getAllGeoTehsil

	@GetMapping()
	public Page<GeoTehsilInfDto> getAllGeoTehsilPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return geoTehsilService.getAllGeoTehsilPaginated(page, size, searchText);
	}// getAllGeoTehsilPaginated

	@GetMapping("/district-code/{districtCode}")
	public List<GeoTehsilInfDto> getAllGeoTehsilByDictrictCode(@PathVariable int districtCode) {
		return geoTehsilService.getAllGeoTehsilByDistrictCode(districtCode);
	}// getAllGeoTehsil

	@PostMapping("/add")
	public ResponseMessage addGeoTehsil(@RequestBody GeoTehsil geoTehsil) {
		return geoTehsilService.addGeoTehsil(geoTehsil);
	}// addAllGeoTehsil

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoTehsilById(@PathVariable int id, @RequestBody GeoTehsil geoTehsil) {
		return geoTehsilService.updateGeoTehsilById(id, geoTehsil);
	}// updateGeoTehsilById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoTehsilService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoTehsilService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoTehsilService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoTehsilById(@PathVariable int id) {
		return geoTehsilService.deleteGeoTehsilById(id);
	}// deleteGeoTehsilById

	@GetMapping("/{id}")
	public GeoTehsil findGeoTehsilById(@PathVariable int id) {
		return geoTehsilService.findGeoTehsilById(id);
	}// findGeoTehsilById

	/**
	 * This API is used to get all Tehsil alias data with pagination.
	 * 
	 * @param page the page number for fetching the alias data
	 * @param size the no of records to be present in a single page
	 * 
	 * @return the response data in pageable list
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias")
	public List<Object> getTehsilAliasWithPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return geoTehsilService.getTehsilAliasWithPage(page, size);
	}

	/**
	 * This API is used to tag Tehsil with respective alias.
	 * 
	 * @param tehsilAlias the alias data to be tagged against Tehsil
	 * 
	 * @return the response data in {@link ResponseMessage}
	 * 
	 * @author PranaySK
	 */
	@PostMapping("/alias/tag")
	public ResponseMessage tagTehsilAlias(@RequestBody Map<String, Object> tehsilAlias) {
		return geoTehsilService.tagTehsilAlias(tehsilAlias);
	}

}// GeoTehsilController
