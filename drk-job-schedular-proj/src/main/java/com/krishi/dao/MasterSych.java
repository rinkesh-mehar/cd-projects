package com.krishi.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.AgrochemicalBrand;
import com.krishi.entity.AlliedActivity;
import com.krishi.entity.Argochemical;
import com.krishi.entity.ArgochemicalCompany;
import com.krishi.entity.ArgochemicalType;
import com.krishi.entity.Bank;
import com.krishi.entity.BankBranch;
import com.krishi.entity.CallingStatus;
import com.krishi.entity.VillageToVillageDistance;
import com.krishi.entity.Commodity;
import com.krishi.entity.CropdataCalendar;
import com.krishi.entity.District;
import com.krishi.entity.EducationType;
import com.krishi.entity.EnrollmentPlace;
import com.krishi.entity.Fertilizer;
import com.krishi.entity.FertilizerApplicationMethod;
import com.krishi.entity.GeneralTerrainType;
import com.krishi.entity.GovtOfficialDept;
import com.krishi.entity.GovtOfficialDesignation;
import com.krishi.entity.HealthQuestionnaire;
import com.krishi.entity.HsCode;
import com.krishi.entity.InsuranceCompany;
import com.krishi.entity.IrrigationMethod;
import com.krishi.entity.IrrigationSource;
import com.krishi.entity.KycDocType;
import com.krishi.entity.Language;
import com.krishi.entity.LhWarehouseModel;
import com.krishi.entity.LoanPurpose;
import com.krishi.entity.LoanSource;
import com.krishi.entity.Machinery;
import com.krishi.entity.MbepEntity;
import com.krishi.entity.MobileType;
import com.krishi.entity.MonthlyWeatherBasedTravelTime;
import com.krishi.entity.Panchayat;
import com.krishi.entity.Pesticides;
import com.krishi.entity.Phenophase;
import com.krishi.entity.PhenophaseDuration;
import com.krishi.entity.PlantPart;
import com.krishi.entity.PoiType;
import com.krishi.entity.ProxyRelationType;
import com.krishi.entity.Region;
import com.krishi.entity.RegionalConnectivityTime;
//import com.krishi.entity.RegionalVariety;
import com.krishi.entity.ResidueDisposeType;
//import com.krishi.entity.Season;
import com.krishi.entity.SeedSource;
import com.krishi.entity.SeedTreatmentAgent;
import com.krishi.entity.StandardQuantityChart;
import com.krishi.entity.StateEntity;
import com.krishi.entity.StateLanguage;
//import com.krishi.entity.StateSeason;
//import com.krishi.entity.StateSeasonCommodity;
import com.krishi.entity.Stress;
import com.krishi.entity.StressControlMeasure;
import com.krishi.entity.StressRecommendation;
import com.krishi.entity.StressSeverity;
import com.krishi.entity.StressSeverityControlMeasures;
import com.krishi.entity.StressSymptoms;
import com.krishi.entity.StressType;
import com.krishi.entity.TaskTypeSpecifications;
import com.krishi.entity.Tehsil;
import com.krishi.entity.TileZL11;
import com.krishi.entity.UnitOfMeasurement;
import com.krishi.entity.Variety;
import com.krishi.entity.VarietyQuality;
import com.krishi.entity.Village;
import com.krishi.entity.VillageToRlDistance;
import com.krishi.entity.VipDesignation;
import com.krishi.entity.VipStatus;
import com.krishi.entity.WeatherBasedTravelTime;
import com.krishi.repository.AgrochemicalBrandRepository;
import com.krishi.repository.AlliedActivityRepository;
import com.krishi.repository.ArgochemicalCompanyRepository;
import com.krishi.repository.ArgochemicalRepository;
import com.krishi.repository.ArgochemicalTypeRepository;
import com.krishi.repository.BankBranchRepository;
import com.krishi.repository.BankRepository;
import com.krishi.repository.CallingStatusRepository;
import com.krishi.repository.VillageToVillageRepository;
import com.krishi.repository.CommodityRepository;
import com.krishi.repository.CropdataCalendarRepository;
import com.krishi.repository.DistrictRepository;
import com.krishi.repository.EducationTypeRepository;
import com.krishi.repository.EnrollmentPlaceRepository;
import com.krishi.repository.FertilizerApplicationMethodRepository;
import com.krishi.repository.FertilizerRepository;
import com.krishi.repository.GeneralTerrainTypeRepository;
import com.krishi.repository.GovtOfficialDeptRepository;
import com.krishi.repository.GovtOfficialDesignationrepository;
import com.krishi.repository.HealthQuestionnaireRepository;
import com.krishi.repository.HsCodeRepository;
import com.krishi.repository.InsuranceCompanyRepository;
import com.krishi.repository.IrrigationMethodRepository;
import com.krishi.repository.IrrigationSourceRepository;
import com.krishi.repository.KycDocTypeRepository;
import com.krishi.repository.LanguageRepository;
import com.krishi.repository.LhWarehouseRepository;
import com.krishi.repository.LoanPurposeRepository;
import com.krishi.repository.LoanSourceRepository;
import com.krishi.repository.MachineryRepository;
import com.krishi.repository.MbepRepository;
import com.krishi.repository.MobileTypeRepository;
import com.krishi.repository.MonthlyWeatherBasedTravelTimeRepository;
import com.krishi.repository.PanchayatRepository;
import com.krishi.repository.PesticidesRepository;
import com.krishi.repository.PhenophaseDurationRepository;
import com.krishi.repository.PhenophaseRepository;
import com.krishi.repository.PlantPartRepository;
import com.krishi.repository.PoiTypeRepository;
import com.krishi.repository.ProxyRelationTypeRepository;
import com.krishi.repository.RegionRepository;
import com.krishi.repository.RegionalConnectivityTimeRepository;
//import com.krishi.repository.RegionalVarietyRepository;
import com.krishi.repository.ResidueDisposeTypeRepository;
//import com.krishi.repository.SeasonRepository;
import com.krishi.repository.SeedSourceRepository;
import com.krishi.repository.SeedTreatmentAgentRepository;
import com.krishi.repository.StandardQuantityRepository;
import com.krishi.repository.StateLanguageRepository;
import com.krishi.repository.StateRepository;
//import com.krishi.repository.StateSeasonCommodityRepository;
//import com.krishi.repository.StateSeasonRepository;
import com.krishi.repository.StressControlMeasureRepository;
import com.krishi.repository.StressRecommendationRepository;
import com.krishi.repository.StressRepository;
import com.krishi.repository.StressSeverityControlMeasuresRepository;
import com.krishi.repository.StressSeverityRepository;
import com.krishi.repository.StressSymptomsRepository;
import com.krishi.repository.StressTypeRepository;
import com.krishi.repository.TaskTypeSpecificationRepository;
import com.krishi.repository.TehsilRepository;
import com.krishi.repository.TileZL11Repository;
import com.krishi.repository.UnitOfMeasurementRepo;
import com.krishi.repository.VarietyQualityRepository;
import com.krishi.repository.VarietyRepository;
import com.krishi.repository.VillageRepository;
import com.krishi.repository.VillageToRlDistanceRepository;
import com.krishi.repository.VipDesignationRepository;
import com.krishi.repository.VipStatusRepository;
import com.krishi.repository.WeatherBasedTravelTimeRepository;

@Component
@Repository
public class MasterSych {
	
	
	private static final Logger LOGGER = LogManager.getLogger(MasterSych.class);


	@PersistenceContext
	EntityManager em;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private CommodityRepository commodityRepo;

	@Autowired
	private EducationTypeRepository educationTypeRepo;

	@Autowired
	private FertilizerApplicationMethodRepository fertilizerApplicationMethodRepo;

	@Autowired
	private GovtOfficialDeptRepository govtOfficialDeptRepo;

	@Autowired
	private IrrigationMethodRepository irrigationMethodRepo;

	@Autowired
	private IrrigationSourceRepository irrigationSourceRepo;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private PoiTypeRepository poiTypeRepository;

	@Autowired
	private ResidueDisposeTypeRepository residueDisposeType;

//	@Autowired
//	private SeasonRepository seasonRepo;

	@Autowired
	private UnitOfMeasurementRepo unitOfMeasurementRepo;

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepo;

	@Autowired
	private SeedSourceRepository seedSourceRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private MachineryRepository machineryRepository;

	@Autowired
	private LoanSourceRepository loanSourceRepo;

	@Autowired
	private LoanPurposeRepository loanPurposeRepo;

	@Autowired
	private KycDocTypeRepository kycDocTypeRepo;

	@Autowired
	private GovtOfficialDesignationrepository govtOfficialDesignationrepo;

	@Autowired
	private PesticidesRepository pesticidesRepository;

	@Autowired
	private StateLanguageRepository stateLanguageRepository;

//	@Autowired
//	private StateSeasonRepository stateSeasonRepository;

	@Autowired
	private TehsilRepository tehsilRepository;

	@Autowired
	private StressRepository stressRepository;

	@Autowired
	private StressTypeRepository stressTypeRepository;

	@Autowired
	private ArgochemicalRepository argochemicalRepository;

	@Autowired
	private ArgochemicalCompanyRepository argochemicalCompanyRepository;

	@Autowired
	private PhenophaseRepository phenophaseRepository;

	@Autowired
	private ArgochemicalTypeRepository argochemicalTypeRepository;

	@Autowired
	private VillageRepository villageRepository;

	@Autowired
	private PanchayatRepository panchayatRepository;

	@Autowired
	private AgrochemicalBrandRepository agrochemicalBrandRepository;

	@Autowired
	private VarietyRepository varietyRepository;

	@Autowired
	private EnrollmentPlaceRepository enrollmentPlaceRepository;

	@Autowired
	private FertilizerRepository fertilizerRepository;

	@Autowired
	private TileZL11Repository tileZL11Repository;

	@Autowired
	private MobileTypeRepository mobileTypeRepository;

	@Autowired
	private PhenophaseDurationRepository phenophaseDurationRepository;

	@Autowired
	private PlantPartRepository plantPartRepository;

	@Autowired
	private ProxyRelationTypeRepository proxyRelationTypeRepository;

	@Autowired
	private SeedTreatmentAgentRepository seedtreatmentAgentRepository;

//	@Autowired
//	private StateSeasonCommodityRepository stateSeasonCommodityRepository;

	@Autowired
	private StressSymptomsRepository stressSymptomsRepository;

	@Autowired
	private VipStatusRepository vipStatusRepository;

	@Autowired
	private StressControlMeasureRepository stressControlMeasureRepository;

	@Autowired
	private StressSeverityControlMeasuresRepository stressSeverityControlMeasuresRepository;

	@Autowired
	private StressSeverityRepository stressSeverityRepository;

	@Autowired
	private HealthQuestionnaireRepository healthQuestionnaireRepository;

	@Autowired
	private VipDesignationRepository vipDesignationRepository;

	@Autowired
	private StressRecommendationRepository stressRecommendationRepository;

	@Autowired
	private MbepRepository mbepRepository;

	@Autowired
	private VarietyQualityRepository varietyQualityRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchState(List<StateEntity> entity) {
		entity.forEach(stateRepo::saveAndFlush);
		LOGGER.info("INFO: state api saved successfully");		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchDistrict(List<District> entity) {
		entity.forEach(districtRepository::saveAndFlush);
		LOGGER.info("INFO: district api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchCommodity(List<Commodity> entity) {
		entity.forEach(commodityRepo::saveAndFlush);
		LOGGER.info("INFO: commodity api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchEducationType(List<EducationType> educationTypeEntity) {
		educationTypeEntity.forEach(educationTypeRepo::saveAndFlush);
		LOGGER.info("INFO: education Type api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchFertilizerApplicationMethod(List<FertilizerApplicationMethod> fertilizerApplicationMethodEntity) {
		fertilizerApplicationMethodEntity.forEach(fertilizerApplicationMethodRepo::saveAndFlush);
		LOGGER.info("INFO: fertilizer Application Method api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchGovtOfficialDep(List<GovtOfficialDept> govtOfficialDeptEntity) {
		govtOfficialDeptEntity.forEach(govtOfficialDeptRepo::saveAndFlush);
		LOGGER.info("INFO: govt Official Dept api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchInsuranceCompany(List<InsuranceCompany> entity) {
		entity.forEach(insuranceCompanyRepo::saveAndFlush);
		LOGGER.info("INFO: insurance Company api saved successfully");		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchirrigationMethod(List<IrrigationMethod> irrigationMethodEntity) {
		irrigationMethodEntity.forEach(irrigationMethodRepo::saveAndFlush);
		LOGGER.info("INFO: irrigation Method api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchIrrigationSource(List<IrrigationSource> irrigationSourceEntity) {
		irrigationSourceEntity.forEach(irrigationSourceRepo::saveAndFlush);
		LOGGER.info("INFO: irrigation Source api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchLanguage(List<Language> languageEntity) {
		languageEntity.forEach(languageRepository::saveAndFlush);
		LOGGER.info("INFO: language api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPoiType(List<PoiType> poiTypeEntity) {
		poiTypeEntity.forEach(poiTypeRepository::saveAndFlush);
		LOGGER.info("INFO: poi Type api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchUnitOfMeasurement(List<UnitOfMeasurement> unitOfMeasurement) {
		unitOfMeasurement.forEach(unitOfMeasurementRepo::saveAndFlush);
		LOGGER.info("INFO: unit Of Measurement api saved successfully");		

	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void synchSeason(List<Season> entity) {
//		entity.forEach(seasonRepo::saveAndFlush);
//		LOGGER.info("INFO: season api saved successfully");		
//
//	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchSeedSource(List<SeedSource> entity) {
		entity.forEach(seedSourceRepository::saveAndFlush);
		LOGGER.info("INFO: seed Source api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchResidueDisposeType(List<ResidueDisposeType> entity) {
		entity.forEach(residueDisposeType::saveAndFlush);
		LOGGER.info("INFO: residue Dispose Type api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchRegion(List<Region> entity) {
		entity.forEach(regionRepository::saveAndFlush);
		LOGGER.info("INFO: region api saved successfully");		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchMachinery(List<Machinery> entity) {
		entity.forEach(machineryRepository::saveAndFlush);
		LOGGER.info("INFO: machinery api saved successfully");		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchLoanSource(List<LoanSource> entity) {
		entity.forEach(loanSourceRepo::saveAndFlush);
		LOGGER.info("INFO: loan Source api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchLoanPurpose(List<LoanPurpose> entity) {
		entity.forEach(loanPurposeRepo::saveAndFlush);
		LOGGER.info("INFO: loan Purpose api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchKycDocType(List<KycDocType> entity) {
		entity.forEach(kycDocTypeRepo::saveAndFlush);
		LOGGER.info("INFO: kyc Doc Type api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchGovtOfficialDesi(List<GovtOfficialDesignation> entity) {
		entity.forEach(govtOfficialDesignationrepo::saveAndFlush);
		LOGGER.info("INFO: govt Official Designation api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPesticides(List<Pesticides> entity) {
		entity.forEach(pesticidesRepository::saveAndFlush);
		LOGGER.info("INFO: pesticides Repository api saved successfully");		

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStateLanguage(List<StateLanguage> entity) {
		entity.forEach(stateLanguageRepository::saveAndFlush);
		LOGGER.info("INFO: state Language api saved successfully");		

	}

	/** after acz introduce - CDT-Ujwal*/
//	
//	  @Transactional(propagation = Propagation.REQUIRES_NEW) public void
//	  synchStateSeason(List<StateSeason> entity) {
//	  entity.forEach(stateSeasonRepository::saveAndFlush);
//	  LOGGER.info("INFO: state Season api saved successfully"); }
	 

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchTehsil(List<Tehsil> entity) {
		entity.forEach(tehsilRepository::saveAndFlush);
		LOGGER.info("INFO: tehsil api saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStress(List<Stress> entity) {
		entity.forEach(stressRepository::saveAndFlush);
		LOGGER.info("INFO: stress api saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressType(List<StressType> entity) {
		entity.forEach(stressTypeRepository::saveAndFlush);
		LOGGER.info("INFO: stress Type saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchArgochemicalCompany(List<ArgochemicalCompany> entity) {
		entity.forEach(argochemicalCompanyRepository::saveAndFlush);
		LOGGER.info("INFO: argo chemical Company saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchArgochemical(List<Argochemical> entity) {
		entity.forEach(argochemicalRepository::saveAndFlush);
		LOGGER.info("INFO: argo chemical saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPhenophase(List<Phenophase> entity) {
		entity.forEach(phenophaseRepository::saveAndFlush);
		LOGGER.info("INFO: phenophase saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchArgochemicalType(List<ArgochemicalType> entity) {
		entity.forEach(argochemicalTypeRepository::saveAndFlush);
		LOGGER.info("INFO: argochemical Type saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVillage(List<Village> entity) {
		entity.forEach(villageRepository::saveAndFlush);
		LOGGER.info("INFO: village  saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPanchayat(List<Panchayat> panchayatList) {
		panchayatList.forEach(panchayatRepository::saveAndFlush);
		LOGGER.info("INFO: panchayat saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchAgrochemicalBrand(List<AgrochemicalBrand> agrochemicalBrandList) {
		agrochemicalBrandList.forEach(agrochemicalBrandRepository::saveAndFlush);
		LOGGER.info("INFO: agro chemical Brand saved successfully");

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVariety(List<Variety> varietyList) {
		varietyList.forEach(varietyRepository::saveAndFlush);
		LOGGER.info("INFO: variety Brand saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchEnrollmentPlace(List<EnrollmentPlace> enrollmentPlace) {
		enrollmentPlace.forEach(enrollmentPlaceRepository::saveAndFlush);
		LOGGER.info("INFO: enrollment Place saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchFertilizer(List<Fertilizer> fertilizerList) {
		fertilizerList.forEach(fertilizerRepository::saveAndFlush);
		LOGGER.info("INFO: fertilizer saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchTileZL11(List<TileZL11> tileZL11List) {
		tileZL11List.forEach(tileZL11Repository::saveAndFlush);
		LOGGER.info("INFO: Tile ZL 11 saved successfully");
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchMobileType(List<MobileType> mobileTypeList) {
		mobileTypeList.forEach(mobileTypeRepository::saveAndFlush);
		LOGGER.info("INFO: Mobile Type saved successfully");

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPhenophaseDuration(List<PhenophaseDuration> phenophaseDurationList) {
		phenophaseDurationList.forEach(phenophaseDurationRepository::saveAndFlush);
		LOGGER.info("INFO: Phenophase Duration saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchPlantPart(List<PlantPart> plantPartList) {
		plantPartList.forEach(plantPartRepository::saveAndFlush);
		LOGGER.info("INFO: Plant Part saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchProxyRelationType(List<ProxyRelationType> proxyRelationTypeList) {
		proxyRelationTypeList.forEach(proxyRelationTypeRepository::saveAndFlush);
		LOGGER.info("INFO: proxy Relation Type saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchSeedtreatmentAgent(List<SeedTreatmentAgent> seedtreatmentAgentList) {
		seedtreatmentAgentList.forEach(seedtreatmentAgentRepository::saveAndFlush);
		LOGGER.info("INFO: seed treatment Agent saved successfully");
	}

	/** after acz introduce - CDT-Ujwal*/
//	
//	  @Transactional(propagation = Propagation.REQUIRES_NEW) public void
//	  synchStateSeasonCommodity(List<StateSeasonCommodity>
//	  stateSeasonCommodityList) { List<StateSeason> list =
//	  stateSeasonRepository.findAll(); stateSeasonCommodityList.forEach(s -> {
//	  List<StateSeason> data = list.stream().filter(l ->(l.getStateId() ==
//	  s.getStateId() && l.getSeasonId() == s.getStateSeasonId()))
//	  .collect(Collectors.toList()); if(data.size() == 1) {
//	  s.setStateSeasonId(data.get(0).getId());
//	  stateSeasonCommodityRepository.saveAndFlush(s); } });
//	  LOGGER.info("INFO: state Season Commodity saved successfully"); }
	 

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressSymptoms(List<StressSymptoms> stressSymptomsList) {
		stressSymptomsList.forEach(stressSymptomsRepository::saveAndFlush);
		LOGGER.info("INFO: stress Symptoms saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVipStatus(List<VipStatus> vipStatusList) {
		vipStatusList.forEach(vipStatusRepository::saveAndFlush);
		LOGGER.info("INFO: vip Status saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressControlMeasure(List<StressControlMeasure> stressControlMeasure) {
		stressControlMeasure.forEach(stressControlMeasureRepository::saveAndFlush);
		LOGGER.info("INFO: stress Control Measure saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressSeverityControlMeasures(List<StressSeverityControlMeasures> stressSeverityControlMeasures) {
		stressSeverityControlMeasures.forEach(stressSeverityControlMeasuresRepository::saveAndFlush);
		LOGGER.info("INFO: stress Severity Control Measures saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressSeverity(List<StressSeverity> stressSeverity) {
		stressSeverity.forEach(stressSeverityRepository::saveAndFlush);
		LOGGER.info("INFO: Stress Severity saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVipDesignationRepo(List<VipDesignation> vipDesignation) {
		vipDesignation.forEach(vipDesignationRepository::saveAndFlush);
		LOGGER.info("INFO: vip Designation saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchStressRecommendationRepo(List<StressRecommendation> stressRecommendation) {
		stressRecommendation.forEach(stressRecommendationRepository::saveAndFlush);
		LOGGER.info("INFO: stress Recommendation saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchHealthQuestionnaire(List<HealthQuestionnaire> healthQuestionnaire) {
		healthQuestionnaire.forEach(healthQuestionnaireRepository::saveAndFlush);
		LOGGER.info("INFO: Health Questionnairen saved successfully");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchMbepRepo(List<MbepEntity> mbepEntity) {
		mbepEntity.forEach(mbepRepository::saveAndFlush);
		LOGGER.info("INFO: Mbep saved successfully");

	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVarietyQuality(List<VarietyQuality> varietyQuality) {
		varietyQuality.forEach(varietyQualityRepository::saveAndFlush);
		LOGGER.info("INFO: Variety Quality saved successfully");
	}

	
//	@Autowired
//	private RegionalVarietyRepository regionalVarietyRepository;
	
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void synRegionalVariety(List<RegionalVariety> regionalVariety) {
//		regionalVariety.forEach(regionalVarietyRepository::saveAndFlush);
//		LOGGER.info("INFO: Regional Variety saved successfully");
//	}

	/** added sync processes - Pranay : Start */
	@Autowired
	private CallingStatusRepository callingStatusRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncCallingStatus(List<CallingStatus> callingStatus) {
		callingStatus.forEach(callingStatusRepository::saveAndFlush);
		LOGGER.info("INFO: Calling Status saved successfully");
	}
	
	/** Bank */
	@Autowired
	private BankRepository bankRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncBankData(List<Bank> bank) {
		bank.forEach(bankRepository::saveAndFlush);
		LOGGER.info("INFO: Bank saved successfully");
	}
	
	/** Bank Branch */
	@Autowired
	private BankBranchRepository bankBranchRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncBankBranchData(List<BankBranch> bankBranch) {
		bankBranch.forEach(bankBranchRepository::saveAndFlush);
		LOGGER.info("INFO: Bank Branch saved successfully");
	}
	
	/** Allied Activity */
	@Autowired
	private AlliedActivityRepository alliedActivityRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncAlliedActivityData(List<AlliedActivity> alliedActivity) {
		alliedActivity.forEach(alliedActivityRepository::saveAndFlush);
		LOGGER.info("INFO: Allied Activity saved successfully");
	}
	/** added sync processes - Pranay : End */
	
	/** added sync processes - Ujwal : Start */
	/** Fetching village to village distance records */
	@Autowired
	private VillageToVillageRepository villageToVillageRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchCaseWiseVillage(List<VillageToVillageDistance> villageToVillageDistances) {
		villageToVillageDistances.forEach(villageToVillageRepository:: saveAndFlush);
		LOGGER.info("INFO: Village To Village Distance Records saved successfully");
	}
	
	/** Fetching Monthly Weather Base Travel Time records */
	@Autowired
	private MonthlyWeatherBasedTravelTimeRepository monthlyWeatherBasedTravelTimeRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchMonthlyWeatherBaseTravelTime(List<MonthlyWeatherBasedTravelTime> basedTravelTimeModels) {
		basedTravelTimeModels.forEach(monthlyWeatherBasedTravelTimeRepository:: saveAndFlush);
		LOGGER.info("INFO: Monthly Weather Base Travel Time records saved successfully");
	}
	
	/** Fetching General Terrain Type records */
	@Autowired
	private GeneralTerrainTypeRepository generalTerrainTypeRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchGeneralTerrainType(List<GeneralTerrainType> generalTerrainTypeModels) {
		generalTerrainTypeModels.forEach(generalTerrainTypeRepository:: saveAndFlush);
		LOGGER.info("INFO: General Terrain Type records saved successfully");
	}
	
	/** Fetching Regional Connectivity Time Records*/
	@Autowired
	private RegionalConnectivityTimeRepository connectivityTimeRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchRegionalConnectivityTime(List<RegionalConnectivityTime> connectivityTimeModels) {
		connectivityTimeModels.forEach(connectivityTimeRepository:: saveAndFlush);
		LOGGER.info("INFO: Regional Connectivity Time Records saved successfully");
	}
	
	/** Fetching Village To RL Distance Records */
	@Autowired
	private VillageToRlDistanceRepository distanceRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchVillageToRlDistance(List<VillageToRlDistance> distanceModels) {
		distanceModels.forEach(distanceRepository:: saveAndFlush);
		LOGGER.info("INFO: Village To RL Distance Records saved successfully");
	}
	
	/** Fetching Weather Based Travel Time Records*/
	@Autowired
	private WeatherBasedTravelTimeRepository weatherBasedTravelTimeRepository; 
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchWeatherBasedTravel(List<WeatherBasedTravelTime> basedTravelTimeModels) {
		basedTravelTimeModels.forEach(weatherBasedTravelTimeRepository:: saveAndFlush);
		LOGGER.info("INFO: Weather Based Travel Time records saved successfully");
	}
	
	/** Fetching Task Type Specifications Records*/
	@Autowired
	private TaskTypeSpecificationRepository taskTypeSpecificationRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void synchTaskTypeSpecification(List<TaskTypeSpecifications> taskTypeSpecificationsModels) {
		taskTypeSpecificationsModels.forEach(taskTypeSpecificationRepository:: saveAndFlush);
		LOGGER.info("INFO:Task Type Specifications records saved successfully");
	}
	
	/** Fetching Cropdata Calendar Records */
	@Autowired
	private CropdataCalendarRepository calendarRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncCropdataCalendar(List<CropdataCalendar> calendars) {
		calendars.forEach(calendarRepository::saveAndFlush);
		LOGGER.info("INFO: Cropdata Calendar records saved successfully");
	}
	/** added sync processes - Ujwal : End */
	
	/** added sync processes - Rinkesh : Start*/
	
	/** Fetching Agri HS Code Records */
	@Autowired
	private HsCodeRepository hsCodeRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncHsCode(List<HsCode> hsCode) {
		hsCode.forEach(hsCodeRepository::saveAndFlush);
		LOGGER.info("INFO: HS Code records saved successfully");
	}
	
	/** Fetching Standard Quantity Chart Records */
	@Autowired
	private StandardQuantityRepository standardQuantityRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStandardQuantity(List<StandardQuantityChart> standardQuantityChart) {
		standardQuantityChart.forEach(standardQuantityRepository::saveAndFlush);
		LOGGER.info("INFO: Standard QuantityRepository records saved successfully");
	}
	
	/** added sync processes - Rinkesh : End */
	
	@Autowired
	private LhWarehouseRepository lhWarehouseRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncLhWarehouse(List<LhWarehouseModel> lhWarehouseModels) {
		lhWarehouseModels.forEach(lhWarehouseRepository :: saveAndFlush);
		LOGGER.info("INFO: LH Warehouse records saved successfully");
	}
}
