package in.cropdata.gateway.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.gateway.dto.ServiceInstanceDTO;
import in.cropdata.gateway.model.Zuul;
import in.cropdata.gateway.response.ApiResponseEntity;
import in.cropdata.gateway.service.ZuulRouteService;
import in.cropdata.gateway.service.ZuulService;

/**
 * @author Vivek Gajbhiye
 *
 */
@RestController
@RequestMapping("/api-gateway")
public class ZuulRouteController {

	@Autowired
	ZuulService zuulService;

	@Autowired
	DiscoveryClient client;

	@Autowired
	ZuulRouteService zuulRouteService;

	@GetMapping("/dashboard")
	public Map<String, List<ServiceInstanceDTO>> showAllServices() {
		return this.zuulService.showAllServices();
	}

	@PostMapping("/add")
	public ApiResponseEntity addNewRoutes(@RequestBody Zuul zuul) {
		return this.zuulService.addRoute(zuul);
	}

	
	@PutMapping("/update/{id}")
	public ApiResponseEntity updateZuulRoutes(@PathVariable Integer id, @RequestBody Zuul zuul) {
		return this.zuulService.updateZuulRoutes(id, zuul);
	}

	@GetMapping("/service/{id}")
	public Zuul getZuul(@PathVariable Integer id) {
		return this.zuulService.fetchZuulRotesById(id);
	}

	@DeleteMapping("/delete-service/{id}")
	public ApiResponseEntity deleteZuulRoute(@PathVariable Integer id) {
		return this.zuulService.deleteZuulRoute(id);
	}

	@GetMapping("/all-services")
	public List<Zuul> fetchAllRoutes() {
		return this.zuulService.getAllZuulRoutes();
	}

	@GetMapping("/reload")
	public ApiResponseEntity reloadZuulList() {
		return this.zuulService.reloadZuulList();

	}
}
