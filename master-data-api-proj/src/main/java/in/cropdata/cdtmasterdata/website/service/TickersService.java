package in.cropdata.cdtmasterdata.website.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoDistrictInfDto;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.website.model.MarketPrice;
import in.cropdata.cdtmasterdata.website.model.TickerCommodity;
import in.cropdata.cdtmasterdata.website.model.TickerCommodityStress;
import in.cropdata.cdtmasterdata.website.repository.MarketPriceRepository;
import in.cropdata.cdtmasterdata.website.repository.TickerCommodityRepository;
import in.cropdata.cdtmasterdata.website.repository.TickerCommodityStressRepository;

@Service
public class TickersService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TickersService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	TickerCommodityRepository tickerCommodityRepository;

	@Autowired
	TickerCommodityStressRepository tickerCommodityStressRepository;

	@Autowired
	MarketPriceRepository marketPriceRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<Map<String, Object>> getAllCommodities() {

		try {

			return tickerCommodityRepository.getAllCommodities();

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getVarietiesByCommodity(Integer commodityId) {
		try {

			return tickerCommodityRepository.getVarietiesByCommodity(commodityId);

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getAllState() {
		try {

			return tickerCommodityRepository.getAllState();

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getDistrictByState(Integer stateCode) {
		try {

			return tickerCommodityRepository.getDistrictByState(stateCode);

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getMarketByStateAndDistrict(Integer stateCode, Integer districtCode) {
		try {

			return tickerCommodityRepository.getMarketByStateAndDistrict(stateCode, districtCode);

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getPhenophase(Integer commodityId) {
		try {

			List<Map<String, Object>> phenophaseList = tickerCommodityRepository.getPhenophase(commodityId);
			return phenophaseList;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getStress(Integer commodityId, Integer phenophaseId) {
		try {

			return tickerCommodityRepository.getStress(commodityId, phenophaseId);

		} catch (Exception e) {
			throw e;
		}
	}

	public Page<Map<String, Object>> getMarketPriceListPaginated(int page, int size, String searchText) {
		try {

			searchText = "%" + searchText + "%";

			Pageable sortedByIdDesc = PageRequest.of(page, size);

			return marketPriceRepository.getMarketPriceListPaginated(sortedByIdDesc, searchText);

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getMarketPriceList() {
		try {

			return marketPriceRepository.getMarketPriceList();

		} catch (Exception e) {
			throw e;
		}
	}

	public Page<Map<String, Object>> getCommodityStressListPaginated(int page, int size, String searchText) {
		try {

			searchText = "%" + searchText + "%";

			Pageable sortedByIdDesc = PageRequest.of(page, size);

			return tickerCommodityRepository.getCommodityStressListPaginated(sortedByIdDesc, searchText);

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getCommodityStressList() {
		try {

			return tickerCommodityRepository.getCommodityStressList();

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addCommodityStress(Map<String, Object> data) {

		ResponseMessage message = new ResponseMessage();
		try {

			ObjectMapper mapper = new ObjectMapper();
			TickerCommodity entity = mapper.convertValue(data, TickerCommodity.class);

			TickerCommodity commodity = new TickerCommodity();
			commodity.setCommodityId(entity.getCommodityId());
			commodity.setPhenophaseId(entity.getPhenophaseId());
			commodity.setStatus(APIConstants.STATUS_INACTIVE);
			TickerCommodity newEntry = tickerCommodityRepository.save(commodity);

			if (entity.getTickerCommodityStressList() != null && entity.getTickerCommodityStressList().size() > 0) {

				List<TickerCommodityStress> stressList = new ArrayList<TickerCommodityStress>();

				for (Integer i : entity.getTickerCommodityStressList()) {

					TickerCommodityStress stress = new TickerCommodityStress();
					stress.setTickerId(newEntry.getId());
					stress.setStressId(i);
					stressList.add(stress);

				}
				tickerCommodityStressRepository.saveAll(stressList);
			}

			message.setMessage("Commodity Stress Info Added Successfully..");
			message.setSuccess(true);

		} catch (Exception e) {
			message.setMessage("Unable to add Commodity Stress Info " + e.getMessage());
			message.setSuccess(false);
		}
		return message;

	}

	public ResponseMessage addMarketPrice(Map<String, Object> data) {
		ResponseMessage message = new ResponseMessage();
		try {

			ObjectMapper mapper = new ObjectMapper();
			MarketPrice entity = mapper.convertValue(data, MarketPrice.class);

//			int checkExist = marketPriceRepository.checkExistMarketPrice(entity.getCommodityId(), entity.getMarketId(),
//					entity.getVarietyId());
//
//			if (checkExist > 0) {
//				message.setError("Already Exist Market Price Info...");
//				message.setSuccess(false);
//
//			} else {

			marketPriceRepository.save(entity);

			message.setMessage("Market Price Info Added Successfully..");
			message.setSuccess(true);
//			}

		} catch (Exception e) {
			message.setError("Unable to add Market Price Info " + e.getMessage());
			message.setSuccess(false);
		}
		return message;
	}

	public MarketPrice getMarketPriceById(Integer id) {
		try {

			MarketPrice marketPrice = marketPriceRepository.findById(id).orElse(null);

			GeoDistrictInfDto stateAndDistrictCode = tickerCommodityRepository
					.getStateAndDistrictByMarketId(marketPrice.getMarketId());

			marketPrice.setStateId(stateAndDistrictCode.getStateCode());
			marketPrice.setDistrictId(stateAndDistrictCode.getDistrictCode());

			return marketPrice;

		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Object> getCommodityStressById(Integer id) {
		try {

			// if (tickerCommodity != null) {
//				List<Integer> stressList = new ArrayList(Arrays.asList(tickerCommodity.get("Stress").toString().split(",")));
//				tickerCommodity.setTickerCommodityStressList(stressList);
//			}

			return tickerCommodityRepository.findCommodityAndStress(id);

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage updateMarketPrice(Map<String, Object> map, Integer id) {

		ResponseMessage message = new ResponseMessage();

		try {

			ObjectMapper mapper = new ObjectMapper();
			MarketPrice entity = mapper.convertValue(map, MarketPrice.class);

			if (entity.getStatus().equals("")) {
				entity.setStatus("Inactive");
			}

			Optional<MarketPrice> marketPrice = marketPriceRepository.findById(id);

			if (marketPrice.isPresent()) {

				MarketPrice mPrice = marketPrice.get();
				mPrice.setCommodityId(entity.getCommodityId());
				mPrice.setVarietyId(entity.getVarietyId());
				mPrice.setMarketId(entity.getMarketId());
				mPrice.setMinPrice(entity.getMinPrice());
				mPrice.setMaxPrice(entity.getMaxPrice());
				mPrice.setModalPrice(entity.getModalPrice());
				mPrice.setStatus(entity.getStatus());

				marketPriceRepository.save(mPrice);

				message.setMessage("Market Price Info Updated Successfully..");
				message.setSuccess(true);

			}

		} catch (Exception e) {
			message.setMessage("Unable to update Market Price Info " + e.getMessage());
			message.setSuccess(false);
		}
		return message;
	}

	public ResponseMessage updateCommodityStress(Map<String, Object> map, Integer id) {

		ResponseMessage message = new ResponseMessage();
		try {

			ObjectMapper mapper = new ObjectMapper();
			TickerCommodity entity = mapper.convertValue(map, TickerCommodity.class);

			if (entity.getStatus().equals("")) {
				entity.setStatus("Inactive");
			}

			Optional<TickerCommodity> tickerCommodity = tickerCommodityRepository.findById(id);

			if (tickerCommodity.isPresent()) {
				TickerCommodity tCommodity = tickerCommodity.get();
				tCommodity.setCommodityId(entity.getCommodityId());
				tCommodity.setPhenophaseId(entity.getPhenophaseId());
				tCommodity.setStatus(entity.getStatus());

				tickerCommodityRepository.save(tCommodity);
			}

			tickerCommodityStressRepository.deleteByTickerId(id);

			if (entity.getTickerCommodityStressList() != null && entity.getTickerCommodityStressList().size() > 0) {

				List<TickerCommodityStress> stressList = new ArrayList<TickerCommodityStress>();

				for (Integer i : entity.getTickerCommodityStressList()) {

					TickerCommodityStress stress = new TickerCommodityStress();
					stress.setTickerId(id);
					stress.setStressId(i);
					stress.setStatus(entity.getStatus());
					stressList.add(stress);

				}
				tickerCommodityStressRepository.saveAll(stressList);
			}

			message.setMessage("Ticker Commodity Stress Info Updated Successfully..");
			message.setSuccess(true);

		} catch (Exception e) {
			message.setMessage("Unalble to update Ticker Commodity Stress " + e.getMessage());
			message.setSuccess(false);
		}

		return message;
	}

	public ResponseMessage deleteCommodityStress(Integer commodityStressId) {
		try {

			Optional<TickerCommodity> dbParameterDetail = tickerCommodityRepository.findById(commodityStressId);

			if (dbParameterDetail.isPresent()) {

				LOGGER.info("deleting commodity stress info...");

				tickerCommodityStressRepository.deleteCommodityStressById(commodityStressId);
				tickerCommodityRepository.deleteCommodityById(commodityStressId);

				return responseMessageUtil.sendResponse(true,
						"commodityStress" + APIConstants.RESPONSE_DELETE_SUCCESS + commodityStressId, "");
			} else {
				return responseMessageUtil.sendResponse(false,
						"commodityStressId Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + commodityStressId, "");
			}

		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public ResponseMessage deleteMarketPrice(Integer marketPriceId) {
		try {

			Optional<MarketPrice> dbParameterDetail = marketPriceRepository.findById(marketPriceId);

			if (dbParameterDetail.isPresent()) {

				LOGGER.info("deleting marketPriceId info...");

				marketPriceRepository.deleteMarketPrice(marketPriceId);

				return responseMessageUtil.sendResponse(true,
						"marketPrice" + APIConstants.RESPONSE_DELETE_SUCCESS + marketPriceId, "");
			} else {
				return responseMessageUtil.sendResponse(false,
						"marketPriceId Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + marketPriceId, "");
			}

		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	public Map<String, Object> getMarketPriceListDateWise() {
		try {

			LocalDate localDate = LocalDate.now();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			ZoneId defaultZoneId = ZoneId.systemDefault();

			List<Map<String, Object>> getMarketPriceList = null;

			Map<String, Object> map = new HashMap<String, Object>();

			getMarketPriceList = marketPriceRepository.getMarketPriceListDateWise(localDate);

			if (getMarketPriceList != null && getMarketPriceList.size() > 0) {
				Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
				String strDate = formatter.format(date);
				map.put("date", strDate);
			} else {
				LocalDate yesterdayDate = localDate.minus(Period.ofDays(1));
				getMarketPriceList = marketPriceRepository.getMarketPriceListDateWise(yesterdayDate);
				Date date = Date.from(yesterdayDate.atStartOfDay(defaultZoneId).toInstant());
				String strDate = formatter.format(date);
				map.put("date", strDate);
			}

			String s = "/-".toString();
			NumberFormat myFormat = NumberFormat.getInstance();

			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> maimMap : getMarketPriceList) {

				Map<String, Object> commodityMap = new HashMap<String, Object>();

				commodityMap.put("Commodity", maimMap.get("Commodity").toString().toLowerCase());

				List<String> marketList = Arrays.asList(
						maimMap.get("Market") == null ? new String[0] : maimMap.get("Market").toString().split(","));

				List<String> varietyList = Arrays.asList(
						maimMap.get("Variety") == null ? new String[0] : maimMap.get("Variety").toString().split(","));

				List<String> minPriceList = Arrays.asList(maimMap.get("MinPrice") == null ? new String[0]
						: maimMap.get("MinPrice").toString().split(","));

				List<String> maxPriceList = Arrays.asList(maimMap.get("MaxPrice") == null ? new String[0]
						: maimMap.get("MaxPrice").toString().split(","));

				List<String> modalPriceList = Arrays.asList(maimMap.get("ModalPrice") == null ? new String[0]
						: maimMap.get("ModalPrice").toString().split(","));

				List<Map<String, Object>> markets = new ArrayList<>();

				for (int i = 0; i < marketList.size(); i++) {

					Map<String, Object> marketWiseMap = new HashMap<>();
					marketWiseMap.put("Name", marketList.get(i).toLowerCase());
					marketWiseMap.put("Variety", varietyList.get(i).toLowerCase());
					marketWiseMap.put("Min Price",
							myFormat.format(Double.parseDouble(minPriceList.get(i))) + s.toString());
					marketWiseMap.put("Max Price", myFormat.format(Double.parseDouble(maxPriceList.get(i))) + s);
					marketWiseMap.put("Modal Price", myFormat.format(Double.parseDouble(modalPriceList.get(i))) + s);

					markets.add(marketWiseMap);

				}

				commodityMap.put("markets", markets);

				data.add(commodityMap);
			}

			map.put("data", data);

			return map;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getCommodityWiseStress() {

		try {

			List<Map<String, Object>> getCommodityWiseStress = tickerCommodityRepository.getCommodityWiseStress();
			List<Map<String, Object>> commodityStressList = new ArrayList<>();

			for (Map<String, Object> mainMap : getCommodityWiseStress) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Commodity", mainMap.get("Commodity"));

				List<Map<String, Object>> data = new ArrayList<>();

				List<String> phenophaseList = Arrays.asList(mainMap.get("Phenophase") == null ? new String[0]
						: mainMap.get("Phenophase").toString().split(","));

				List<String> stressList = Arrays.asList(
						mainMap.get("Stress") == null ? new String[0] : mainMap.get("Stress").toString().split("##"));

				for (int i = 0; i < phenophaseList.size(); i++) {

					Map<String, Object> phenophaseMap = new HashMap();

					phenophaseMap.put("Phenophase", phenophaseList.get(i));
					phenophaseMap.put("Stress", stressList.get(i));

					data.add(phenophaseMap);

				}

				map.put("data", data);
				commodityStressList.add(map);

			}

			return commodityStressList;

		} catch (Exception e) {
			throw e;
		}

	}

}
