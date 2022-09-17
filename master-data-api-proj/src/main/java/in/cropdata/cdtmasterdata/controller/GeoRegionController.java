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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoRegionInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStateInf;
import in.cropdata.cdtmasterdata.model.GeoRegion;
import in.cropdata.cdtmasterdata.service.GeoRegionService;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;

@RestController
@RequestMapping("/geo/region")
public class GeoRegionController {

	@Autowired
	private GeoRegionService geoRegionService;

	@GetMapping("/list")
	public List<GeoRegionInfDto> getAllGeoRegion() {
		return geoRegionService.getAllGeoRegion();
	}// getAllGeoRegion

	@GetMapping()
	public Page<GeoRegionInfDto> getAllGeoRegionPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return geoRegionService.getAllGeoRegionPaginated(page, size,searchText);
	}// getAllGeoRegionPaginated

	@PostMapping("/add")
	public ResponseMessage addGeoRegion(@RequestBody GeoRegion geoRegion) {
		return geoRegionService.addGeoRegion(geoRegion);
	}// addAllGeoRegion

	@PutMapping("/{regionId}/update")
	public ResponseMessage updateGeoRegionByregionId(@PathVariable int regionId, @RequestBody GeoRegion geoRegion) {
		return geoRegionService.updateGeoRegionByRegionId(regionId, geoRegion);
	}// updateGeoRegionById
	
	@GetMapping("/state-code/{stateCode}")
	public List<GeoRegionDto> getAllGeoRegionbyStateCode(@PathVariable int stateCode) {
		return geoRegionService.getAllGeoRegionbyStateCode(stateCode);
	}

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoRegionService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoRegionService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoRegionService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{regionId}/delete")
	public ResponseMessage deleteGeoRegionByRegionId(@PathVariable int regionId) {
		return geoRegionService.deleteGeoRegionByRegionId(regionId);
	}// deleteGeoRegionById
	
	@GetMapping("/region-id/{regionId}")
	public List<RegionalStateInf> getRegionByStateCode(@PathVariable int regionId){
		return geoRegionService.getRegionByStateCode(regionId);
	}//getRegionByStateCode

//	@GetMapping("/{regionId}")
//	public GeoRegion findGeoRegionByRegionId(@PathVariable int regionId) {
//		return geoRegionService.findGeoRegionByRegionId(regionId);
//	}
}// GeoRegionController
