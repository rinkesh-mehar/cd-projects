package in.cropdata.cdtmasterdata.service;

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
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.model.WeatherParams;
import in.cropdata.cdtmasterdata.repository.GeneralUomRepository;
import in.cropdata.cdtmasterdata.repository.WeatherParamsRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralWeatherParameterDto;

@Service
public class WeatherParamsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	
	@Autowired
	private WeatherParamsRepository weatherParamsRepository;
	
	@Autowired
	private GeneralUomRepository generalUomRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<WeatherParams> getAllWeatherParams() {

		try {
			List<WeatherParams> WeatherParamsList = weatherParamsRepository.findAll(Sort.by("name"));

			for (WeatherParams weatherParams : WeatherParamsList) {

				weatherParams = getData(weatherParams);

			} // for
			return WeatherParamsList;
		} catch (Exception e) {
			throw e;
		}
	}

	public Page<GeneralWeatherParameterDto> getWeatherParamListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return weatherParamsRepository.getWeatherParamListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Weather-Param Data Found For Searched Text -> " + searchText);
		}
	}
	
	private WeatherParams getData(WeatherParams weatherParams) {
		try {
			
			Optional<GeneralUom> founduom = generalUomRepository
					.findById(weatherParams.getUnitId());

			if (founduom.isPresent()) {
				GeneralUom uom = founduom.get();
				weatherParams.setUnitId(uom.getId());
				weatherParams.setUnit(uom.getName());

			}
			
			return weatherParams;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addWeatherParams(WeatherParams weatherParams)
	{
		try
		{
			final String weatherParameterName = weatherParams.getName().strip();
			final String weatherLabel = weatherParams.getLabel().strip();

			if (weatherParamsRepository.findByNameAndLabel(weatherParameterName, weatherLabel).isEmpty())
			{
				weatherParams.setName(weatherParameterName);
				weatherParams.setLabel(weatherLabel);

				weatherParams = weatherParamsRepository.save(weatherParams);

				approvalUtil.addRecord(DBConstants.TBL_WEATHER_PARAMS, weatherParams.getId());

				return responseMessageUtil.sendResponse(true,
						"Weather-Param" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Weather-Param".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(weatherLabel));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addWeatherParams

	public ResponseMessage updateWeatherParamsById(int id, WeatherParams weatherParams) {

		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);

			if (foundWeatherParams.isPresent()) {

				WeatherParams update = foundWeatherParams.get();

				if (weatherParams.getUnitId() > 0) {
					update.setUnitId(weatherParams.getUnitId());
				}
				
				if (weatherParams.getName() != null) {
					update.setName(weatherParams.getName());
				}
				
				if (weatherParams.getLabel() != null) {
					update.setLabel(weatherParams.getLabel());
				}
				
				if (weatherParams.getStatus() != null) {
					update.setStatus(weatherParams.getStatus());
				}
				
				weatherParams = weatherParamsRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_WEATHER_PARAMS, weatherParams.getId());

				return responseMessageUtil.sendResponse(true,
						"Weather-Param" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Weather-Param-" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateWeatherParamsById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);

			if (foundWeatherParams.isPresent()) {

				WeatherParams weatherParams = foundWeatherParams.get();
				weatherParams.setStatus(APIConstants.STATUS_APPROVED);

				weatherParams = weatherParamsRepository.save(weatherParams);

				approvalUtil.primaryApprove(DBConstants.TBL_WEATHER_PARAMS, weatherParams.getId());

				return responseMessageUtil.sendResponse(true,
						"Weather-Param" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Weather-Param" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);

			if (foundWeatherParams.isPresent()) {

				WeatherParams watherParams = foundWeatherParams.get();
				watherParams.setStatus(APIConstants.STATUS_ACTIVE);

				watherParams = weatherParamsRepository.save(watherParams);

				approvalUtil.finalApprove(DBConstants.TBL_WEATHER_PARAMS, watherParams.getId());

				return responseMessageUtil.sendResponse(true,
						"AWeather-Param" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Weather-Param" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteWeatherParamsById(int id) {
		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);
			if (foundWeatherParams.isPresent()) {

				WeatherParams weatherParams = foundWeatherParams.get();
				weatherParams.setStatus(APIConstants.STATUS_DELETED);

				weatherParams = weatherParamsRepository.save(weatherParams);

				approvalUtil.delete(DBConstants.TBL_WEATHER_PARAMS, weatherParams.getId());

				return responseMessageUtil.sendResponse(true,
						"Weather-Param" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Weather-Param" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteWeatherParamsById

	public WeatherParams findWeatherParamsById(int id) {
		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);
			if (foundWeatherParams.isPresent()) {
				return foundWeatherParams.get();
			} else {
				throw new DoesNotExistException("Weather-Param" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findWeatherParamsById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository.findById(id);

			if (foundWeatherParams.isPresent()) {

				WeatherParams weatherParams = foundWeatherParams.get();
				weatherParams.setStatus(APIConstants.STATUS_REJECTED);
				weatherParams = weatherParamsRepository.save(weatherParams);

				approvalUtil.finalApprove(DBConstants.TBL_WEATHER_PARAMS, weatherParams.getId());

				return responseMessageUtil.sendResponse(true, "Weather-Param " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Weather-Param " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}