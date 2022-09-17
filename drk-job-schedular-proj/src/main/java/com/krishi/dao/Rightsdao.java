package com.krishi.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishi.batch.BatchApplication;
import com.krishi.entity.FarmCase;
import com.krishi.entity.Farmer;
import com.krishi.entity.FarmerBankAccount;
import com.krishi.entity.FarmerFarm;
import com.krishi.entity.FarmerKyc;
import com.krishi.entity.Rights;
import com.krishi.repository.FarmCaseRepository;
import com.krishi.repository.FarmerBankAccountRepository;
import com.krishi.repository.FarmerFarmRepository;
import com.krishi.repository.FarmerKycRepository;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.RightsRepository;

@Service
public class Rightsdao {

	private static final Logger LOGGER = LogManager.getLogger(Rightsdao.class);

	@Autowired
	private RightsRepository rightsRepository;

	@Autowired
	private FarmCaseRepository farmCaseRepository;

	@Autowired
	private FarmerFarmRepository farmerFarmRepo;

	@Autowired
	private FarmerRepository farmerRepo;

	@Autowired
	private FarmerBankAccountRepository farmerBankRepo;
	
	@Autowired 
	private FarmerKycRepository farmerkycRepo;

	public List<Rights> getSignedRights() {
		List<Rights> signedRights = rightsRepository
				.findAllsignedRights();

		return signedRights;
	}

	public List<FarmCase> getFarmerId() {
		List<Rights> signedRights = getSignedRights();
		List<String> caseId = signedRights.stream().map(id -> id.getCaseId()).collect(Collectors.toList());
		List<FarmCase> farmcase = farmCaseRepository.findAllById(caseId);
		LOGGER.info("INFO: Rigths getFarmerId ="+farmcase);		
		return farmcase;

	}

	public List<FarmerFarm> getFarmFarmerId() {
		List<FarmCase> farmID = getFarmerId();

		List<String> farmId = farmID.stream().map(id -> id.getFarmId()).collect(Collectors.toList());
		List<FarmerFarm> farmerFarm = farmerFarmRepo.findAllById(farmId);
		LOGGER.info("INFO: Rigths getFarmFarmerId ="+farmerFarm);
		return farmerFarm;
	}

	public List<Farmer> findFarmers() {
		List<FarmerFarm> farmersFarmID = getFarmFarmerId();
		List<String> farmerId = farmersFarmID.stream().map(id -> id.getFarmerId()).collect(Collectors.toList());
		List<Farmer> farmers = farmerRepo.findAllById(farmerId);
		LOGGER.info("INFO: Rigths findFarmers ="+farmers);

		return farmers;
	}


	public FarmerBankAccount findBankdetails(String findByFarmerId) {
		/** replace farmerId to caseId - As per discussion in 21/08/2021*/
//		FarmerBankAccount bankDetails = farmerBankRepo.findByFarmerId(findByFarmerId);
//		LOGGER.info("INFO: Rigths findBankdetails ="+bankDetails);

//		return bankDetails;
		return null;
	}
	
	public FarmerKyc findFarmerKyc(String farmerId)
	{
		FarmerKyc farmerKyc=farmerkycRepo.findByFarmerId(farmerId).get(0);
		LOGGER.info("INFO: Rigths findFarmerKyc ="+farmerKyc);
		return farmerKyc;
	}
	public Rights findRightsId(String FarmerId) {
		
		 FarmerFarm farmerFarm= farmerFarmRepo.findByFarmerId(FarmerId).get(0);
		  String id= farmerFarm.getId();
		  FarmCase farmcase=farmCaseRepository.findAllByFarmId(id).get(0);
		//  String farmId=farmcase.getFarmId();
		  //LOGGER.info("INFO: Rigths findRightsId ="+rightsRepository.findByCaseId(farmcase.getId()));
		  return rightsRepository.findTop1ByCaseIdOrderByVersionNumberDesc(farmcase.getId());
		
		}

		
}
