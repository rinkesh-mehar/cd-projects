package in.cropdata.cdtmasterdata.cropcalender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.cropcalender.service.CropCalendarService;
import in.cropdata.cdtmasterdata.dto.CropCalenderDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalSeasonInfDto;

@RestController
@RequestMapping("/crop/calendar")
public class CropCalenderController {

	@Autowired
	private CropCalendarService cropCalenderService;

	@GetMapping("/list")
	public CropCalenderDto getCropCalendarList(@RequestParam int commodityId, @RequestParam int varietyId,
			@RequestParam int stateCode, @RequestParam int seasonId) {
		return cropCalenderService.getCropCalendarList(commodityId, varietyId, stateCode, seasonId);
	}// getCalenderList

	@GetMapping("/getAvailableStateList")
	public List<GeoStateInfDto> getAvailableStateList() {
		return cropCalenderService.getAvailableStateList();
	}// getAvailableStateList

	@GetMapping("/getAvailableSeasonList")
	public List<GeoStateInfDto> getAvailableSeasonList(@RequestParam Integer stateCode,
			@RequestParam Integer commodityId, @RequestParam Integer varietyId) {
		return cropCalenderService.getAvailableSeasonList(stateCode, commodityId, varietyId);
	}// getAvailableSeasonList

	@GetMapping("/getCommodityByStateList")
	public List<GeoStateInfDto> getCommodityByState(@RequestParam Integer stateCode) {
		return cropCalenderService.getCommodityByState(stateCode);
	}// getCommodityByState

	@GetMapping("/getVarietyByStateAndCommodity")
	public List<GeoStateInfDto> getVarietyByStateAndCommodity(@RequestParam Integer stateCode,
			@RequestParam Integer commodityId) {
		return cropCalenderService.getVarietyByStateAndCommodity(stateCode, commodityId);
	}// getVarietyByStateAndCommodity

}
