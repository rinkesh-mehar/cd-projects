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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoVillageInfDto;
import in.cropdata.cdtmasterdata.model.GeoVillage;
import in.cropdata.cdtmasterdata.service.GeoVillageService;

@RestController
@RequestMapping("/geo/village")
public class GeoVillageController {

	@Autowired
	private GeoVillageService geoVillageService;

	@GetMapping("/list")
	public List<GeoVillageInfDto> getAllGeoVillage() {
		return geoVillageService.getAllGeoVillage();
	}// getAllGeoVillage

	@GetMapping()
	public Page<GeoVillageInfDto> getAllGeoVillagePaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return geoVillageService.getAllGeoVillagePaginated(page, size, searchText);
	}// getAllGeoVillagePaginated

	@PostMapping("/add")
	public ResponseMessage addGeoVillage(@RequestBody GeoVillage geoVillage) {
		return geoVillageService.addGeoVillage(geoVillage);
	}// addGeoVillage

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeoVillageById(@PathVariable int id, @RequestBody GeoVillage geoVillage) {
		return geoVillageService.updateGeoVillageById(id, geoVillage);
	}// updateGeoVillageById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return geoVillageService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return geoVillageService.finalApproveById(id);
	}// finalApproveById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return geoVillageService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeoVillageById(@PathVariable int id) {
		return geoVillageService.deleteGeoVillageById(id);
	}// deleteGeoVillageById

	@GetMapping("/{id}")
	public GeoVillage findGeoVillageById(@PathVariable int id) {
		return geoVillageService.findGeoVillageById(id);
	}

	/**
	 * This API is used to get all Village alias data with pagination.
	 * 
	 * @param page the page number for fetching the alias data
	 * @param size the no of records to be present in a single page
	 * 
	 * @return the response data in pageable list
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/alias")
	public List<Object> getVillageAliasWithPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return geoVillageService.getVillageAliasWithPage(page, size);
	}

	/**
	 * This API is used to tag Village with respective alias.
	 * 
	 * @param villageAlias the alias data to be tagged against Village
	 * 
	 * @return the response data in {@link ResponseMessage}
	 * 
	 * @author PranaySK
	 */
	@PostMapping("/alias/tag")
	public ResponseMessage tagVillageAlias(@RequestBody Map<String, Object> villageAlias) {
		return geoVillageService.tagVillageAlias(villageAlias);
	}

	/**
	 * This API is used to fetch GEO village list based on tehsil code.
	 * 
	 * @param tehsilCode the tehsil code based on which the GEO village list to be
	 *                   fetched
	 * 
	 * @return the response data in list of {@link GeoVillageInfDto}
	 * 
	 * @author PranaySK
	 */
	@GetMapping("/list/{tehsilCode}")
	public List<GeoVillageInfDto> getGeoVillagesByTehsilCode(
			@PathVariable(name = "tehsilCode", required = true) Integer tehsilCode) {
		return geoVillageService.getGeoVillagesByTehsilCode(tehsilCode);
	}

}
