package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCondusiveWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.model.AgriCondusiveWeather;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.AgriCondusiveWeatherMissing;
import in.cropdata.cdtmasterdata.model.AgriStress;
import in.cropdata.cdtmasterdata.model.AgriStressType;
import in.cropdata.cdtmasterdata.model.WeatherParams;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.repository.AgriCondusiveWeatherMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriCondusiveWeatherRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriDistrictCommodityStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressTypeRepository;
import in.cropdata.cdtmasterdata.repository.WeatherParamsRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCondusiveWeatherService {

	@Autowired
	private AgriCondusiveWeatherRepository agriCondusiveWeatherRepository;
	
	@Autowired
	private AgriCondusiveWeatherMissingRepository agriCondusiveWeatherMissingRepository;

	@Autowired
	private AgriCommodityRepositoy commodityRepositoy;

	@Autowired
	private AgriStressTypeRepository agriStressTypeRepository;

	@Autowired
	private AgriDistrictCommodityStressRepository agriCommodityStressRepository;
	
	@Autowired
	private AgriStressRepository agriStressRepository;

	@Autowired
	private WeatherParamsRepository weatherParameterRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriCondusiveWeather> getAllAgriAgriCondusiveWeather() {

		try {
			List<AgriCondusiveWeather> CondusiveWeatherList = agriCondusiveWeatherRepository.findAll();

			for (AgriCondusiveWeather condusiveWeather : CondusiveWeatherList) {

				condusiveWeather = getData(condusiveWeather);

			} // for
			return CondusiveWeatherList;
		} catch (Exception e) {
			throw e;
		}
	}

	public Page<AgriCondusiveWeatherInfDto> getAllAgriCondusiveWeatherPaginated(int page, int size,String missing ,String searchText, int isValid) {

		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);
			Page<AgriCondusiveWeatherInfDto> condusiveWeatherList;

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			
			if("0".equals(missing)) {
				if (isValid == 0) {
					condusiveWeatherList = agriCondusiveWeatherRepository.getAgriCondusiveWeatherInvalidated(sortedByIdDesc, searchText);
				} else {
					condusiveWeatherList = agriCondusiveWeatherRepository.getAgriCondusiveWeather(sortedByIdDesc, searchText);
				}
			}else {
				if (isValid == 0) {
					condusiveWeatherList = agriCondusiveWeatherRepository.getAgriCondusiveWeatherMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					condusiveWeatherList = agriCondusiveWeatherRepository.getAgriCondusiveWeatherMissingList(sortedByIdDesc, searchText);
				}
			}
			return condusiveWeatherList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCondusiveWeatherPaginated

	private AgriCondusiveWeather getData(AgriCondusiveWeather condusiveWeather) {
		try {
			Optional<AgriCommodity> foundCommodity = commodityRepositoy.findById(condusiveWeather.getCommodityId());

			if (foundCommodity.isPresent()) {
				AgriCommodity commodity = foundCommodity.get();
				condusiveWeather.setCommodity(commodity.getName());

				if (condusiveWeather.getStressTypeId() != null) {
					Optional<AgriStressType> foundStressType = agriStressTypeRepository
							.findById(condusiveWeather.getStressTypeId());

					if (foundStressType.isPresent()) {
						AgriStressType stressType = foundStressType.get();
						condusiveWeather.setStressType(stressType.getName());
					}

//					Optional<AgriCommodityStress> foundStress = agriCommodityStressRepository.findById(condusiveWeather.getStressId());
					Optional<AgriStress> foundStress = agriStressRepository.findById(condusiveWeather.getStressId());

					if (foundStress.isPresent()) {
						AgriStress stress = foundStress.get();
						condusiveWeather.setStress(stress.getName());
					}

					if (condusiveWeather.getPrimaryWeatherParameterId() != null) {
						Optional<WeatherParams> foundWeatherParams = weatherParameterRepository
								.findById(condusiveWeather.getPrimaryWeatherParameterId());

						if (foundWeatherParams.isPresent()) {
							WeatherParams weatherParams = foundWeatherParams.get();
							condusiveWeather.setPrimaryWeatherParameter(weatherParams.getName());
						}

						if (condusiveWeather.getSecondaryWeatherParameterId() != null) {
							Optional<WeatherParams> foundWeatherParam = weatherParameterRepository
									.findById(condusiveWeather.getSecondaryWeatherParameterId());

							if (foundWeatherParam.isPresent()) {
								WeatherParams weatherParams = foundWeatherParam.get();
								condusiveWeather.setSecondaryWeatherParameter(weatherParams.getName());
							}
						}

					}

				}

			}

			return condusiveWeather;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addAgriCondusiveWeather(AgriCondusiveWeather agriCondusiveWeather) {

		try {

			agriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Condusive-Weather" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriCondusiveWeather

	public ResponseMessage updateAgriCondusiveWeatherById(int id, AgriCondusiveWeather agriCondusiveWeather) {

		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);

			if (foundCondusiveWeather.isPresent()) {

				AgriCondusiveWeather update = foundCondusiveWeather.get();

				if (agriCondusiveWeather.getCommodityId() != null && agriCondusiveWeather.getCommodityId() > 0) {
					update.setCommodityId(agriCondusiveWeather.getCommodityId());
				}

				if (agriCondusiveWeather.getStressTypeId() != null && agriCondusiveWeather.getStressTypeId() > 0) {
					update.setStressTypeId(agriCondusiveWeather.getStressTypeId());
				}

				if (agriCondusiveWeather.getStressId() != null && agriCondusiveWeather.getStressId() > 0) {
					update.setStressId(agriCondusiveWeather.getStressId());
				}
				
				if(agriCondusiveWeather.getPrimaryWeatherParameterId() != null && agriCondusiveWeather.getPrimaryWeatherParameterId() > 0) {
					update.setPrimaryWeatherParameterId(agriCondusiveWeather.getPrimaryWeatherParameterId());
				}

				if (agriCondusiveWeather.getPrimarySpecificationAverage() != null
						&& agriCondusiveWeather.getPrimarySpecificationAverage() >= 0) {
					update.setPrimarySpecificationAverage(agriCondusiveWeather.getPrimarySpecificationAverage());
				}

				if (agriCondusiveWeather.getPrimarySpecificationLower() != null
						&& agriCondusiveWeather.getPrimarySpecificationLower() >= 0) {
					update.setPrimarySpecificationLower(agriCondusiveWeather.getPrimarySpecificationLower());
				}

				if (agriCondusiveWeather.getPrimarySpecificationUpper() != null
						&& agriCondusiveWeather.getPrimarySpecificationUpper() >= 0) {
					update.setPrimarySpecificationUpper(agriCondusiveWeather.getPrimarySpecificationUpper());
				}

//				if (agriCondusiveWeather.getPrimaryStressDuration() > 0) {
//					update.setPrimaryStressDuration(agriCondusiveWeather.getPrimaryStressDuration());
//				}

				if (agriCondusiveWeather.getPrimaryStressDurationPast() != null
						&& agriCondusiveWeather.getPrimaryStressDurationPast() >= 0) {
					update.setPrimaryStressDurationPast(agriCondusiveWeather.getPrimaryStressDurationPast());
				}

				if (agriCondusiveWeather.getPrimaryStressDurationFuture() != null
						&& agriCondusiveWeather.getPrimaryStressDurationFuture() >= 0) {
					update.setPrimaryStressDurationFuture(agriCondusiveWeather.getPrimaryStressDurationFuture());
				}

				if(agriCondusiveWeather.getSecondaryWeatherParameterId() != null && agriCondusiveWeather.getSecondaryWeatherParameterId() > 0) {
					update.setSecondaryWeatherParameterId(agriCondusiveWeather.getSecondaryWeatherParameterId());
				}
				
				if (agriCondusiveWeather.getSecondarySpecificationAverage() != null
						&& agriCondusiveWeather.getSecondarySpecificationAverage() >= 0) {
					update.setSecondarySpecificationAverage(agriCondusiveWeather.getSecondarySpecificationAverage());
				}

				if (agriCondusiveWeather.getSecondarySpecificationLower() != null
						&& agriCondusiveWeather.getSecondarySpecificationLower() >= 0) {
					update.setSecondarySpecificationLower(agriCondusiveWeather.getSecondarySpecificationLower());
				}

				if (agriCondusiveWeather.getSecondarySpecificationUpper() != null
						&& agriCondusiveWeather.getSecondarySpecificationUpper() >= 0) {
					update.setSecondarySpecificationUpper(agriCondusiveWeather.getSecondarySpecificationUpper());
				}

//				if (agriCondusiveWeather.getSecondaryStressDuration() > 0) {
//					update.setSecondaryStressDuration(agriCondusiveWeather.getSecondaryStressDuration());
//				}

				if (agriCondusiveWeather.getSecondaryStressDurationPast() != null
						&& agriCondusiveWeather.getSecondaryStressDurationPast() >= 0) {
					update.setSecondaryStressDurationPast(agriCondusiveWeather.getSecondaryStressDurationPast());
				}
				if (agriCondusiveWeather.getSecondaryStressDurationFuture() != null
						&& agriCondusiveWeather.getSecondaryStressDurationFuture() >= 0) {
					update.setSecondaryStressDurationFuture(agriCondusiveWeather.getSecondaryStressDurationFuture());
				}

				if (agriCondusiveWeather.getStatus() != null) {
					update.setStatus(agriCondusiveWeather.getStatus());
				}

				agriCondusiveWeather = agriCondusiveWeatherRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateAgriCondusiveWeatherById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);

			if (foundCondusiveWeather.isPresent()) {

				AgriCondusiveWeather agriCondusiveWeather = foundCondusiveWeather.get();
				agriCondusiveWeather.setStatus(APIConstants.STATUS_APPROVED);

				agriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);

			if (foundCondusiveWeather.isPresent()) {

				AgriCondusiveWeather agriCondusiveWeather = foundCondusiveWeather.get();
				agriCondusiveWeather.setStatus(APIConstants.STATUS_ACTIVE);

				agriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById
	
	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);

			if (foundCondusiveWeather.isPresent()) {

				AgriCondusiveWeather agriCondusiveWeather = foundCondusiveWeather.get();
				agriCondusiveWeather.setStatus(APIConstants.STATUS_REJECTED);

				agriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriCondusiveWeatherById(int id) {
		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);
			if (foundCondusiveWeather.isPresent()) {

				AgriCondusiveWeather agriCondusiveWeather = foundCondusiveWeather.get();
				agriCondusiveWeather.setStatus(APIConstants.STATUS_DELETED);

				agriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);

				approvalUtil.delete(DBConstants.TBL_AGRI_CONDUCIVE_WEATHER, agriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriCondusiveWeatherById

	public AgriCondusiveWeather findAgriCondusiveWeatherById(int id) {
		try {
			Optional<AgriCondusiveWeather> foundCondusiveWeather = agriCondusiveWeatherRepository.findById(id);
			if (foundCondusiveWeather.isPresent()) {
				return foundCondusiveWeather.get();
			} else {
				throw new DoesNotExistException("Agri-Condusive-Weather" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriCondusiveWeatherById

	public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(int commodityId, int stressTypeId) {
		try {
//			List<AgriCommodityStress> stressList = agriCommodityStressRepository.findByCommodityIdAndStressTypeId(commodityId,
//					stressTypeId);
			
			List<AgriDistrictCommodityStressInfDto> stressList = agriCommodityStressRepository.getStressByCommodityIdAndStressTypeId(commodityId,
					stressTypeId);

			return stressList;

		} catch (Exception e) {
			throw e;
		}
	}// getByCommodityIdAndStressTypeId
	
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriCondusiveWeatherMissing> foundAgriCondusiveWeatherMissing = agriCondusiveWeatherMissingRepository.findById(id);

			if (foundAgriCondusiveWeatherMissing.isPresent()) {
				AgriCondusiveWeather agriCondusiveWeather = new AgriCondusiveWeather();
				AgriCondusiveWeatherMissing agriCondusiveWeatherMissing = foundAgriCondusiveWeatherMissing.get();
				
				agriCondusiveWeather.setCommodityId(agriCondusiveWeatherMissing.getCommodityId());
				agriCondusiveWeather.setStressTypeId(agriCondusiveWeatherMissing.getStressTypeId());
				agriCondusiveWeather.setStressId(agriCondusiveWeatherMissing.getStressId());
				agriCondusiveWeather.setPrimaryWeatherParameterId(agriCondusiveWeatherMissing.getPrimaryWeatherParameterId());
				agriCondusiveWeather.setSecondaryWeatherParameterId(agriCondusiveWeatherMissing.getSecondaryWeatherParameterId());
				agriCondusiveWeather.setPrimarySpecificationAverage(agriCondusiveWeatherMissing.getPrimarySpecificationAverage());
				agriCondusiveWeather.setPrimarySpecificationLower(agriCondusiveWeatherMissing.getPrimarySpecificationLower());
				agriCondusiveWeather.setPrimarySpecificationUpper(agriCondusiveWeatherMissing.getPrimarySpecificationUpper());
				agriCondusiveWeather.setPrimaryStressDurationPast(agriCondusiveWeatherMissing.getPrimaryStressDurationPast());
				agriCondusiveWeather.setPrimaryStressDurationFuture(agriCondusiveWeatherMissing.getPrimaryStressDurationFuture());
				agriCondusiveWeather.setSecondarySpecificationAverage(agriCondusiveWeatherMissing.getSecondarySpecificationAverage());
				agriCondusiveWeather.setSecondarySpecificationLower(agriCondusiveWeatherMissing.getSecondarySpecificationLower());
				agriCondusiveWeather.setSecondarySpecificationUpper(agriCondusiveWeatherMissing.getSecondarySpecificationUpper());
				agriCondusiveWeather.setSecondaryStressDurationPast(agriCondusiveWeatherMissing.getSecondaryStressDurationPast());
				agriCondusiveWeather.setSecondaryStressDurationFuture(agriCondusiveWeatherMissing.getSecondaryStressDurationFuture());
				agriCondusiveWeather.setStatus(agriCondusiveWeatherMissing.getStatus());
				
				AgriCondusiveWeather savedAgriCondusiveWeather = agriCondusiveWeatherRepository.save(agriCondusiveWeather);
				
				agriCondusiveWeatherMissingRepository.deleteById(id);

				System.out.println("RCom Id : " + savedAgriCondusiveWeather.getId());
				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_SEASON, savedAgriCondusiveWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Condusive-Weather-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Condusive-Weather-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public List<AgriDistrictCommodityStressInfDto> getStressByCommodityIdAndStressTypeId(int commodityId, int stressTypeId) {
		try {
			List<AgriDistrictCommodityStressInfDto> stressList = agriCommodityStressRepository.getStressByCommodityIdAndStressTypeId(commodityId,
					stressTypeId);

			return stressList;

		} catch (Exception e) {
			throw e;
		}
	}

}
