package in.cropdata.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.service.BuyerPreApplicationMastersService;
import in.cropdata.portal.vo.BuyerPreApplicationMastersVO;
import in.cropdata.portal.vo.CurrencyVo;

@RestController
@RequestMapping("/site/buyer-pre-application-masters")
public class BuyerPreApplicationMastersController {
	
	@Autowired
	private BuyerPreApplicationMastersService buyrsPreApplicationMastersService;

	@GetMapping("/applicant-type-list")
	public List<BuyerPreApplicationMastersVO> getApplicantTypeList() {
		return buyrsPreApplicationMastersService.getApplicantTypeList();
	}
	
	@GetMapping("/application-type-list")
	public List<BuyerPreApplicationMastersVO> getApplicationTypeList() {
		return buyrsPreApplicationMastersService.getApplicationTypeList();
	}
	
	@GetMapping("/bussiness-type-list")
	public List<BuyerPreApplicationMastersVO> getBusinessTypeList() {
		return buyrsPreApplicationMastersService.getBusinessTypeList();
	}
	
	@GetMapping("/firm-type-list")
	public List<BuyerPreApplicationMastersVO> getFirmTypeList() {
		return buyrsPreApplicationMastersService.getFirmTypeList();
	}
	
	@GetMapping("/commodity-list")
	public List<BuyerPreApplicationMastersVO> getCommodityList() {
		return buyrsPreApplicationMastersService.getCommodityList();
	}

	@GetMapping("/currency-list")
	public List<CurrencyVo> getCurrencyList() {
		return buyrsPreApplicationMastersService.getCurrencyList();
	}
	
	@GetMapping("/designation-list")
	public List<BuyerPreApplicationMastersVO> getDesignationList() {
		return buyrsPreApplicationMastersService.getDesignationList();
	}
	
	@GetMapping("/active-commodity-list")
	public List<BuyerPreApplicationMastersVO> getActiveCommodityList() {
		return buyrsPreApplicationMastersService.getActiveCommodityList();
	}
	
}
