package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import in.cropdata.cdtmasterdata.website.model.CropdataCalendar;
import in.cropdata.cdtmasterdata.website.model.vo.CropdataCalendarVO;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;
import in.cropdata.cdtmasterdata.website.service.CropdataCalendarService;


@RestController
@RequestMapping("/site/holiday-calendar")
public class CropdataCalendarController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private CropdataCalendarService cropdataCalendarService;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addHoliday(@RequestBody CropdataCalendar cropdataCalendar) {

		LOGGER.info("Add Cropdat From Controller...");
		return new ResponseEntity<>(cropdataCalendarService.addHoliday(cropdataCalendar), HttpStatus.CREATED);
	}
	
	@GetMapping("/region-list")
	public List<GeoRegionDto> getAllGeoRegion() {
		return cropdataCalendarService.getAllGeoRegion();
	}
	
	@GetMapping("/paginatedList")
	public Page<CropdataCalendarVO> getHolidayListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		LOGGER.info("Inside Holiday Pagenation List...");
		LOGGER.info("searchText..." + searchText);

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return cropdataCalendarService.getHolidayListByPagenation(page, size, searchText);
	}
	
	@GetMapping("/holiday-list-by-regionId")
	public Page<CropdataCalendarVO> getHolidayListByRegionId(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = true) Integer regionId,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		
		LOGGER.info("Inside Holiday Pagenation List By Region Id...");
		LOGGER.info("regionId..." + regionId);
		LOGGER.info("searchText..." + searchText);

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return cropdataCalendarService.getHolidayListByRegionId(page, size, regionId, searchText);
	}
	
	@GetMapping("/holiday-date-list")
	public String[] getHolidayDateList(@RequestParam(required = true) Integer regionId) {

		return cropdataCalendarService.getHolidayDateList(regionId);

	}
	
	@GetMapping("check-holiday-exist")
	public Integer checkHolidayAlreadyExist(@RequestParam(required = true) String regionId, @RequestParam(required = true) String holidayDate) {

		LOGGER.info("regionId..." + regionId);
		LOGGER.info("holidayDate..." + holidayDate);
		return cropdataCalendarService.checkHolidayAlreadyExist(Integer.parseInt(regionId),holidayDate);

	}
	
	@PutMapping("/activate-holiday/{id}")
	public ResponseMessage activateHoliday(@PathVariable int id) {
		return cropdataCalendarService.activateHoliday(id);
	}
	
	@PutMapping("/deactivate-holiday/{id}")
	public ResponseMessage deactivateHoliday(@PathVariable int id) {
		return cropdataCalendarService.deactivateHoliday(id);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateHoliday(@PathVariable(required = true) Integer id,
			@RequestBody CropdataCalendar cropdataCalendar) {

		if (id == null) {
			throw new InvalidDataException("Holiday Id can not be null!");
		}

		if (cropdataCalendar == null) {
			throw new InvalidDataException("Holiday data can not be null!");
		}

		LOGGER.info("Updating Holiday From Controller for ID -> {}", id);
		return new ResponseEntity<>(cropdataCalendarService.updateHoliday(id, cropdataCalendar), HttpStatus.OK);
	}

}
