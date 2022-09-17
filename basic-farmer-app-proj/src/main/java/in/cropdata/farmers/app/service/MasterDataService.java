package in.cropdata.farmers.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.farmers.app.constants.APIConstants;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.masters.dto.CityVillagePinDTO;
import in.cropdata.farmers.app.masters.model.District;
import in.cropdata.farmers.app.masters.model.FarmerFarmOwnershipType;
import in.cropdata.farmers.app.masters.model.FarmerIdProof;
import in.cropdata.farmers.app.masters.model.GeoPanchayat;
import in.cropdata.farmers.app.masters.model.GeoVillage;
import in.cropdata.farmers.app.masters.model.IrrigationSource;
import in.cropdata.farmers.app.masters.model.State;
import in.cropdata.farmers.app.masters.model.Tehsil;
import in.cropdata.farmers.app.masters.model.Variety;
import in.cropdata.farmers.app.masters.repository.AgriCropTypeRepository;
import in.cropdata.farmers.app.masters.repository.CommodityRepository;
import in.cropdata.farmers.app.masters.repository.DistrictRepository;
import in.cropdata.farmers.app.masters.repository.FarmerFarmOwnershipTypeRepository;
import in.cropdata.farmers.app.masters.repository.FarmerIdProofRepository;
import in.cropdata.farmers.app.masters.repository.GeoPanchayatRepository;
import in.cropdata.farmers.app.masters.repository.GeoVillageRepository;
import in.cropdata.farmers.app.masters.repository.IrrigationSourceRepository;
import in.cropdata.farmers.app.masters.repository.MasterDaoRepository;
import in.cropdata.farmers.app.masters.repository.StateRepository;
import in.cropdata.farmers.app.masters.repository.TehsilRepository;
import in.cropdata.farmers.app.masters.repository.VarietyRepository;
import in.cropdata.farmers.app.model.FarmCaseLocation;
import in.cropdata.farmers.app.model.FarmLocation;
import in.cropdata.farmers.app.utils.FarmerAppUtils;

@Service
public class MasterDataService {

	@Autowired
	private DrkFarmerRepository drkFarmerRepository;

	@Autowired
	private CommodityRepository commodityRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private IrrigationSourceRepository irrigationSourceRepository;

	@Autowired
	private VarietyRepository vRepository;

	@Autowired
	private TehsilRepository tehsilRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	private GeoVillageRepository villageRepository;

	@Autowired
	private GeoPanchayatRepository panchayatRepository;

	@Autowired
	FarmerFarmOwnershipTypeRepository farmerFarmOwnershipTypeRepository;

	@Autowired
	private FarmerIdProofRepository farmerIdProofRepository;

	@Autowired
	private AgriCropTypeRepository agriCropTypeRepository;

	@Autowired
	private MasterDaoRepository masterDaoRepository;

	@Autowired
	private FarmerAppUtils farmerAppUtils;

	@Autowired
	private GeoVillageRepository geoVillageRepository;

	public Map<String, Object> getAllgeoStates() {
		Map<String, Object> responseMessage = new LinkedHashMap<>();
		try {
			List<Map<String, Object>> stateListMap = new ArrayList<>();
			List<State> stateList = stateRepository.findAllByStatusAndCountryCode("Active", 1);

			if (stateList != null && stateList.size() > 0) {
				for (State st : stateList) {
					Map<String, Object> state = new HashMap<>();
					state.put("stateCode", st.getStateCode());
					state.put("name", st.getName());
					stateListMap.add(state);
				}

				responseMessage.put("status", true);
				responseMessage.put("message", "Geo State List");
				responseMessage.put("states", stateListMap);
			} else {
				responseMessage.put("status", false);
				responseMessage.put("message", "No State Found.");
				responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}
		} catch (Exception e) {
			responseMessage.put("status", false);
			responseMessage.put("message", "No data Found.");
			responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return responseMessage;
	}

	public Map<String, Object> getTehsilByDistrictCode(int districtCode) {

		Map<String, Object> responseMessage = new LinkedHashMap<>();
		List<Map<String, Object>> dataMapList = null;
		try {
			Map<String, Object> tehsilMap = null;
			List<Tehsil> tehsilList = tehsilRepository.findAllByDistrictCode(districtCode);
			if (tehsilList != null && !tehsilList.isEmpty()) {
				dataMapList = new ArrayList<>();
				for (Tehsil tehsil : tehsilList) {
					tehsilMap = new HashMap<>();
					tehsilMap.put("tehsilCode", tehsil.getTeshilCode());
					tehsilMap.put("name", tehsil.getName());
					dataMapList.add(tehsilMap);
				}
				responseMessage.put("tehsils", dataMapList);
			} else {
				responseMessage.put("status", false);
				responseMessage.put("message", "No Tehsil Found.");
				responseMessage.put("tehsil", new ArrayList<>());
				responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}

			List<Map<String, Object>> geoCityList = tehsilRepository.findGeoCityByDistrictCode(districtCode);
			Map<String, Object> geoCityMap = null;
			if (geoCityList != null && !geoCityList.isEmpty()) {
				dataMapList = new ArrayList<>();
				for (Map<String, Object> cityMap : geoCityList) {
					geoCityMap = new HashMap<>();
					geoCityMap.put("cityCode", cityMap.get("code"));
					geoCityMap.put("name", cityMap.get("name"));
					dataMapList.add(geoCityMap);

				}
				responseMessage.put("cities", dataMapList);

			} else {
				responseMessage.put("status", false);
				responseMessage.put("message", "No Cities Found.");
				responseMessage.put("city", new ArrayList<>());
				responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}

			responseMessage.put("status", true);
			responseMessage.put("message", "Tehsil and City list by district code");

		} catch (Exception e) {
			responseMessage.put("status", false);
			responseMessage.put("message", "No data Found.");
			responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return responseMessage;
	}

	public Map<String, Object> getDistrictListByStateCode(Integer stateCode) {
		Map<String, Object> responseMessage = new LinkedHashMap<>();
		try {

			List<Map<String, Object>> districtListMap = new ArrayList<>();
			List<District> districtList = districtRepository.findAllByStateCode(stateCode);

			if (districtList != null & districtList.size() > 0) {
				for (District st : districtList) {
					Map<String, Object> district = new HashMap<>();
					district.put("districtCode", st.getDistrictCode());
					district.put("name", st.getName());
					districtListMap.add(district);
				}
				responseMessage.put("status", true);
				responseMessage.put("message", "District List By State");
				responseMessage.put("districts", districtListMap);

			} else {
				responseMessage.put("status", false);
				responseMessage.put("message", "No District Found.");
				responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}
		} catch (Exception e) {
			responseMessage.put("status", false);
			responseMessage.put("message", "No District Found.");
			responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return responseMessage;
	}

	public Map<String, Object> getPanchayatByTehsil(Integer tehsilCode) {
		Map<String, Object> geoRecords = null;
		try {
			if (tehsilCode != null) {
				geoRecords = new HashMap<>();

//				List<GeoVillage> geoVillageList = villageRepository.findGeoVillageByTehsilCode(tehsilCode);
				List<GeoPanchayat> geoPanchayatList = panchayatRepository.findGeoPanchayatByTehsilCode(tehsilCode);

				/*
				 * if (!(geoVillageList.isEmpty()) ) { geoRecords.put("geoVillages",
				 * geoVillageList); }
				 */
				if (!(geoPanchayatList.isEmpty())) {
					geoRecords.put("geoPanchayats", geoPanchayatList);
					geoRecords.put("status", true);
					geoRecords.put("message", "Panchayat By Tehsil");

				} else {
					geoRecords.put("status", false);
					geoRecords.put("message", "No Panchayat Found.");
					geoRecords.put("errorCode", ErrorConstants.NO_DATA_FOUND);
				}
			}
//			geoRecords.put("status", true);
//			geoRecords.put("message", "Panchayat and Village List By Tehsil");

		} catch (Exception ex) {
			geoRecords = new HashMap<>();
			geoRecords.put("status", false);
			geoRecords.put("message", "Panchayat are not found for your selected Tehsil.");
			geoRecords.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return geoRecords;
	}

	public Map<String, Object> getVillageByPanchayat(Integer panchayatCode) {
		Map<String, Object> geoRecords = null;
		try {
			if (panchayatCode != null) {
				geoRecords = new HashMap<>();

				List<GeoVillage> geoVillageList = villageRepository.findGeoVillageByPanchayatCode(panchayatCode);
//				List<GeoPanchayat> geoPanchayatList = panchayatRepository.findGeoPanchayatByTehsilCode(tehsilCode);

				if (!(geoVillageList.isEmpty())) {
					geoRecords.put("geoVillages", geoVillageList);
					geoRecords.put("status", true);
					geoRecords.put("message", "Village By Panchayat");
				} else {
					geoRecords.put("status", false);
					geoRecords.put("message", "No Village found");
					geoRecords.put("errorCode", ErrorConstants.NO_DATA_FOUND);
				}
				/*
				 * if (!(geoPanchayatList.isEmpty())) { geoRecords.put("geoPanchayats",
				 * geoPanchayatList); }
				 */
			}
//			geoRecords.put("status", true);
//			geoRecords.put("message", "Panchayat and Village List By Tehsil");

		} catch (Exception ex) {
			geoRecords = new HashMap<>();
			geoRecords.put("status", false);
			geoRecords.put("message", "No Village Found.");
			geoRecords.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return geoRecords;
	}

	public Map<String, Object> getAllCommodityWaterSourceLandOwnershipDoc(Map<String, Object> _data) {
		Map<String, Object> masters = new LinkedHashMap<>();
		List<Map<String, Object>> commodityList = new ArrayList<>();
		List<Map<String, Object>> irrigationSourceList = new ArrayList<>();
		List<Map<String, Object>> landOwnershipDocList = new ArrayList<>();

		try {

//			List<Map<String, Object>> allActiveCommodity = masterDaoRepository.getAllActiveCommodity(115,"2021-06-24");
//			if (!allActiveCommodity.isEmpty()) {
//				allActiveCommodity.forEach(data -> {
//					Map<String, Object> commoditytMap = new HashMap<String, Object>();
//					commoditytMap.put("ID", data.get("ID"));
//					commoditytMap.put("name", data.get("name") + " (" + data.get("SowingWeekStart") + "-"
//							+ data.get("SowingWeekEnd") + ")");
//					commoditytMap.put("zonalCommodityId", data.get("zonalCommodityId"));
//					commodityList.add(commoditytMap);
//				});
//			}

			Map<String, Object> latLongObj = new HashMap<String, Object>();
			latLongObj.put("Latitude", _data.get("latitude"));
			latLongObj.put("Longitude", _data.get("longitude"));

			//Python Api Will provide ACZ ID here.
			FarmLocation dataByCordinates = farmerAppUtils.getDataByCordinates(latLongObj);
			if (dataByCordinates != null && dataByCordinates.getStatus().equals("true")
					&& (!dataByCordinates.getData().isEmpty())) {

				FarmCaseLocation caseLocation = dataByCordinates.getData().get(0);
				if (caseLocation != null) {
					var sowingDate = "";
					Integer cropTypeId = (Integer)_data.get("cropTypeID");
					if (_data.containsKey("sowingDate")) {
						sowingDate = _data.get("sowingDate").toString();
					}
					List<Map<String, Object>> allActiveCommodity = masterDaoRepository.getAllActiveCommodity(
							caseLocation.getAczID(), sowingDate,cropTypeId);
					if (!allActiveCommodity.isEmpty()) {
						allActiveCommodity.forEach(data -> {
							Map<String, Object> commoditytMap = new HashMap<String, Object>();
							commoditytMap.put("ID", data.get("ID"));
							commoditytMap.put("name", data.get("name") + " (" + data.get("SowingWeekStart") + "-"
									+ data.get("SowingWeekEnd") + ")");
							commoditytMap.put("zonalCommodityId", data.get("zonalCommodityId"));
							commodityList.add(commoditytMap);
						});
					}
				}//
			}

			List<IrrigationSource> allIrrigationSource = irrigationSourceRepository.getAllIrrigationSource();
			allIrrigationSource.forEach(data -> {
				Map<String, Object> irrigationSourceMap = new HashMap<String, Object>();
				irrigationSourceMap.put("ID", data.getId());
				irrigationSourceMap.put("name", data.getName());
				irrigationSourceList.add(irrigationSourceMap);
			});
			List<FarmerFarmOwnershipType> allActivefarmerFarmOwnershipType = farmerFarmOwnershipTypeRepository
					.getAllActivefarmerFarmOwnershipType();
			allActivefarmerFarmOwnershipType.forEach(data -> {
				Map<String, Object> landOwnershipDocMap = new HashMap<String, Object>();
				landOwnershipDocMap.put("ID", data.getId());
				landOwnershipDocMap.put("name", data.getName());
				landOwnershipDocList.add(landOwnershipDocMap);
			});

			masters.put("status", true);
			masters.put("message", "All Case Master List ");
			masters.put("commodities", commodityList);
			masters.put("irrigationSources", irrigationSourceList);
			masters.put("landOwnershipTypes", landOwnershipDocList);

		} catch (Exception e) {
			System.err.println(e.getMessage());
//			System.err.println(e.getStackTrace());
			e.printStackTrace();
			masters.put("status", false);
			masters.put("message", "No data Found.");
			masters.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return masters;
	}

	public Map<String, Object> getAllVarietyByCommodityIdAndStateCode(Integer zonalCommodityID) {
		Map<String, Object> responseMessage = new LinkedHashMap<>();
		try {
			List<Map<String, Object>> varietyListMap = new ArrayList<>();

			List<Variety> varietyList = vRepository.getAllVarietyByCommodityIdAndStateCode(zonalCommodityID);

			if (varietyList != null && !varietyList.isEmpty()) {
				for (Variety st : varietyList) {
					Map<String, Object> variety = new HashMap<>();
					variety.put("ID", st.getId());
					variety.put("name", st.getName());
					varietyListMap.add(variety);
				}
				responseMessage.put("status", true);
				responseMessage.put("message", "Variety List by Commodity");
				responseMessage.put("varieties", varietyListMap);

			} else {

				responseMessage.put("status", false);
				responseMessage.put("message", "No Variety Found.");
				responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}
			return responseMessage;
		} catch (Exception e) {

			responseMessage.put("status", false);
			responseMessage.put("message", "No Variety Found.");
			responseMessage.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			return responseMessage;
		}
	}

	public Map<String, Object> getFarmerIdProof() {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		List<FarmerIdProof> farmerIdProofList = farmerIdProofRepository
				.findByStatusIn(Arrays.asList("Active", "Approved"));
		if (!farmerIdProofList.isEmpty()) {
			responseMap.put("status", true);
			responseMap.put("idProofList", farmerIdProofList);
			responseMap.put("message", "Successfully delivered farmer id proof");
		} else {
			responseMap.put("status", false);
			responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			responseMap.put("message", "No data found of farmer id proof");
		}
		return responseMap;
	}

	public Map<String, Object> getAllCommodityByDistrict(Integer districtCode, String authToken) {
		Map<String, Object> masters = new LinkedHashMap<String, Object>();
		Map<String, Object> responseMessage = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> commodityList = new ArrayList<>();
		Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authToken);
		DrkFarmer farmer = null;
		if (farmerFound.isPresent()) {
			farmer = farmerFound.get();
			List<Map<String, Object>> neighbourDistrictList = masterDaoRepository
					.getNeighbourDistrictByFarmerID(farmer.getId());
			String distinctNeighbourDistrict = this.getDistinctNeighbourDistrict(neighbourDistrictList);
			List<Map<String, Object>> allActiveCommodityByDistrictCode = masterDaoRepository
					.getActiveCommodityByIncludingNeighboringDistrictCode(farmer.getId(), distinctNeighbourDistrict);
			try {
				for (Map<String, Object> cm : allActiveCommodityByDistrictCode) {
					Map<String, Object> commoditytMap = new HashMap<String, Object>();
					commoditytMap.put("ID", cm.get("ID"));
					commoditytMap.put("name", cm.get("name"));
					commodityList.add(commoditytMap);
				}
				if (!commodityList.isEmpty()) {
					masters.put("status", true);
					masters.put("message", "Commodity List By District");
					masters.put("commodities", commodityList);
				} else {
					masters.put("status", false);
					masters.put("message",
							"Sorry, no crops are available for your district. CropData shall soon provide the data for your location. Please try again later");
					masters.put("errorCode", ErrorConstants.NO_DATA_FOUND);
				}
			} catch (Exception e) {
				masters.put("status", false);
				masters.put("message", "No Commodity Found.");
				masters.put("errorCode", ErrorConstants.NO_DATA_FOUND);
			}
			return masters;
		} else {
			responseMessage.put("status", false);
			responseMessage.put("errorCode", ErrorConstants.UNAUTHORIZED);
			responseMessage.put("message", "Unauthorized User");
			return responseMessage;
		}

	}

	/**
	 * It is use to search address based on the city,village and pincode It is pass
	 * two paramter 1.stateCode 2.searchedCharacter
	 */
	public Map<String, Object> getSearchList(String searchedCharacter) {
		Map<String, Object> response = new LinkedHashMap<>();
		List<Map<String, Object>> addCityVillagePinDTOList = new ArrayList<>();
		try {
			if (searchedCharacter != null) {
				String searchText = searchedCharacter.concat("%");

				/** find list of address from DB based on statecode and searchedCharacter */
				List<CityVillagePinDTO> cityVillagePinDTOList = villageRepository.findBySearchedCharacter(searchText);
				if (cityVillagePinDTOList != null && !cityVillagePinDTOList.isEmpty()) {
					for (CityVillagePinDTO cityVillagePin : cityVillagePinDTOList) {

						Map<String, Object> stringObjectMap = new HashMap<>();
						stringObjectMap.put("name", cityVillagePin.getName());
						stringObjectMap.put("districtCode", cityVillagePin.getDistrictCode());
						stringObjectMap.put("districtName", cityVillagePin.getDistrictName());
						stringObjectMap.put("stateCode", cityVillagePin.getStateCode());
						stringObjectMap.put("stateName", cityVillagePin.getStateName());

						/** Change address type based on DB value */
						if (cityVillagePin.getType().equals("village")) {
							stringObjectMap.put("villageCode", cityVillagePin.getId());
							stringObjectMap.put("cityCode", 0);
							stringObjectMap.put("panchayatCode", cityVillagePin.getPanchayatCode());
//							stringObjectMap.put("panchayatName", cityVillagePin.getPanchayatName());
							stringObjectMap.put("panchayatName", cityVillagePin.getPanchayatName().equals("0") ? null: cityVillagePin.getPanchayatName());
							stringObjectMap.put("tehsilCode", cityVillagePin.getTehsilCode());
							stringObjectMap.put("tehsilName", cityVillagePin.getTeshilName());
							stringObjectMap.put("type", APIConstants.VILLAGE_TYPE);

						} else {
							stringObjectMap.put("cityCode", cityVillagePin.getId());
							stringObjectMap.put("villageCode", 0);
							stringObjectMap.put("type", APIConstants.CITY_TYPE);
						}
						addCityVillagePinDTOList.add(stringObjectMap);
					}
					response.put("cityVillageList", addCityVillagePinDTOList);
					response.put("status", true);
					response.put("message", "City/Village list dilivered successfully.");
				} else {
					response.put("status", false);
					response.put("message", "Not Found City's or Villages Against the given State.");
					response.put("errorCode", ErrorConstants.NO_DATA_FOUND);
				}
			} else {
				response.put("status", false);
				response.put("message", "Please check  searchedCharacter.");
				response.put("errorCode", ErrorConstants.PARAM_MISSING);
			}
		} catch (Exception e) {
			response.put("status", false);
			response.put("message", "Not Found City's or Villages");
			response.put("errorCode", ErrorConstants.NO_DATA_FOUND);
		}
		return response;
	}

	public String getDistinctNeighbourDistrict(List<Map<String, Object>> list) {
		Set<String> districtSet = new HashSet<>();

		if (!list.isEmpty()) {
			for (Map<String, Object> map : list) {
				String[] str = map.get("Districts").toString().split(",");
				for (String s : str) {
					districtSet.add(s);
				}
			}

		}
		return districtSet.toString().replaceAll("(^\\[|\\]$)","").replaceAll("\\s", "");
	}
}
