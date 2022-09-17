package in.cropdata.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.service.AgriCommodityService;
import in.cropdata.portal.vo.AgriCommodityVo;
@RestController
@RequestMapping("/site/commodity")
public class AgriCommodityController {
	
	@Autowired
	private AgriCommodityService agriCommodityService;

	@GetMapping("/active-commodity")
	public List<AgriCommodityVo> getActiveCommodityList() {
		return agriCommodityService.getActiveCommodityList();
	}

	@GetMapping("/commodity")
	public List<AgriCommodityVo> getCommodityList() {
		return agriCommodityService.getCommodityList();
	}
	
}
