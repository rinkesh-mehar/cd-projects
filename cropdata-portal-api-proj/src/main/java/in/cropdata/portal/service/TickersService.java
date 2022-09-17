package in.cropdata.portal.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.model.MarketPrice;
import in.cropdata.portal.model.TickerCommodity;
import in.cropdata.portal.model.TickerCommodityStress;
import in.cropdata.portal.repository.MarketPriceRepository;
import in.cropdata.portal.repository.TickerCommodityRepository;
import in.cropdata.portal.repository.TickerCommodityStressRepository;
import in.cropdata.portal.vo.GeoDistrictInfVO;

@Service
public class TickersService {

	@Autowired
	TickerCommodityRepository tickerCommodityRepository;

	@Autowired
	TickerCommodityStressRepository tickerCommodityStressRepository;

	@Autowired
	MarketPriceRepository marketPriceRepository;

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

			int checkExist = marketPriceRepository.checkExistMarketPrice(entity.getCommodityId(), entity.getMarketId(),
					entity.getVarietyId());

			if (checkExist > 0) {
				message.setError("Already Exist Market Price Info...");
				message.setSuccess(false);

			} else {

				marketPriceRepository.save(entity);

				message.setMessage("Market Price Info Added Successfully..");
				message.setSuccess(true);
			}

		} catch (Exception e) {
			message.setError("Unable to add Market Price Info " + e.getMessage());
			message.setSuccess(false);
		}
		return message;
	}

	public MarketPrice getMarketPriceById(Integer id) {
		try {

			MarketPrice marketPrice = marketPriceRepository.findById(id).orElse(null);

			GeoDistrictInfVO stateAndDistrictCode = tickerCommodityRepository
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

			return tickerCommodityRepository.findCommodityAndStress(id);

		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Object> getMarketPriceListDateWise() {
		try {

			LocalDate localDate = LocalDate.now();
			DecimalFormat formatr = new DecimalFormat("#,###");

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			ZoneId defaultZoneId = ZoneId.systemDefault();

			List<Map<String, Object>> getMarketPriceList = null;
			List<Map<String, Object>> list = new ArrayList<>();

			Map<String, Object> map = new HashMap<String, Object>();

			getMarketPriceList = marketPriceRepository.getMarketPriceListDateWise(localDate);

			if (getMarketPriceList != null && getMarketPriceList.size() > 0) {
				Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
				String strDate = formatter.format(date);
				map.put("date", strDate);
			} else {
				LocalDate yesterdayDate = localDate.minus(Period.ofDays(1));
				getMarketPriceList = marketPriceRepository.getMarketPriceListDateWise(yesterdayDate);

				if (getMarketPriceList != null && getMarketPriceList.size() > 0) {

					Date date = Date.from(yesterdayDate.atStartOfDay(defaultZoneId).toInstant());
					String strDate = formatter.format(date);
					map.put("date", strDate);

				} else {
					LocalDate dateBeforeYesterday = localDate.minus(Period.ofDays(2));
					getMarketPriceList = marketPriceRepository.getMarketPriceListDateWise(dateBeforeYesterday);
					Date date = Date.from(dateBeforeYesterday.atStartOfDay(defaultZoneId).toInstant());
					String strDate = formatter.format(date);
					map.put("date", strDate);
				}
			}

			String s = "/-".toString();

			for (Map<String, Object> m : getMarketPriceList) {

				Map<String, Object> marketWiseMap = new HashMap<>();
				marketWiseMap.put("MarketName", m.get("Market").toString().toLowerCase());
				marketWiseMap.put("Commodity", m.get("Commodity").toString().toLowerCase());
				marketWiseMap.put("Variety", m.get("Variety").toString().toLowerCase());
				marketWiseMap.put("MinPrice", formatr.format(((m.get("MinPrice")))) + s);
				marketWiseMap.put("MaxPrice", formatr.format(m.get("MaxPrice")) + s);
				marketWiseMap.put("ModalPrice", formatr.format(m.get("ModalPrice")) + s);

				list.add(marketWiseMap);

			}
			map.put("data", list);

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

				List<String> phenophaseList = Arrays.asList(mainMap.get("Phenophase") == null ? new String[0]
						: mainMap.get("Phenophase").toString().split(","));

				List<String> stressList = Arrays.asList(
						mainMap.get("Stress") == null ? new String[0] : mainMap.get("Stress").toString().split("##"));

				for (int i = 0; i < phenophaseList.size(); i++) {

					Map<String, Object> phenophaseMap = new HashMap<>();

					phenophaseMap.put("Commodity", mainMap.get("Commodity"));

					phenophaseMap.put("Phenophase", phenophaseList.get(i));
					phenophaseMap.put("Stress", stressList.get(i));

					commodityStressList.add(phenophaseMap);

				}
			}

			return commodityStressList;

		} catch (Exception e) {
			throw e;
		}

	}

}
