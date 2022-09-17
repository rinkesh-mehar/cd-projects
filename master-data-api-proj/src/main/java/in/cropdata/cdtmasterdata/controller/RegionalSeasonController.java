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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalSeasonInfDto;
import in.cropdata.cdtmasterdata.model.RegionalSeason;
import in.cropdata.cdtmasterdata.service.RegionalSeasonService;

@RestController
@RequestMapping("/regional/season")
public class RegionalSeasonController {

	@Autowired
	private RegionalSeasonService regionSeasonService;

	@GetMapping("/list")
	public List<RegionalSeasonInfDto> getAllRegionSeason() {
		return regionSeasonService.getAllRegioSeason();
	}// getAllRegionSeason

	@GetMapping()
	public Page<RegionalSeasonInfDto> getAllRegionalSeasonPaginatade(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false,defaultValue = "0") String missing,
																	 @RequestParam("isValid") int isValid,
																	 @RequestParam(required = false, defaultValue = "") String searchText) {
		return regionSeasonService.getAllRegionalSeasonPaginated(page, size,missing, searchText,isValid);

	}// getAllRegionalSeasonPaginatade

	@PostMapping("/add")
	public ResponseMessage addRegionSeason(@RequestBody RegionalSeason regionSeason) {
		return regionSeasonService.addRegionSeason(regionSeason);
	}// addAllRegionSeason

	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionSeasonById(@PathVariable int id, @RequestBody RegionalSeason regionSeason) {
		return regionSeasonService.updateRegionSeasonById(id, regionSeason);
	}// updateRegionSeasonById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionSeasonService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionSeasonService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionSeasonService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionSeasonById(@PathVariable int id) {
		return regionSeasonService.deleteRegionSeasonById(id);
	}// deleteRegionSeasonById

	@GetMapping("/{id}")
	public RegionalSeason findRegionSeasonById(@PathVariable int id) {
		return regionSeasonService.findRegionSeasonById(id);
	}
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionSeasonService.moveToMaster(id);
	}

}
