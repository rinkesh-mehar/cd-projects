package in.cropdata.cdtmasterdata.website.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationMastersVO;
import in.cropdata.cdtmasterdata.website.model.vo.CurrencyVo;
import in.cropdata.cdtmasterdata.website.repository.BuyerPreApplicationMastersRepository;


@Service
public class BuyerPreApplicationMastersService {

private static final Logger LOGGER = LoggerFactory.getLogger(BuyerPreApplicationMastersService.class);
	
	@Autowired
	private BuyerPreApplicationMastersRepository buyrsPreApplicationMastersRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public List<BuyerPreApplicationMastersVO> getApplicantTypeList() {
		try {
			LOGGER.info("getting Applicant Type list info...");

			return buyrsPreApplicationMastersRepository.getApplicantTypeList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Applicant Type Data Found!");
		}
	}
	
	public List<BuyerPreApplicationMastersVO> getApplicationTypeList() {
		try {
			LOGGER.info("getting Applications Type list info...");

			return buyrsPreApplicationMastersRepository.getApplicationTypeList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Application Type Data Found!");
		}
	}
	
	public List<BuyerPreApplicationMastersVO> getBusinessTypeList() {
		try {
			LOGGER.info("getting Bussiness Type list info...");

			return buyrsPreApplicationMastersRepository.getBusinessTypeList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Bussiness Type Data Found!");
		}
	}
	
	public List<BuyerPreApplicationMastersVO> getFirmTypeList() {
		try {
			LOGGER.info("getting Firm Type list info...");

			return buyrsPreApplicationMastersRepository.getFirmTypeList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Firm Type Data Found!");
		}
	}
	
	public List<BuyerPreApplicationMastersVO> getCommodityList() {
		try {
			LOGGER.info("getting commodity list info...");

			return buyrsPreApplicationMastersRepository.getCommodityList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Commodity Data Found!");
		}
	}
	
	public List<CurrencyVo> getCurrencyList() {
		try {
			LOGGER.info("getting currency list info...");

			return buyrsPreApplicationMastersRepository.getCurrencyList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No currency Data Found!");
		}
	}
	
	public List<BuyerPreApplicationMastersVO> getDesignationList() {
		try {
			LOGGER.info("getting designation list info...");

			return buyrsPreApplicationMastersRepository.getDesignationList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Designation Data Found!");
		}
	}
}
