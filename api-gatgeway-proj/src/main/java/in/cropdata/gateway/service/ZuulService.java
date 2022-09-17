package in.cropdata.gateway.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import in.cropdata.gateway.dto.ServiceInstanceDTO;
import in.cropdata.gateway.framework.ApplicationSession;
import in.cropdata.gateway.model.Zuul;
import in.cropdata.gateway.repository.ZuulRepository;
import in.cropdata.gateway.response.ApiResponse;
import in.cropdata.gateway.response.ApiResponseEntity;

/**
 * @author Vivek Gajbhiye
 *
 */
@Service
public class ZuulService {

	@Autowired
	ZuulRepository zuulRepository;

	@Autowired
	ApiResponse apiResponse;

	@Autowired
	DiscoveryClient client;

	@SuppressWarnings("null")
	public ApiResponseEntity addRoute(Zuul zuul) {
		try {
			Optional<Zuul> foundServiceId = this.zuulRepository.findByServiceId(zuul.getServiceId());
			Zuul zuulSave = this.zuulRepository.save(zuul);
			if (zuulSave != null) {
				ApplicationSession.getCurrent().forceReloadZuulList();
				return this.apiResponse.apiResponse(true, zuulSave.getServiceId() + " Saved Successfully", "");
			} else {
				return this.apiResponse.apiResponse(true,
						zuulSave.getServiceId() + " Something wrong while creating path ", "");
			}
		} catch (Exception e) {
			return this.apiResponse.apiResponse(false, "exception occured", e.getMessage());
		}
	}

	public List<Zuul> getAllZuulRoutes() {
		return this.zuulRepository.findAll();
	}

	public ApiResponseEntity updateZuulRoutes(Integer id, Zuul zuul) {
		Optional<Zuul> foundZuul = this.zuulRepository.findById(id);
		if (foundZuul.isPresent()) {
			zuul.setId(id);
			this.zuulRepository.save(zuul);
			ApplicationSession.getCurrent().forceReloadZuulList();
			return this.apiResponse.apiResponse(true, "Update Successfully", "");
		} else {
			return this.apiResponse.apiResponse(true, "Something wrong while updating the service", "");
		}
	}

	public ApiResponseEntity deleteZuulRoute(Integer id) {
		try {
			this.zuulRepository.deleteById(id);
			return this.apiResponse.apiResponse(true, "service deleted successfully", "");
		} catch (Exception e) {
			return this.apiResponse.apiResponse(false, "excetion occured", e.getMessage());
		}

	}

	public Zuul fetchZuulRotesById(Integer id) {
		try {
			Optional<Zuul> foundById = this.zuulRepository.findById(id);
			if (foundById.isPresent()) {
				return foundById.get();
			} else {
				throw new RuntimeException("Data Not found");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public ApiResponseEntity reloadZuulList() {
		ApplicationSession.getCurrent().getZuulList();
		return this.apiResponse.apiResponse(true, "Zuul Load All Services", "");

	}

	public Map<String, List<ServiceInstanceDTO>> showAllServices() {

		List<ServiceInstance> collect = client.getServices().stream().flatMap(it -> client.getInstances(it).stream())
				.collect(Collectors.toList());
		Map<String, List<ServiceInstanceDTO>> map = new HashMap<>();
		ArrayList<ServiceInstanceDTO> arr = new ArrayList<>();
		for (ServiceInstance serviceInstance : collect) {
			ServiceInstanceDTO dto = new ServiceInstanceDTO();
			dto.setHost(serviceInstance.getHost());
			dto.setServiceId(serviceInstance.getServiceId());
			dto.setPort(serviceInstance.getPort());
			dto.setInstanceId(serviceInstance.getInstanceId());
			dto.setMetadata(serviceInstance.getMetadata());
			dto.setUri(serviceInstance.getUri().toString());
			String serviceUrl = "http://" + serviceInstance.getServiceId() + ":" + serviceInstance.getPort();
			dto.setServiceUrl(serviceUrl);
			arr.add(dto);

		}
		List<ServiceInstanceDTO> api = arr.stream().filter(r -> r.getServiceId().contains("api"))
				.collect(Collectors.toList());
		List<ServiceInstanceDTO> ui = arr.stream().filter(r -> r.getServiceId().contains("ui"))
				.collect(Collectors.toList());
		List<ServiceInstanceDTO> py = arr.stream().filter(r -> r.getServiceId().contains("py"))
				.collect(Collectors.toList());
		List<ServiceInstanceDTO> others = arr.stream().filter(r -> !r.getServiceId().contains("api")
				&& !r.getServiceId().contains("py") && !r.getServiceId().contains("ui")).collect(Collectors.toList());
		map.put("java", api);
		map.put("ui", ui);
		map.put("py", py);
		map.put("java", others);
		return map;
	}

}
