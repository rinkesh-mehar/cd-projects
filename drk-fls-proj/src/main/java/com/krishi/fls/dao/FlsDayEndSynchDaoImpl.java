package com.krishi.fls.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.krishi.fls.model.FlsDataSynch;
import com.krishi.fls.repository.BankDetailsRepository;
import com.krishi.fls.repository.CaseCoordinateRepository;
import com.krishi.fls.repository.CaseCropRepository;
import com.krishi.fls.repository.CaseDetailRepository;
import com.krishi.fls.repository.CaseKmlRepository;
import com.krishi.fls.repository.FarmCaseRepository;
import com.krishi.fls.repository.FarmerCroppingPattrenRepository;
import com.krishi.fls.repository.FarmerFarmRepository;
import com.krishi.fls.repository.FarmerGeneralInfoRepository;
import com.krishi.fls.repository.FarmerInsuranceRepository;
import com.krishi.fls.repository.FarmerKycRepository;
import com.krishi.fls.repository.FarmerLandInfoRepository;
import com.krishi.fls.repository.FarmerLoanInfoRepository;
import com.krishi.fls.repository.FarmerMachineryInfoRepository;
import com.krishi.fls.repository.FarmerPolyHouseRepository;
import com.krishi.fls.repository.FarmerRepository;
import com.krishi.fls.repository.RightsRepository;
import com.krishi.fls.repository.TaskAerialPhotosRepository;
import com.krishi.fls.repository.TaskAllocationRepositry;
import com.krishi.fls.repository.TaskHealthDetailsRepository;
import com.krishi.fls.repository.TaskHealthPhotoRepository;
import com.krishi.fls.repository.TaskSpotRepository;
import com.krishi.fls.repository.TaskStressDetailsRepository;
import com.krishi.fls.repository.TaskTransactionRepository;
import com.krishi.fls.repository.TaskhistoryRepository;
import com.krishi.fls.repository.VillageAdditionalInfoRepository;

@Repository
@Component
public class FlsDayEndSynchDaoImpl implements FlsDayEndSynchDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FarmerFarmRepository farmRepo;

	@Autowired
	private FarmCaseRepository farmCaseRepo;

	@Autowired
	private FarmerRepository farmerRepo;

	@Autowired
	private FarmerCroppingPattrenRepository farmerCroppingPattrenRepo;

	@Autowired
	private FarmerGeneralInfoRepository farmerGeneralInfoRepo;

	@Autowired
	private FarmerInsuranceRepository farmerInnsuranceRepo;

	@Autowired
	private FarmerKycRepository farmerKycRepo;

	@Autowired
	private FarmerLandInfoRepository farmerLandRepository;

	@Autowired
	private FarmerLoanInfoRepository farmerLoanRepo;

	@Autowired
	private FarmerPolyHouseRepository farmerPolyhouseRepo;

	@Autowired
	private FarmerMachineryInfoRepository farmerMachineRepo;

	@Autowired
	private TaskAllocationRepositry taskRepo;

	@Autowired
	private CaseCropRepository casecropRepo;

	@Autowired
	private CaseDetailRepository caseDetailRepo;

	@Autowired
	private TaskhistoryRepository taskhistoryRepo;
	
	@Autowired
	private CaseCoordinateRepository caseCoordinateRepo;
	
	@Autowired
	private TaskStressDetailsRepository taskStressDetailsRepo;
	
	@Autowired
	private TaskHealthDetailsRepository tashHealthDetailsRepo;
	
	@Autowired
	private TaskSpotRepository taskSpotRepo;
	
	@Autowired
	private TaskHealthPhotoRepository taskhealthPhotoRepo;
	
	@Autowired
	private RightsRepository rightsRepo;

	@Autowired
	private BankDetailsRepository bankDetailsRepo;
	
	@Autowired
	private CaseKmlRepository caseKmlRepo;
	
	@Autowired
	private VillageAdditionalInfoRepository villageAdditionalInfoRepo;
	
	@Autowired
	private TaskTransactionRepository taskTransactionRepo;
	
	@Autowired
	private TaskAerialPhotosRepository TaskAerialPhotosRepo;
	
	
	
	@Override
	public void flsSynch(FlsDataSynch dataSynch) {

		farmRepo.saveAll(dataSynch.getFarmerFarm());
		dataSynch.getFarmCase().forEach(i -> {
			if (i.getSyncReq()) {
				farmCaseRepo.save(i);
			}
		}

		);

		farmerRepo.saveAll(dataSynch.getFarmer());
		farmerCroppingPattrenRepo.saveAll(dataSynch.getFarmerCroping());
		farmerGeneralInfoRepo.saveAll(dataSynch.getFarmerGenInfo());
		farmerInnsuranceRepo.saveAll(dataSynch.getFarmerinsuranceInfo());
		farmerKycRepo.saveAll(dataSynch.getFarmerKyc());
		farmerLandRepository.saveAll(dataSynch.getFarmerLandInfo());
		farmerLoanRepo.saveAll(dataSynch.getFarmerLoanInfo());
		farmerPolyhouseRepo.saveAll(dataSynch.getFarmerPolyhouse());
		farmerMachineRepo.saveAll(dataSynch.getFarmerMachinery());
		if (dataSynch.getTask() != null) {
			taskRepo.saveAll(dataSynch.getTask());
		}
		casecropRepo.saveAll(dataSynch.getCaseCropInfo());
		if (dataSynch.getCasefieldDetails() != null) {
			caseDetailRepo.saveAll(dataSynch.getCasefieldDetails());
		}
		if (dataSynch.getTaskHistory() != null) {
			taskhistoryRepo.saveAll(dataSynch.getTaskHistory());
		}
		if (dataSynch.getCaseCoordinates() != null) {
			caseCoordinateRepo.saveAll(dataSynch.getCaseCoordinates());
		}
		
		if (dataSynch.getStressTaskDetails() != null) {
			taskStressDetailsRepo.saveAll(dataSynch.getStressTaskDetails());
		}
		
		if (dataSynch.getHealthTaskDetails() != null) {
			tashHealthDetailsRepo.saveAll(dataSynch.getHealthTaskDetails());
		}
		
		if (dataSynch.getTaskSpot() != null) {
			taskSpotRepo.saveAll(dataSynch.getTaskSpot());
		}
		
		if (dataSynch.getHealthPhotos() != null) {
			taskhealthPhotoRepo.saveAll(dataSynch.getHealthPhotos());
		}
		
		if (dataSynch.getRights() != null) {
			rightsRepo.saveAll(dataSynch.getRights());
		}
		
		if(dataSynch.getBankDetails()!=null)
		{
			bankDetailsRepo.saveAll(dataSynch.getBankDetails());
		}
		if(dataSynch.getCaseKml()!=null)
		{
			caseKmlRepo.saveAll(dataSynch.getCaseKml());
		}
		
		if(dataSynch.getVillageAdditionalInfo()!=null)
		{
			villageAdditionalInfoRepo.saveAll(dataSynch.getVillageAdditionalInfo());
		}
		if(dataSynch.getTaskTransaction()!=null)
		{
			taskTransactionRepo.saveAll(dataSynch.getTaskTransaction());
		}
		if(dataSynch.getTaskAerialPhotos() != null) {
			TaskAerialPhotosRepo.saveAll(dataSynch.getTaskAerialPhotos());
		}
	}

}
