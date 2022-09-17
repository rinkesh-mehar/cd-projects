package in.cropdata.cdtmasterdata.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Transient;

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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFavourableWeatherInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodity;
import in.cropdata.cdtmasterdata.model.AgriFavourableWeather;
import in.cropdata.cdtmasterdata.model.AgriFavourableWeatherMissing;
import in.cropdata.cdtmasterdata.model.AgriPhenophase;
import in.cropdata.cdtmasterdata.model.WeatherParams;
import in.cropdata.cdtmasterdata.repository.AgriCommodityRepositoy;
import in.cropdata.cdtmasterdata.repository.AgriFavourableWeatherMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriFavourableWeatherRepository;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseRepository;
import in.cropdata.cdtmasterdata.repository.WeatherParamsRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriFavourableWeatherService {

	@Autowired
	private AgriFavourableWeatherRepository agriFavourableWeatherRepository;
	
	@Autowired
	private AgriFavourableWeatherMissingRepository agriFavourableWeatherMissingRepository;

	@Autowired
	private AgriCommodityRepositoy commodityRepositoy;

	@Autowired
	private AgriPhenophaseRepository agriPhenophaseRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private WeatherParamsRepository weatherParamsRepository;

	public List<AgriFavourableWeather> getAllAgriAgriFavourableWeather() {

		try {
			List<AgriFavourableWeather> FavourableWeatherList = agriFavourableWeatherRepository.findAll();

			for (AgriFavourableWeather FavourableWeather : FavourableWeatherList) {

				FavourableWeather = getData(FavourableWeather);

			} // for
			return FavourableWeatherList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public Page<AgriFavourableWeatherInfDto> getAllFavourableWeatherPaginated(int page, int size,String missing, String  searchText, int isValid) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
		
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriFavourableWeatherInfDto> favourableWeatherList;
			
			if("0".equals(missing)) {
				if (isValid == 0) {
					favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeatherInvalidated(sortedByIdDesc, searchText);
				} else {
					favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeather(sortedByIdDesc, searchText);
				}
			}else {
				if (isValid == 0) {
					favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeatherMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeatherMissingList(sortedByIdDesc, searchText);
				}
			}
			
			
			

//			if (isValid == 0) {
//				favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeatherInvalidated(sortedByIdDesc, searchText);
//			} else {
//				favourableWeatherList = agriFavourableWeatherRepository.getFavourableWeather(sortedByIdDesc, searchText);
//			}

			return favourableWeatherList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllRegionalLanguagePaginated

	private AgriFavourableWeather getData(AgriFavourableWeather favourableWeather) {
		try {
			Optional<AgriCommodity> foundCommodity = commodityRepositoy.findById(favourableWeather.getCommodityId());

			if (foundCommodity.isPresent()) {
				AgriCommodity commodity = foundCommodity.get();
				favourableWeather.setCommodity(commodity.getName());
			}

			Optional<AgriPhenophase> foundPhenophase = agriPhenophaseRepository
					.findById(favourableWeather.getPhenophaseId());

			if (foundPhenophase.isPresent()) {
				AgriPhenophase phenophase = foundPhenophase.get();
				favourableWeather.setPhenophase(phenophase.getName());
			}

			Optional<WeatherParams> foundWeatherParams = weatherParamsRepository
					.findById(favourableWeather.getWeatherParameterId());

			if (foundWeatherParams.isPresent()) {
				WeatherParams weatherParams = foundWeatherParams.get();
				favourableWeather.setWeatherParameter(weatherParams.getName());
			}
			return favourableWeather;

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseMessage addAgriFavourableWeather(AgriFavourableWeather agriFavourableWeather) {

		try {

			agriFavourableWeather = agriFavourableWeatherRepository.save(agriFavourableWeather);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

			return responseMessageUtil.sendResponse(true, "Agri-FavourableWeather" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriFavourableWeather

	public ResponseMessage updateAgriFavourableWeatherById(int id, AgriFavourableWeather agriFavourableWeather) {

		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);

			if (foundFavourableWeather.isPresent()) {

				AgriFavourableWeather update = foundFavourableWeather.get();

				if (agriFavourableWeather.getCommodityId() > 0) {
					update.setCommodityId(agriFavourableWeather.getCommodityId());
				}

				if (agriFavourableWeather.getPhenophaseId() > 0) {
					update.setPhenophaseId(agriFavourableWeather.getPhenophaseId());
				}

				if (agriFavourableWeather.getWeatherParameterId() > 0) {
					update.setWeatherParameterId(agriFavourableWeather.getWeatherParameterId());
				}

				if (agriFavourableWeather.getSpecificationAverage() > 0) {
					update.setSpecificationAverage(agriFavourableWeather.getSpecificationAverage());
				}
				
				if (agriFavourableWeather.getSpecificationLower() > 0) {
					update.setSpecificationLower(agriFavourableWeather.getSpecificationLower());
				}
				
				if (agriFavourableWeather.getSpecificationUpper() > 0) {
					update.setSpecificationUpper(agriFavourableWeather.getSpecificationUpper());
				}

				if (agriFavourableWeather.getStatus() != null) {
					update.setStatus(agriFavourableWeather.getStatus());
				}

				agriFavourableWeather = agriFavourableWeatherRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FavourableWeather" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FavourableWeather" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateAgriFavourableWeatherById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);

			if (foundFavourableWeather.isPresent()) {

				AgriFavourableWeather agriFavourableWeather = foundFavourableWeather.get();
				agriFavourableWeather.setStatus(APIConstants.STATUS_APPROVED);

				agriFavourableWeather = agriFavourableWeatherRepository.save(agriFavourableWeather);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FavourableWeather" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FavourableWeather" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);

			if (foundFavourableWeather.isPresent()) {

				AgriFavourableWeather agriFavourableWeather = foundFavourableWeather.get();
				agriFavourableWeather.setStatus(APIConstants.STATUS_ACTIVE);

				agriFavourableWeather = agriFavourableWeatherRepository.save(agriFavourableWeather);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FavourableWeather" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FavourableWeather" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById
	
	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);

			if (foundFavourableWeather.isPresent()) {

				AgriFavourableWeather agriFavourableWeather = foundFavourableWeather.get();
				agriFavourableWeather.setStatus(APIConstants.STATUS_REJECTED);

				agriFavourableWeather = agriFavourableWeatherRepository.save(agriFavourableWeather);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FavourableWeather" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FavourableWeather" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage deleteAgriFavourableWeatherById(int id) {
		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);
			if (foundFavourableWeather.isPresent()) {

				AgriFavourableWeather agriFavourableWeather = foundFavourableWeather.get();
				agriFavourableWeather.setStatus(APIConstants.STATUS_DELETED);

				agriFavourableWeather = agriFavourableWeatherRepository.save(agriFavourableWeather);

				approvalUtil.delete(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, agriFavourableWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FavourableWeather" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-FavourableWeather" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriFavourableWeatherById

	public AgriFavourableWeather findAgriFavourableWeatherById(int id) {
		try {
			Optional<AgriFavourableWeather> foundFavourableWeather = agriFavourableWeatherRepository.findById(id);
			if (foundFavourableWeather.isPresent()) {
				return foundFavourableWeather.get();
			} else {
				throw new DoesNotExistException("Agri-FavourableWeather" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriFavourableWeatherById

	@Transactional
	public ResponseMessage moveToMaster(Integer id) {
		try {
			Optional<AgriFavourableWeatherMissing> foundMissingAgrifavoWeather = agriFavourableWeatherMissingRepository.findById(id);

			if (foundMissingAgrifavoWeather.isPresent()) {
				AgriFavourableWeather agriFavoWeather = new AgriFavourableWeather();
				AgriFavourableWeatherMissing agriFavoWeatherMissing = foundMissingAgrifavoWeather.get();
				
				agriFavoWeather.setCommodityId(agriFavoWeatherMissing.getCommodityId());
				agriFavoWeather.setPhenophaseId(agriFavoWeatherMissing.getPhenophaseId());
				agriFavoWeather.setWeatherParameterId(agriFavoWeatherMissing.getWeatherParameterId());
				agriFavoWeather.setSpecificationAverage(agriFavoWeatherMissing.getSpecificationAverage());
				agriFavoWeather.setSpecificationLower(agriFavoWeatherMissing.getSpecificationLower());
				agriFavoWeather.setSpecificationUpper(agriFavoWeatherMissing.getSpecificationUpper());
				agriFavoWeather.setStatus(agriFavoWeatherMissing.getStatus());
				
				AgriFavourableWeather saveAgriFavoWeather = agriFavourableWeatherRepository.save(agriFavoWeather);
				
				agriFavourableWeatherMissingRepository.deleteById(id);

				System.out.println("AFavoWeather Id : " + saveAgriFavoWeather.getId());
				approvalUtil.addRecord(DBConstants.TBL_AGRI_FAVOURABLE_WEATHER, saveAgriFavoWeather.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Favourable-Weather-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Favourable-Weather-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
