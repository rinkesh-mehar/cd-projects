/**
 * 
 */
package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import in.cropdata.cdtmasterdata.website.model.MarketPrice;
import in.cropdata.cdtmasterdata.website.service.TickersService;

/**
 * @author cropdata-kunal
 *
 */

@RestController
//@CrossOrigin("*")
@RequestMapping("/site/ticker")
public class TickersController {

	private static final Logger logger = LoggerFactory.getLogger(NewsReportsController.class);

	@Autowired
	TickersService tickersService;

	@GetMapping("/getCommodities")
	public List<Map<String, Object>> getAllCommodities() {

		return tickersService.getAllCommodities();
	}

	@GetMapping("/getVarietiesByCommodity")
	public List<Map<String, Object>> getVarieties(@RequestParam Integer commodityId) {

		return tickersService.getVarietiesByCommodity(commodityId);
	}

	@GetMapping("/getAllState")
	public List<Map<String, Object>> getAllState() {

		return tickersService.getAllState();
	}

	@GetMapping("/getDistrictByState")
	public List<Map<String, Object>> getDistrictByState(@RequestParam Integer stateCode) {

		return tickersService.getDistrictByState(stateCode);
	}

	@GetMapping("/getMarketName")
	public List<Map<String, Object>> getMarketByStateAndDistrict(@RequestParam Integer stateCode,
			@RequestParam Integer districtCode) {

		return tickersService.getMarketByStateAndDistrict(stateCode, districtCode);
	}

	@GetMapping("/getPhenophase")
	public List<Map<String, Object>> getPhenophaseByCommodity(@RequestParam Integer commodityId) {

		return tickersService.getPhenophase(commodityId);
	}

	@GetMapping("/getStress")
	public List<Map<String, Object>> getStressByCommodityAndPhenophase(@RequestParam Integer commodityId,
			@RequestParam Integer phenophaseId) {

		return tickersService.getStress(commodityId, phenophaseId);
	}

	@GetMapping("/getMarketPriceListPaginated")
	public Page<Map<String, Object>> getMarketPriceListPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {

		return tickersService.getMarketPriceListPaginated(page, size, searchText);
	}

	@GetMapping("/getMarketPriceList")
	public List<Map<String, Object>> getMarketPriceList() {

		return tickersService.getMarketPriceList();
	}

	@GetMapping("/getMarketPriceListDateWise")
	public Map<String, Object> getMarketPriceListDateWise() {

		return tickersService.getMarketPriceListDateWise();
	}

	@GetMapping("/getCommodityWiseStress")
	public List<Map<String, Object>> getCommodityWiseStress() {

		return tickersService.getCommodityWiseStress();
	}

	@GetMapping("/getMarketPriceById/{id}")
	public MarketPrice getMarketPriceById(@PathVariable(name = "id", required = true) Integer id) {
		if (id == null) {
			throw new InvalidDataException("id can not be null!");
		}

		logger.info("Get Market Price Details...");
		return tickersService.getMarketPriceById(id);
	}

	@GetMapping("/getCommodityStressById/{id}")
	public Map<String, Object> getCommodityStressById(@PathVariable Integer id) {
		if (id == null) {
			throw new InvalidDataException("id can not be null!");
		}

		logger.info("Get Commodity Stress Details...");
		return tickersService.getCommodityStressById(id);
	}

	@GetMapping("/getCommodityStressListPaginated")
	public Page<Map<String, Object>> getCommodityStressListPaginated(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {

		return tickersService.getCommodityStressListPaginated(page, size, searchText);
	}

	/** Fetching the list of all commodity stress*/
	@GetMapping("/getCommodityStressList")
	public List<Map<String, Object>> getCommodityStressList() {

		return tickersService.getCommodityStressList();
	}

	@PostMapping("/addCommodityStress")
	public ResponseEntity<ResponseMessage> addCommodityStress(@RequestBody Map<String, Object> map) {

		if (map == null) {
			throw new InvalidDataException("Commodity Stress data can not be null!");
		}

		logger.info("Adding Commodity Stress Details...");
		return new ResponseEntity<>(tickersService.addCommodityStress(map), HttpStatus.CREATED);
	}

	@PostMapping("/addMarketPrice")
	public ResponseEntity<ResponseMessage> addMarketPrice(@RequestBody Map<String, Object> map) {

		if (map == null) {
			throw new InvalidDataException("Market Price data can not be null!");
		}

		logger.info("Adding Market Price Details...");
		return new ResponseEntity<>(tickersService.addMarketPrice(map), HttpStatus.CREATED);
	}

	@PutMapping("/updateMarketPrice")
	public ResponseEntity<ResponseMessage> updateMarketPrice(@RequestBody Map<String, Object> map,
			@RequestParam Integer id) {

		if (map == null) {
			throw new InvalidDataException("Market Price data can not be null!");
		}
		if (id == null || id == 0) {
			throw new InvalidDataException("id can not be null or 0");
		}

		logger.info("Updating Market Price Details...");
		return new ResponseEntity<>(tickersService.updateMarketPrice(map, id), HttpStatus.CREATED);
	}

	@PutMapping("/updateCommodityStress")
	public ResponseEntity<ResponseMessage> updateCommodityStress(@RequestBody Map<String, Object> map,
			@RequestParam Integer id) {

		if (map == null) {
			throw new InvalidDataException("Commodity Stress data can not be null!");
		}
		if (id == null || id == 0) {
			throw new InvalidDataException("id can not be null or 0");
		}

		logger.info("Updating Market Price Details...");
		return new ResponseEntity<>(tickersService.updateCommodityStress(map, id), HttpStatus.CREATED);
	}

	@PutMapping("/commodityStress/delete")
	public ResponseEntity<ResponseMessage> deleteCommodityStress(@RequestParam Integer id) {

		if (id == null || id == 0) {
			throw new InvalidDataException("id can not be null or 0");
		}

		logger.info("Deleting Commodity Stress Details...");
		return new ResponseEntity<>(tickersService.deleteCommodityStress(id), HttpStatus.CREATED);
	}

	@PutMapping("/market-price/delete")
	public ResponseEntity<ResponseMessage> deleteMarketPrice(@RequestParam Integer id) {

		if (id == null || id == 0) {
			throw new InvalidDataException("id can not be null or 0");
		}

		logger.info("Deleting Market Price Details...");
		return new ResponseEntity<>(tickersService.deleteMarketPrice(id), HttpStatus.CREATED);
	}

}
