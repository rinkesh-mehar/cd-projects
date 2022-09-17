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
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.GeoCountry;
import in.cropdata.cdtmasterdata.service.GeoCountryService;

@RestController
@RequestMapping("/geo/country")
public class GeoCountryController {

	@Autowired
	private GeoCountryService geoCountryService;

	@GetMapping("/list")
	public List<GeoCountry> getAllGeoCountry() {
		return geoCountryService.getAllGeoCountry();
	}// getAllGeoCountry

	/**
	 * @return Only return active country's
	 */
	@GetMapping("/active/list")
	public List<GeoCountry> getActiveGeoCountry()
	{
		return geoCountryService.getActiveGeoCountryList();
	}
	
	@GetMapping("/paginatedList")
	public Page<GeoCountry> getCountryListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return geoCountryService.getCountryListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addGeoCountry(@RequestBody GeoCountry geoCountry) {
		return geoCountryService.addGeoCountry(geoCountry);
	}// addAllGeoCountry

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoCountryById(@PathVariable int id, @RequestBody GeoCountry geoCountry) {
		return geoCountryService.updateGeoCountryById(id, geoCountry);
	}// updateGeoCountryById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoCountryService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoCountryService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoCountryService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoCountryById(@PathVariable int id) {
		return geoCountryService.deleteGeoCountryById(id);
	}// deleteGeoCountryById

	@GetMapping("/{id}")
	public GeoCountry findGeoCountryById(@PathVariable int id) {
		return geoCountryService.findGeoCountryById(id);
	}// findGeoCountryById

}// GeoCountryController
