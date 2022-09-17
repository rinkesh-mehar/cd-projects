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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoDistrictInfDto;
import in.cropdata.cdtmasterdata.model.GeoDistrict;
import in.cropdata.cdtmasterdata.service.GeoDistrictService;

@RestController
@RequestMapping("/geo/district")
public class GeoDistrictController {

	@Autowired
	private GeoDistrictService geoDistrictService;

	@GetMapping("/list")
	public List<GeoDistrictInfDto> getAllGeoDistrict() {
		return geoDistrictService.getAllGeoDistrict();
	}// getAllGeoDistrict

	@GetMapping()
	public Page<GeoDistrictInfDto> getAllGeoDistrictPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return geoDistrictService.getAllGeoDistrictPaginated(page, size, searchText);
	}// getAllGeoDistrictPaginated

	@GetMapping("/state-code/{stateCode}")
	public List<GeoDistrict> getAllGeoDistrictbyStateCode(@PathVariable int stateCode) {
		return geoDistrictService.getAllGeoDistrictByStateCode(stateCode);
	}// getAllGeoDistrict

	@PostMapping("/add")
	public ResponseMessage addAllGeoDistrict(@RequestBody GeoDistrict geoDistrict) {
		return geoDistrictService.addGeoDistrict(geoDistrict);
	}// addAllGeoDistrict

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoDistrictById(@PathVariable int id, @RequestBody GeoDistrict geoDistrict) {
		return geoDistrictService.updateGeoDistrictById(id, geoDistrict);
	}// updateGeoDistrictById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoDistrictService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoDistrictService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoDistrictService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoDistrictById(@PathVariable int id) {
		return geoDistrictService.deleteGeoDistrictById(id);
	}// deleteGeoDistrictById

	@GetMapping("/{id}")
	public GeoDistrict findGeoDistrictById(@PathVariable int id) {
		return geoDistrictService.findGeoDistrictById(id);
	}// findGeoDistrictById

	/**
	 * This API is used to get all States and district alias data.
	 * 
	 * @return <code>Map</code> the response data in map
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias/page")
	public List<Object> getDistrictAliasPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return geoDistrictService.getGeoDistrictAliasWithPage(page, size);
	}
	/*
	 * @GetMapping("/alias") public Map<String, Object> getDistrictAlias() { return
	 * geoDistrictService.getDistrictAlias(); }
	 */

	/**
	 * This API is used to get tag district with respective alias.
	 * 
	 * @return <code>ResponseMessage</code> the response data in
	 *         {@link ResponseMessage}
	 * 
	 * @author PranaySK
	 */
	@PostMapping("/alias/tag")
	public ResponseMessage tagDistrictAlias(@RequestBody Map<String, Object> districtAlias) {
		return geoDistrictService.tagDistrictWithAlias(districtAlias);
	}
}
