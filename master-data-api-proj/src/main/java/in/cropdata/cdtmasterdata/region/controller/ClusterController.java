package in.cropdata.cdtmasterdata.region.controller;

import in.cropdata.cdtmasterdata.region.model.*;
import in.cropdata.cdtmasterdata.region.service.ClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
public class ClusterController {

	private static final Logger logger = LoggerFactory.getLogger(ClusterController.class);

	@Autowired
	private ClusterService service;

	@PostMapping("/get-details")
	public List<RegionOutputModel> getDetails(@RequestParam Integer regionId, @RequestBody String data) {
		logger.debug("_data -> {}", data);
		String regions = data.replaceAll("[\\[\\]]", "");
		return service.getDetails(regionId, regions);
	}

	@PostMapping("/generate-region")
	public ResponseModel generateRegion(@RequestParam Integer regionId, @RequestBody CreateRegion data) {
		System.out.println(data.get_subregions());
		return service.generateRegion(regionId, data);
	}

	@GetMapping("/get-list")
	public List<ClusterListModel> getList() {
		return service.getList();
	}

//	@PostMapping("/region-action")
//	public  boolean  regionAction(@RequestBody Map<String, String> _data) {			
//		return service.regionAction(_data);
//	}

	@PostMapping("/region-action")
	public ResponseModel regionAction(@ModelAttribute MmpkUploadWrapper fileWrapper) throws IOException {
//		fileWrapper.setFile((List<MultipartFile>) fileWrapper.getMyFile());
		logger.info("MyFile ---> {}", fileWrapper.getCsvFile());
		return service.regionAction(fileWrapper);
	}

	@GetMapping("/region-state")
	public List<StateModel> getState() {
		return service.getState();
	}

	@GetMapping("/region-district")
	public List<DistrictModel> gteDistrict(){
		return service.getDistrict();
	}

	@GetMapping("/region-image/{id}")
	public ResponseModel getRegionImage(@PathVariable int id) {
		return service.getRegionImage(id);
	}
}
