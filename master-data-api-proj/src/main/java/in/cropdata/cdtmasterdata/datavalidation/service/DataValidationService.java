package in.cropdata.cdtmasterdata.datavalidation.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.datavalidation.dao.DataValidtionDao;

@Service
public class DataValidationService {
	
	@Autowired
	private DataValidtionDao dataValidtionDao;
	
	public Map<String, Object> getDataValidation(){
		return dataValidtionDao.getDataValidation();
	}//getDataValidation

}//DataValidationService
