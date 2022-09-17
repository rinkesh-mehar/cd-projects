package in.cropdata.cdtmasterdata.website.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.model.CropdataCalendar;
import in.cropdata.cdtmasterdata.website.model.vo.CropdataCalendarVO;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;
import in.cropdata.cdtmasterdata.website.repository.CropdataCalendarRepository;

@Service
public class CropdataCalendarService {


	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private CropdataCalendarRepository cropdataCalendarRepository;

	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	public ResponseMessage addHoliday(CropdataCalendar cropdataCalendar) {
		try {
			
			LOGGER.info("cropdataCalendar..." + cropdataCalendar);
			
			LocalDate holidayDate = cropdataCalendar.getHolidayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			LOGGER.info("day..." + holidayDate.getDayOfMonth());
			LOGGER.info("month..." + holidayDate.getMonthValue());
			LOGGER.info("year..." + holidayDate.getYear());
			
			cropdataCalendar.setDay(holidayDate.getDayOfMonth());
			cropdataCalendar.setMonth(holidayDate.getMonthValue());
			cropdataCalendar.setYear(holidayDate.getYear());

			cropdataCalendarRepository.save(cropdataCalendar);
		
			return responseMessageUtil.sendResponse(true, "Holiday" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Page<CropdataCalendarVO> getHolidayListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all holiday from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return cropdataCalendarRepository.getHolidayListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Holiday Data Found For Searched Text -> " + searchText);
		}
	}
	
	public Page<CropdataCalendarVO> getHolidayListByRegionId(Integer page, Integer size, Integer regionId, String searchText){
		try {
			LOGGER.info("getting all holiday from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return cropdataCalendarRepository.getHolidayListByRegionId(sortedByIdDesc, regionId, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Holiday Data Found For Region Id -> " + regionId + " SearchText -> " + searchText);
		}
	}
	
	public List<GeoRegionDto> getAllGeoRegion() {
		try {
			List<GeoRegionDto> list = cropdataCalendarRepository.getGeoRegionList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String[] getHolidayDateList(Integer regionId) {

		try {
			LOGGER.info("getting all holiday date list info...");

			return cropdataCalendarRepository.getHolidayDateList(regionId);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Holiday Date List Data Found!");
		}
	}
	
	public Integer checkHolidayAlreadyExist(Integer regionId, String holidayDate) {

		try {

			LOGGER.info("Checking holiday exist...");
			
			String [] holidayDateSplit = holidayDate.split("-");
			
			Integer day = Integer.parseInt(holidayDateSplit[0]);
			Integer month = Integer.parseInt(holidayDateSplit[1]);
			Integer year = Integer.parseInt(holidayDateSplit[2]);
			
			LOGGER.info("day : " + day + " month : " + month + " year : " + year);
			
			return cropdataCalendarRepository.checkHolidayAlreadyExist(regionId,day, month, year);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Records Found.");
		}

	}
	
	 public ResponseMessage activateHoliday(int id) {
			try {
				Optional<CropdataCalendar> optionalCropdataCallendar = cropdataCalendarRepository.findById(id);
				if (optionalCropdataCallendar.isPresent()) {

					cropdataCalendarRepository.activateHoliday(id);

					return responseMessageUtil.sendResponse(true, "Holiday" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
							"");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Holiday" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
				}
			} catch (Exception e) {
				return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
			}

		}
	 
	 public ResponseMessage deactivateHoliday(int id) {
			try {
				Optional<CropdataCalendar> optionalCropdataCallendar = cropdataCalendarRepository.findById(id);
				if (optionalCropdataCallendar.isPresent()) {

					cropdataCalendarRepository.deactivateHoliday(id);

					return responseMessageUtil.sendResponse(true, "Holiday" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
							"");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Holiday" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
				}
			} catch (Exception e) {
				return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
			}

		}
	 
	 public ResponseMessage updateHoliday(Integer id, CropdataCalendar cropdataCalendar) {
			try {
				
				Optional<CropdataCalendar> cropdataCalendarDetail = cropdataCalendarRepository.findById(id);

				if (cropdataCalendarDetail.isPresent()) {
					
						LOGGER.info("\"Updating Holiday From Service for ID -> {}\", id");
						LocalDate holidayDate = cropdataCalendar.getHolidayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						LOGGER.info("day..." + holidayDate.getDayOfMonth());
						LOGGER.info("month..." + holidayDate.getMonthValue());
						LOGGER.info("year..." + holidayDate.getYear());
						
						
						cropdataCalendar.setId(id);
						cropdataCalendar.setDay(holidayDate.getDayOfMonth());
						cropdataCalendar.setMonth(holidayDate.getMonthValue());
						cropdataCalendar.setYear(holidayDate.getYear());
						cropdataCalendarRepository.save(cropdataCalendar);
					
					return responseMessageUtil.sendResponse(true, "Holiday" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
							"");
				} else {
					return responseMessageUtil.sendResponse(false,
							"Holiday Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id, "");
				}
			}catch (Exception ex) {
				return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
			}
		}
	
}
