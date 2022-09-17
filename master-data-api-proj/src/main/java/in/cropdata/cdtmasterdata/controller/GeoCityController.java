package in.cropdata.cdtmasterdata.controller;

import java.util.List;

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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoCityInfDto;
import in.cropdata.cdtmasterdata.model.GeoCity;
import in.cropdata.cdtmasterdata.service.GeoCityService;

@RestController
@RequestMapping("/geo/city")
public class GeoCityController {

	private GeoCityService geoCityService;

	@GetMapping("/list")
	public List<GeoCity> getAllGeoCity() {
		return geoCityService.getAllGeoCity();
	}// getAllGeoCity

	@GetMapping()
	public Page<GeoCityInfDto> getAllGeoCityPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return geoCityService.getAllGeoCityPaginated(page, size,searchText);
	}// getAllGeoCityPaginated

	@PostMapping("/add")
	public ResponseMessage addGeoCity(@RequestBody GeoCity GeoCity) {
		return geoCityService.addGeoCity(GeoCity);
	}// addGeoCity

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoCityById(@PathVariable int id, @RequestBody GeoCity GeoCity) {
		return geoCityService.updateGeoCityById(id, GeoCity);
	}// updateGeoCityById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoCityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoCityService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoCityService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoCityById(@PathVariable int id) {
		return geoCityService.deleteGeoCityById(id);
	}// deleteGeoCityById

	@GetMapping("/{id}")
	public GeoCity findGeoCityById(@PathVariable int id) {
		return geoCityService.findGeoCityById(id);
	}
}
