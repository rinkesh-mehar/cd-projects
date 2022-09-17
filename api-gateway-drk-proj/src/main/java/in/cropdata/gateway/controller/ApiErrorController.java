/**
 * 
 */
package in.cropdata.gateway.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import in.cropdata.gateway.dto.ServiceInstanceDTO;

/**
 * @author Vivek Gajbhiye
 *
 */
@Controller
public class ApiErrorController extends DefaultErrorAttributes implements ErrorController {

	private static final Logger LOG = LoggerFactory.getLogger(ApiErrorController.class);

	private static final String PATH = "/error";


	@Autowired
	DiscoveryClient client;

	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {


		List<ServiceInstance> collect = client.getServices().stream().flatMap(it -> client.getInstances(it).stream())
				.collect(Collectors.toList());
		Map<String, Object> map = new HashMap<>();
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
		List<ServiceInstanceDTO> others = arr.stream().filter(r -> !r.getServiceId().contains("api") && !r.getServiceId().contains("py")&& !r.getServiceId().contains("ui")).collect(Collectors.toList());
		model.addAttribute("java", api);
		model.addAttribute("ui", ui);
		model.addAttribute("python", py);
		model.addAttribute("others", others);
		LOG.info(" data {}", arr);

		return "index";
	}

	
	@RequestMapping(value = PATH)
	@ResponseBody
	public Map<String, Object> errorHandle(final WebRequest webRequest, final boolean includeStackTrace) {
		return getErrorAttributes(webRequest, includeStackTrace);

	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	@Override
	public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
		final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
		final Map<String, Object> jsonApiErrorAttributes = new LinkedHashMap<>();
		jsonApiErrorAttributes.put("status", errorAttributes.get("status"));
		jsonApiErrorAttributes.put("path", errorAttributes.get("path"));
		jsonApiErrorAttributes.put("title", errorAttributes.get("error"));
		jsonApiErrorAttributes.put("detail", errorAttributes.get("message"));
		return jsonApiErrorAttributes;
	}

}
