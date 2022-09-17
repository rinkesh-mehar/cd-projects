package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.ProductsAndServicesDTO;
import in.cropdata.cdtmasterdata.website.model.ProductsAndServices;
import in.cropdata.cdtmasterdata.website.model.vo.ProductsAndServicesVO;
import in.cropdata.cdtmasterdata.website.service.ProductsAndServicesService;

@RestController
@RequestMapping("/site/products-and-services")
public class ProductsAndServicesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAndServicesController.class);
	
	@Autowired
	private ProductsAndServicesService productsAndServicesService;

	@GetMapping("/list")
	public List<ProductsAndServicesVO> getProductsAndServicesList() {

		return productsAndServicesService.getProductsAndServicesList();
	}
	
	@GetMapping("/paginatedList")
	public Page<ProductsAndServicesVO> getEnginesListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return productsAndServicesService.getProductsAndServicesListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addTools(@ModelAttribute ProductsAndServicesDTO productsAndServicesDTO) {

		if (productsAndServicesDTO == null) {
			throw new InvalidDataException("Products And Services data can not be null!");
		}
		LOGGER.info("Add Products And Services From Controller...");
		return new ResponseEntity<>(productsAndServicesService.addProductsAndServices(productsAndServicesDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public ProductsAndServices getProductsAndServicesById(@PathVariable(required = true) Integer id) {

		return productsAndServicesService.getProductsAndServicesById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateProductsAndServices(@PathVariable(required = true) Integer id,
			@ModelAttribute ProductsAndServicesDTO productsAndServicesDTO) {

		if (id == null) {
			throw new InvalidDataException("Products And Services Id can not be null!");
		}

		if (productsAndServicesDTO == null) {
			throw new InvalidDataException("Products And Services data can not be null!");
		}

		LOGGER.info("Updating Products And Services From Controller for ID -> {}", id);
		return new ResponseEntity<>(productsAndServicesService.updateProductsAndServices(id, productsAndServicesDTO), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeProductsAndServices(@PathVariable int id) {
		return productsAndServicesService.activeProductsAndServices(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveProductsAndServices(@PathVariable int id) {
		return productsAndServicesService.deactiveProductsAndServices(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteProductsAndServices(@PathVariable int id) {
		return productsAndServicesService.deleteProductsAndServices(id);
	}
	
}
