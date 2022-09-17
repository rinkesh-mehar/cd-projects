package com.krishi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.krishi.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.krishi.entity.RegionalVariety;
//import com.krishi.entity.Season;
//import com.krishi.entity.StateSeason;
//import com.krishi.entity.StateSeasonCommodity;
import com.krishi.model.AgrochemicalBrandModel;
import com.krishi.model.ArgochemicalCompanyModel;
import com.krishi.model.ArgochemicalModel;
import com.krishi.model.ArgochemicalTypeModel;
import com.krishi.model.CommodityModel;
import com.krishi.model.DistrictModel;
import com.krishi.model.EducationTypeModel;
import com.krishi.model.EnrollmentPlaceModel;
import com.krishi.model.FertilizerApplicationMethodModel;
import com.krishi.model.FertilizerModel;
import com.krishi.model.GovtOfficialDeptModel;
import com.krishi.model.GovtOfficialDesignationModel;
import com.krishi.model.HealthQuestionnaireModel;
import com.krishi.model.InsuranceCompanyModel;
import com.krishi.model.IrrigationMethodModel;
import com.krishi.model.IrrigationSourceModel;
import com.krishi.model.KycDocTypeModel;
import com.krishi.model.LanguageModel;
import com.krishi.model.LoanPurposeModel;
import com.krishi.model.LoanSourceModel;
import com.krishi.model.MachineryModel;
import com.krishi.model.MasterDataSyncInfo;
import com.krishi.model.MasterDataTable;
import com.krishi.model.MbepModel;
import com.krishi.model.MobileTypeModel;
import com.krishi.model.PanchayatModel;
import com.krishi.model.PesticidesModel;
import com.krishi.model.PhenophaseDurationMasterDataModel;
import com.krishi.model.PhenophaseModel;
import com.krishi.model.PlanPartModel;
import com.krishi.model.PoiTypeModel;
import com.krishi.model.ProxyRelationTypeModel;
import com.krishi.model.RegionModel;
import com.krishi.model.RequestStateModel;
import com.krishi.model.ResidueDisposeTypeModel;
//import com.krishi.model.SeasonModel;
import com.krishi.model.SeedSourceModel;
import com.krishi.model.SeedTreatmentAgentModel;
import com.krishi.model.StateLanguageMasterModel;
//import com.krishi.model.StateSeasonCommodityModel;
//import com.krishi.model.StateSeasonModel;
import com.krishi.model.StressControlMeasureModel;
import com.krishi.model.StressModel;
import com.krishi.model.StressRecommendationModel;
import com.krishi.model.StressSeverityControlMeasuresModel;
import com.krishi.model.StressSeverityModel;
import com.krishi.model.StressSymptomsModel;
import com.krishi.model.StressTypeModel;
import com.krishi.model.TehsilModel;
import com.krishi.model.TileZL11Model;
import com.krishi.model.UnitOfMeasurementModel;
import com.krishi.model.VarietyModel;
import com.krishi.model.VarietyQualityModel;
import com.krishi.model.VillageModel;
import com.krishi.model.VipDesignationModel;
import com.krishi.model.VipStatusModel;
import com.krishi.repository.GstmSyncRepository;
//import com.krishi.repository.StateSeasonRepository;
import com.krishi.repository.StaticDataRepository;
import com.krishi.utility.FileUtility;

@Service
public class MasterdataSyncImpl {

	private static final Logger LOGGER = LogManager.getLogger(MasterdataSyncImpl.class);
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	@Autowired
	private StaticDataRepository staticDataRepository;

	@Autowired
	private GstmSyncRepository gstmSyncRepository;

//	@Autowired
//	private StateSeasonRepository stateSeasonRepo;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SystemEmailService systemEmailService;

	private String masterDataApi;

	private String masterDataKey;

	@Autowired
	private MasterdataSyncImpl masterdataSyncImpl;

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private FileUtility fileUtility;
	
	

	List<MasterDataSyncInfo> logs = new ArrayList<>();

	/*pull updated master data from gstm api and update in the database*/
	public void run() {
		Long startTime = System.currentTimeMillis();
		logs.clear();
		StaticData masterDataApiUrlData = staticDataRepository.findByKey("masterDataBasePath");
		StaticData apikeyData = staticDataRepository.findByKey("masterDataApikey");

		masterDataApi = masterDataApiUrlData.getValue();
		masterDataKey = apikeyData.getValue();

		if (masterDataApiUrlData == null || masterDataApiUrlData.getValue() == null
				|| masterDataApiUrlData.getValue().isBlank()) {
			LOGGER.error("ERROR :: Master data base url not found!");
			return;
		}
		if (apikeyData == null || apikeyData.getValue() == null || apikeyData.getValue().isBlank()) {
			LOGGER.error("ERROR :: Master data api key not found!");
			return;
		}

		StaticData tableList = staticDataRepository.findByKey("masterDataMyncTableList");

		if (tableList != null && !tableList.getValue().isBlank() && !tableList.getValue().contains("ALL")) {

			List<String> list = Arrays.asList(tableList.getValue().split(","));

			if (list.contains(MasterDataTable.STATE.name())) {
				try {
					masterdataSyncImpl.syncState();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.EDUCATION_TYPE.name())) {
				try {
					masterdataSyncImpl.syncEducation();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.DISTRICT.name())) {
				try {
					masterdataSyncImpl.syncDistrict();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.COMMODITY.name())) {
				try {
					masterdataSyncImpl.syncCommodity();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.FERTILIZER_APPLICATION_METHOD.name())) {
				try {
					masterdataSyncImpl.syncFertilizerApplicationMethod();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.GOVT_OFFICIAL_DEPT.name())) {
				try {
					masterdataSyncImpl.syncGovtOfficialDept();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.INSURANCE_COMPANY.name())) {
				try {
					masterdataSyncImpl.syncInsurenceCompancy();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.IRRIGATION_METHOD.name())) {
				try {
					masterdataSyncImpl.syncIrrigationMethod();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.IRRIGATION_SOURCE.name())) {
				try {
					masterdataSyncImpl.syncIrrigationSource();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.LANGUAGE.name())) {
				try {
					masterdataSyncImpl.syncLanguage();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.POI_TYPE.name())) {
				try {
					masterdataSyncImpl.syncPoiType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.REGION.name())) {
				try {
					masterdataSyncImpl.syncRegion();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.RESIDUE_DISPOSE_TYPE.name())) {
				try {
					masterdataSyncImpl.syncResidueDisposeType();
				} catch(Exception e) {}

			}

//			if (list.contains(MasterDataTable.SEASON.name())) {
//				try {
//					masterdataSyncImpl.syncSeason();
//				} catch(Exception e) {}
//				
//			}

			if (list.contains(MasterDataTable.SEED_SOURCE.name())) {
				try {
					masterdataSyncImpl.syncSeedSource();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.UNIT_OF_MEASUREMENT.name())) {
				try {
					masterdataSyncImpl.syncUnitOfMeasurement();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.MACHINERY.name())) {
				try {
					masterdataSyncImpl.syncMachinery();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.LOAN_SOURCE.name())) {
				try {
					masterdataSyncImpl.syncloanSource();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.LOAN_PURPOSE.name())) {
				try {
					masterdataSyncImpl.syncloanPurpose();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.KYC_DOC_TYPE.name())) {
				try {
					masterdataSyncImpl.syncKycDocType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.GOVT_OFFICIAL_DESIGNATION.name())) {
				try {
					masterdataSyncImpl.syncGovtOfficialDesignation();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.TEHSIL.name())) {
				try {
					masterdataSyncImpl.syncTehsil();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STATE_LANGUAGE.name())) {
				try {
					masterdataSyncImpl.syncStateLanguageMaster();
				} catch(Exception e) {}

			}

			/** after acz introduce - CDT-Ujwal*/
			/*
			 * if (list.contains(MasterDataTable.STATE_SEASON.name())) { try {
			 * masterdataSyncImpl.syncStateSeason(); } catch(Exception e) {}
			 * 
			 * }
			 */

			if (list.contains(MasterDataTable.STRESS_TYPE.name())) {
				try {
					masterdataSyncImpl.syncStressType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.PHENOPHASE.name())) {
				try {
					masterdataSyncImpl.syncPhenophase();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.AGROCHEMICAL.name())) {
				try {
					masterdataSyncImpl.syncAgrochemical();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VILLAGE.name())) {
				try {
					masterdataSyncImpl.syncVillage();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.AGROCHEMICAL_COMPANY.name())) {
				try {
					masterdataSyncImpl.syncArgochemicalCompany();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.AGROCHEMICAL_TYPE.name())) {
				try {
					masterdataSyncImpl.syncAgrochemicalType();
				} catch(Exception e) {}


			}

			if (list.contains(MasterDataTable.PANCHAYAT.name())) {
				try {
					masterdataSyncImpl.syncPanchayat();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.AGROCHEMICAL_BRAND.name())) {
				try {
					masterdataSyncImpl.syncAgrochemicalBrand();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.PESTICIDES.name())) {
				try {
					masterdataSyncImpl.syncPesticides();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.ENROLLMENT_PLACE.name())) {
				try {
					masterdataSyncImpl.syncEnrollmentPlace();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.FERTILIZER.name())) {
				try {
					masterdataSyncImpl.syncFertilizer();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.MOBILE_TYPE.name())) {
				try {
					masterdataSyncImpl.syncMobileType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.PHENOPHASE_DURATION.name())) {
				try {
					masterdataSyncImpl.syncPhenophaseDuration();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.PLANT_PART.name())) {
				try {
					masterdataSyncImpl.syncPlantPart();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.PROXY_RELATION_TYPE.name())) {
				try {
					masterdataSyncImpl.syncProxyRelationType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.SEED_TREATMENT_AGENT.name())) {
				try {
					masterdataSyncImpl.syncSeedTreatmentAgent();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STRESS.name())) {
				try {
					masterdataSyncImpl.syncStress();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STRESS_SYMPTOMS.name())) {
				try {
					masterdataSyncImpl.syncStressSymptoms();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VARIETY.name())) {
				try {
					masterdataSyncImpl.syncVariety();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VIP_STATUS.name())) {
				try {
					masterdataSyncImpl.syncVipStatus();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STRESS_CONTROL_MEASURES.name())) {
				try {
					masterdataSyncImpl.syncStressControlMeasure();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STRESS_SEVERITY.name())) {
				try {
					masterdataSyncImpl.syncStressSeverity();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VIP_DESIGNATION.name())) {
				try {
					masterdataSyncImpl.syncVipDesignation();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.STRESS_RECOMMENDATION.name())) {
				try {
					masterdataSyncImpl.syncStressControlRecommendation();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.HEALTH_QUESTIONNAIRE.name())) {
				try {
					masterdataSyncImpl.syncHealthQuestionnaire();
				} catch(Exception e) {}

			}

			/** after acz introduce -CDT-Ujwal*/
			/*
			 * if (list.contains(MasterDataTable.STATE_SEASON_COMMODITY.name())) { try {
			 * masterdataSyncImpl.syncStateSeasonCommodity(); } catch(Exception e) {}
			 * 
			 * }
			 */

			if (list.contains(MasterDataTable.STRESS_SEVERITY_CONTROL_MEASURES.name())) {
				try {
					masterdataSyncImpl.syncStressSeverityControlMeasures();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VARIETY_QUALITY.name())) {
				try {
					masterdataSyncImpl.syncVarietyQuality();
				} catch(Exception e) {}
				
			}
			
			/** Sync Tile_ZL_11 Start - CDT: Rinkesh*/
			if (list.contains(MasterDataTable.TILE_ZL11.name())) {
				try {
					masterdataSyncImpl.syncTileZL11();
				} catch(Exception e) {}
			}

			if (list.contains(MasterDataTable.REGIONAL_COMMODITY.name())) {
				try {
					masterdataSyncImpl.syncRegionalCommodity();
				} catch(Exception e) {}
			}
			/** Sync Tile_ZL_11 End - CDT: Rinkesh*/
			/*
			 * if (list.contains(MasterDataTable.REGIONAL_VARIETY.name())) { try {
			 * masterdataSyncImpl.syncRegionalVariety(); } catch(Exception e) {}
			 * 
			 * }
			 */

			if (list.contains(MasterDataTable.MBEP.name())) {
				try {
					masterdataSyncImpl.syncMbep();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.CALLING_STATUS.name())) {
				try {
					masterdataSyncImpl.syncCallingStatus();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.GENERAL_BANK.name())) {
				try {
					masterdataSyncImpl.syncGeneralBank();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.GENERAL_BANK_BRANCH.name())) {
				try {
					masterdataSyncImpl.syncGeneralBankBranch();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.ALLIED_ACTIVITY.name())) {
				try {
					masterdataSyncImpl.syncAlliedActivity();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VILLAGE_TO_VILLAGE_DISTANCE.name())) {
				try {
					masterdataSyncImpl.syncVillageToVillageDistance();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.REGIONAL_MONTHLY_WEATHER_BASED_TRAVEL_TIME.name())) {
				try {
					masterdataSyncImpl.syncRegionalMonthlyWeatherBaseTravelTime();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.GENERAL_TERRAIN_TYPE.name())) {
				try {
					masterdataSyncImpl.syncGeneralTerrainType();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.REGIONAL_CONNECTIVITY_TIME.name())) {
				try {
					masterdataSyncImpl.syncRegionalConnectivityTime();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.VILLAGE_TO_RL_DISTANCE.name())) {
				try {
					masterdataSyncImpl.syncVillageToRLDistance();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.WEATHER_BASED_TRAVEL_TIME.name())) {
				try {
					masterdataSyncImpl.syncWeatherBasedTravelTime();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.TASK_TYPE_SPECIFICATIONS.name())) {
				try {
					masterdataSyncImpl.syncTaskTypeSpecifications();
				} catch(Exception e) {}

			}

			if (list.contains(MasterDataTable.CROPDATA_CALENDER.name())) {
				try {
					masterdataSyncImpl.syncCropdataCalendar();
				} catch(Exception e) {}

			}
			if (list.contains(MasterDataTable.HS_CODE.name())) {
				try {
					masterdataSyncImpl.syncHsCode();
				} catch(Exception e) {}

			}
			if (list.contains(MasterDataTable.STANDARD_QUANTITY_CHART.name())) {
				try {
					masterdataSyncImpl.syncStandardQuantityChart();
				} catch(Exception e) {}

			}

			/** added new sync table in GSTM - CDT-Ujwal -Start*/
			if(list.contains(MasterDataTable.LH_WAREHOUSE.name())) {
				try {
					masterdataSyncImpl.syncLhWarehouse();
				} catch (Exception e) {}
			}
			if(list.contains(MasterDataTable.PACKAGING_TYPE.name())) {
				try {
					masterdataSyncImpl.syncPackagingType();
				}catch (Exception e) {}
			}
			if(list.contains(MasterDataTable.QUALITY_PARAMETERE.name())) {
				try {
					masterdataSyncImpl.syncQualityParameter();
				} catch (Exception e) {}
			}
			if(list.contains(MasterDataTable.QUALITY_COMMODITY_PARAMETERE.name())) {
				try {
					masterdataSyncImpl.syncQualityCommodityParameter();
				}catch (Exception e) {}
			}

		} else {
			try {
				masterdataSyncImpl.syncState();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncEducation();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncDistrict();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncCommodity();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncFertilizerApplicationMethod();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncGovtOfficialDept();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncIrrigationMethod();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncInsurenceCompancy();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncIrrigationSource();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncLanguage();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPoiType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncRegion();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncResidueDisposeType();
			} catch(Exception e) {}
//			try {
//				masterdataSyncImpl.syncSeason();
//			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncSeedSource();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncMachinery();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncloanSource();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncloanPurpose();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncKycDocType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncGovtOfficialDesignation();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPesticides();
			} catch(Exception e) {}

			/** after acz introduce - CDT-Ujwal */
//			
//			  try { masterdataSyncImpl.syncStateSeason(); } catch(Exception e) {}
//			 
			try {
				masterdataSyncImpl.syncTehsil();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStateLanguageMaster();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStressType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncAgrochemicalType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncArgochemicalCompany();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncAgrochemical();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncVillage();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPhenophase();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncUnitOfMeasurement();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPanchayat();
			} catch(Exception e) {}
//			try {
//				masterdataSyncImpl.syncStress();
//			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncAgrochemicalBrand();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncVariety();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncEnrollmentPlace();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncMobileType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPhenophaseDuration();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncPlantPart();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStressSymptoms();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncVipStatus();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncProxyRelationType();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncSeedTreatmentAgent();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncFertilizer();
			} catch(Exception e) {
				LOGGER.info("Exception in Fertilizer");
				e.printStackTrace();

			}
			/** after acz introduce -CDT-Ujwal */
//			
//			  try { masterdataSyncImpl.syncStateSeasonCommodity(); } catch(Exception e) {}
//			 
			try {
				masterdataSyncImpl.syncStressControlMeasure();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStressSeverityControlMeasures();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStressSeverity();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncVipDesignation();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStressControlRecommendation();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncHealthQuestionnaire();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncVarietyQuality();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncTileZL11();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncRegionalCommodity();
			} catch(Exception e) {}
			/*
			 * try { masterdataSyncImpl.syncRegionalVariety(); } catch(Exception e) {}
			 */
			try {
				masterdataSyncImpl.syncMbep();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncCallingStatus();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncGeneralBank();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncGeneralBankBranch();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncAlliedActivity();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncVillageToVillageDistance();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncRegionalMonthlyWeatherBaseTravelTime();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncGeneralTerrainType();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncRegionalConnectivityTime();
			} catch(Exception e) {}
			try {
			 	masterdataSyncImpl.syncWeatherBasedTravelTime();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncTaskTypeSpecifications();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncCropdataCalendar();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncHsCode();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncStandardQuantityChart();
			} catch(Exception e) {}
			try {
				masterdataSyncImpl.syncLhWarehouse();
			} catch (Exception e) {}
			try {
				masterdataSyncImpl.syncPackagingType();
			} catch (Exception e) {}
			try {
				masterdataSyncImpl.syncQualityParameter();
			} catch (Exception e) {}
			try {
				masterdataSyncImpl.syncQualityCommodityParameter();
			} catch (Exception e) {}

		}
		Long endTime = System.currentTimeMillis();
		systemEmailService.sendDataSyncStatusMail(logs, startTime, endTime);
		em.close();
	}

	public MasterdataSyncImpl() {
		super();
	}

	

	// state
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncState() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STATE.name());
		logs.add(syncInfo);

		Date startTime = new Date();

		LOGGER.info("INFO :: State API sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";

		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STATE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<RequestStateModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do { 
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/state")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<RequestStateModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<StateEntity> list = response.stream().map(a -> a.getEntity()).collect(Collectors.toList());
					int i = 0;
					for (StateEntity m : list) {
						++i;
						StateEntity existRecored = em.find(StateEntity.class, m.getStateId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}
						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STATE.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.saveAndFlush(gstmSyn);
			}
			Date endTime = new Date();
			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: State sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		
		} catch (Exception e) {
			
			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: State API error: " + e.getMessage());
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Education
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncEducation() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.EDUCATION_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Education API Sync started At: " + FORMAT.format(startTime));


		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.EDUCATION_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<EducationTypeModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/education-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<EducationTypeModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<EducationType> list = response.stream().map(a -> a.getEntity()).collect(Collectors.toList());
					int i = 0;
					for (EducationType m : list) {
						++i;
						EducationType existRecored = em.find(EducationType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.EDUCATION_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.saveAndFlush(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: EDUCATION_TYPE sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {
			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: EDUCATION TYPE API error");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// District

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncDistrict() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.DISTRICT.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: District Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.DISTRICT.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<DistrictModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/district")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<DistrictModel>>() {
				});

				pageRecordsCount = response.size();

				
				if (pageRecordsCount > 0) {
					List<District> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (District m : list) {
						++i;
						District existRecored = em.find(District.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.DISTRICT.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: District sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: District  API error:");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Commodity

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncCommodity() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.COMMODITY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Commodity API Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.COMMODITY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<CommodityModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/commodity")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<CommodityModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<Commodity> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (Commodity m : list) {
						++i;
						Commodity existRecored = em.find(Commodity.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.COMMODITY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Commodity sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Commodity API error");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Fertilizer Application Method

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncFertilizerApplicationMethod() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.FERTILIZER_APPLICATION_METHOD.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Fertilizer Application Method Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.FERTILIZER_APPLICATION_METHOD.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<FertilizerApplicationMethodModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/fertilizer-application-method")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<FertilizerApplicationMethodModel>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<FertilizerApplicationMethod> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (FertilizerApplicationMethod m : list) {
						++i;
						FertilizerApplicationMethod existRecored = em.find(FertilizerApplicationMethod.class,
								m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.FERTILIZER_APPLICATION_METHOD.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Fertilizer Application Method sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Fertilizer Application Method API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Govt Official Dept

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncGovtOfficialDept() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.GOVT_OFFICIAL_DEPT.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Govt Official Dept Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.GOVT_OFFICIAL_DEPT.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}
		try {
			List<GovtOfficialDeptModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/govt-official-dept")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<GovtOfficialDeptModel>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<GovtOfficialDept> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (GovtOfficialDept m : list) {
						++i;
						GovtOfficialDept existRecored = em.find(GovtOfficialDept.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.GOVT_OFFICIAL_DEPT.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: govt official dept sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: govt_official_dept API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Irrigation Method

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncIrrigationMethod() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.IRRIGATION_METHOD.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Irrigation Method Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.IRRIGATION_METHOD.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<IrrigationMethodModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/irrigation-method")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<IrrigationMethodModel>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<IrrigationMethod> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (IrrigationMethod m : list) {
						++i;
						IrrigationMethod existRecored = em.find(IrrigationMethod.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.IRRIGATION_METHOD.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Irrigation Method sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Irrigation Method API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// insurence compancy
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncInsurenceCompancy() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.INSURANCE_COMPANY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Insurance Company Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.INSURANCE_COMPANY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<InsuranceCompanyModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/insurance-company")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<InsuranceCompanyModel>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<InsuranceCompany> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());	
					int i = 0;
					
					for (InsuranceCompany m : list) {
						++i;
						InsuranceCompany existRecored = em.find(InsuranceCompany.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.INSURANCE_COMPANY.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();
			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Insurance Company sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Insurance Company API error:");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Irrigation source
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncIrrigationSource() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.IRRIGATION_SOURCE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Irrigation source API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.IRRIGATION_SOURCE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<IrrigationSourceModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/irrigation-source")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<IrrigationSourceModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<IrrigationSource> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;

					for (IrrigationSource m : list) {
						++i;
						IrrigationSource existRecored = em.find(IrrigationSource.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.IRRIGATION_SOURCE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Irrigation Source sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Irrigation Source API error:");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// for Language Method
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncLanguage() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.LANGUAGE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Language API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.LANGUAGE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<LanguageModel> response = null;
			
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/language")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<LanguageModel>>() {
				});

				pageRecordsCount = response.size();
				if (pageRecordsCount > 0) {

					List<Language> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Language m : list) {
						++i;
						Language existRecored = em.find(Language.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.LANGUAGE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Language sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Langauage API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Region
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncRegion() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.REGION.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO ::  Region API Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.REGION.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<RegionModel> response = null;
			
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/region")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<RegionModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<Region> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Region m : list) {
						++i;
						Region existRecored = em.find(Region.class, m.getRegionId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.REGION.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Region sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Region API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// residue dispose type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncResidueDisposeType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.RESIDUE_DISPOSE_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Residue Dispose Type Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.RESIDUE_DISPOSE_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<ResidueDisposeTypeModel> response = null;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/residue-dispose-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<ResidueDisposeTypeModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<ResidueDisposeType> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (ResidueDisposeType m : list) {
						++i;
						ResidueDisposeType existRecored = em.find(ResidueDisposeType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.RESIDUE_DISPOSE_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Residue Dispose Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Residue Dispose Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Season
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void syncSeason() {
//
//		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
//		syncInfo.setTableName(MasterDataTable.SEASON.name());
//		logs.add(syncInfo);
//
//		Date startTime = new Date();
//		LOGGER.info("INFO :: Season API Sync started At: " + FORMAT.format(startTime));
//
//		String lastSyncTime = "0";
//		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.SEASON.name());
//		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
//			lastSyncTime = gstmSyn.getLastSync();
//		}
//
//		try {
//
//			int pageRecordsCount = 0;
//			int totalRecordsCount = 0;
//			int pageNo = 0;
//			int totalNewRecords = 0;
//			int totalUpdatedRecords = 0;
//
//			List<SeasonModel> response = null;
//
//			do {
//				 ++pageNo;
//				LOGGER.info("INFO :: Page Number: " + pageNo);
//
//				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/season")
//						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
//						.queryParam("page", pageNo);
//
//				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
//						String.class);
//
//				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<SeasonModel>>() {
//				});
//
//				pageRecordsCount = response.size();
//
//				if (pageRecordsCount > 0) {
//
//					List<Season> list = response.stream().map(a -> a.getEntity())
//							.collect(Collectors.toList());
//
//					int i = 0;
//					for (Season m : list) {
//						++i;
//						Season existRecored = em.find(Season.class, m.getId());
//						if (existRecored != null) {
//							if (!existRecored.equals(m)) {
//								em.merge(m);
//								em.flush();
//								++totalUpdatedRecords;
//							}
//						} else {
//							em.persist(m);
//							em.flush();
//							++totalNewRecords;
//						}
//
//						if (i >= 500) {
//							i = 0;
//							em.clear();
//						}
//					}
//				}
//				
//				totalRecordsCount += pageRecordsCount;
//			} while (pageRecordsCount > 0);
//
//			if (gstmSyn == null) {
//				gstmSyn = new GstmSync();
//				gstmSyn.setSchemaName(MasterDataTable.SEASON.name());
//			}
//
//			if (totalRecordsCount > 0) {
//				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
//				gstmSyncRepository.save(gstmSyn);
//			}
//
//			Date endTime = new Date();
//
//			syncInfo.setTotalRecordsCount(totalRecordsCount);
//			syncInfo.setPageCount(pageNo-1);
//			syncInfo.setTotalNewRecords(totalNewRecords);
//			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
//			syncInfo.setSuccess(true);
//			
//
//			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
//			LOGGER.info("INFO :: Season sync Successful");
//			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
//			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
//			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
//			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
//
//		} catch (Exception e) {
//
//			syncInfo.setErrorMessage(e.getMessage());
//			LOGGER.error("ERROR :: Season Type API error: ");
//			LOGGER.error(e);
//			throw new RuntimeException(e);
//		}
//	}

	// seed source
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncSeedSource() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.SEED_SOURCE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.SEED_SOURCE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<SeedSourceModel> response = null;
			
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/seed-source")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<SeedSourceModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<SeedSource> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (SeedSource m : list) {
						++i;
						SeedSource existRecored = em.find(SeedSource.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.SEED_SOURCE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Seed Source sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Seed Source Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Machinery
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncMachinery() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.MACHINERY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Machinery Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.MACHINERY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<MachineryModel> response = null;

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/machinery")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<MachineryModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<Machinery> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Machinery m : list) {
						++i;
						Machinery existRecored = em.find(Machinery.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.MACHINERY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Machinery sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Machinery Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// loan source
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncloanSource() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.LOAN_SOURCE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: loan source API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.LOAN_SOURCE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<LoanSourceModel> response = null;
		
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/loan-source")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<LoanSourceModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<LoanSource> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (LoanSource m : list) {
						++i;
						LoanSource existRecored = em.find(LoanSource.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.LOAN_SOURCE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Loan Source sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Loan Source Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Loan Purpose
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncloanPurpose() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.LOAN_PURPOSE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.LOAN_PURPOSE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<LoanPurposeModel> response = null;
	

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/loan-purpose")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);
				
				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<LoanPurposeModel>>() {
				});

				pageRecordsCount = response.size();
		

				if (pageRecordsCount > 0) {

					List<LoanPurpose> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (LoanPurpose m : list) {
						++i;
						LoanPurpose existRecored = em.find(LoanPurpose.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.LOAN_PURPOSE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Loan Purpose sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Loan Purpose Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Kyc doc type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncKycDocType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.KYC_DOC_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: KycDocType Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.KYC_DOC_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<KycDocTypeModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/kyc-doc-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<KycDocTypeModel>>() {
				});

				pageRecordsCount = response.size();

			
				if (pageRecordsCount > 0) {

					List<KycDocType> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (KycDocType m : list) {
						++i;
						KycDocType existRecored = em.find(KycDocType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.KYC_DOC_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: KycDocType sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::KycDocType Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// govt official designation
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncGovtOfficialDesignation() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.GOVT_OFFICIAL_DESIGNATION.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: GovtOfficialDesignation API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.GOVT_OFFICIAL_DESIGNATION.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<GovtOfficialDesignationModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/govt-official-designation")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<GovtOfficialDesignationModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<GovtOfficialDesignation> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (GovtOfficialDesignation m : list) {
						++i;
						GovtOfficialDesignation existRecored = em.find(GovtOfficialDesignation.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.GOVT_OFFICIAL_DESIGNATION.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Govt Official Designation sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Govt Official Designation API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// pesticides
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPesticides() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PESTICIDES.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Pesticides Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PESTICIDES.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<PesticidesModel> response = null;
		

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/pesticides")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PesticidesModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<Pesticides> pesticidesList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Pesticides m : pesticidesList) {
						++i;
						Pesticides existRecored = em.find(Pesticides.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PESTICIDES.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Pesticides sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Pesticides API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** after acz introduce- CDT-Ujwal */
	// State Season
	
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void syncStateSeason() {
//
//		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
//		syncInfo.setTableName(MasterDataTable.STATE_SEASON.name());
//		logs.add(syncInfo);
//
//		Date startTime = new Date();
//		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));
//
//		String lastSyncTime = "0";
//		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STATE_SEASON.name());
//		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
//			lastSyncTime = gstmSyn.getLastSync();
//		}
//
//		try {
//
//			int pageRecordsCount = 0;
//			int totalRecordsCount = 0;
//			int pageNo = 0;
//			int totalNewRecords = 0;
//			int totalUpdatedRecords = 0;
//
//			List<StateSeasonModel> response = null;
//
//			do {
//				++pageNo;
//				LOGGER.info("INFO :: Page Number: " + pageNo);
//
//				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/state-season")
//						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
//						.queryParam("page", pageNo);
//
//				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
//						String.class);
//
//				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StateSeasonModel>>() {
//				});
//
//				pageRecordsCount = response.size();
//
//				if (pageRecordsCount > 0) {
//
//					List<StateSeason> stateSeasonList = response.stream().map(a -> a.getEntity())
//							.collect(Collectors.toList());
//
//					int i = 0;
//					for (StateSeason m : stateSeasonList) {
//						++i;
//						StateSeason existRecored = em.find(StateSeason.class, m.getId());
//						if (existRecored != null) {
//							if (!existRecored.equals(m)) {
//								em.merge(m);
//								em.flush();
//								++totalUpdatedRecords;
//							}
//						} else {
//							em.persist(m);
//							em.flush();
//							++totalNewRecords;
//						}
//
//						if (i >= 500) {
//							i = 0;
//							em.clear();
//						}
//					}
//
//				}
//
//				totalRecordsCount += pageRecordsCount;
//			} while (pageRecordsCount > 0);
//
//			if (gstmSyn == null) {
//				gstmSyn = new GstmSync();
//				gstmSyn.setSchemaName(MasterDataTable.STATE_SEASON.name());
//			}
//
//			if (totalRecordsCount > 0) {
//				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
//				gstmSyncRepository.save(gstmSyn);
//			}
//
//			Date endTime = new Date();
//
//			syncInfo.setTotalRecordsCount(totalRecordsCount);
//			syncInfo.setPageCount(pageNo - 1);
//			syncInfo.setTotalNewRecords(totalNewRecords);
//			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
//			syncInfo.setSuccess(true);
//
//			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
//			LOGGER.info("INFO :: State Season sync Successful");
//			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
//			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
//			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
//			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
//
//		} catch (Exception e) {
//
//			syncInfo.setErrorMessage(e.getMessage());
//			LOGGER.error("ERROR :: State Season API error: ");
//			LOGGER.error(e);
//			throw new RuntimeException(e);
//		}
//	}
	 
	// Tehsil
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncTehsil() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.TEHSIL.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Tehsil Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.TEHSIL.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<TehsilModel> response = null;
		

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/tehsil")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<TehsilModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<Tehsil> tehsilList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Tehsil m : tehsilList) {
						++i;
						Tehsil existRecored = em.find(Tehsil.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.TEHSIL.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Tehsil API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// State Language Master
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStateLanguageMaster() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STATE_LANGUAGE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: StateLanguageMaster Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STATE_LANGUAGE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<StateLanguageMasterModel> response = null;
	

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/state-language-master").queryParam("unixTimestamp", lastSyncTime)
						.queryParam("apiKey", masterDataKey).queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StateLanguageMasterModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<StateLanguage> stateLanguageList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (StateLanguage m : stateLanguageList) {
						++i;
						StateLanguage existRecored = em.find(StateLanguage.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STATE_LANGUAGE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: State Language sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: State Language API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// stress type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Stress Type Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<StressTypeModel> response = null;
	

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/stress-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressTypeModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<StressType> stressTypeList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;

					for (StressType m : stressTypeList) {
						++i;
						StressType existRecored = em.find(StressType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// poi type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPoiType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.POI_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Poi Type Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.POI_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<PoiTypeModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/poi-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PoiTypeModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<PoiType> poiTypeList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (PoiType m : poiTypeList) {
						++i;
						PoiType existRecored = em.find(PoiType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.POI_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: poi Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: poi Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// AgrochemicalType
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncAgrochemicalType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.AGROCHEMICAL_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Agrochemical Type Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.AGROCHEMICAL_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<ArgochemicalTypeModel> response = null;

			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/agrochemical-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<ArgochemicalTypeModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<ArgochemicalType> argochemicalTypeList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (ArgochemicalType m : argochemicalTypeList) {
						++i;
						ArgochemicalType existRecored = em.find(ArgochemicalType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.AGROCHEMICAL_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Argochemical Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Argochemical Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Argochemical Company
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncArgochemicalCompany() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.AGROCHEMICAL_COMPANY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.AGROCHEMICAL_COMPANY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<ArgochemicalCompanyModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/company")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<ArgochemicalCompanyModel>>() {
				});

				pageRecordsCount = response.size();



				if (pageRecordsCount > 0) {

					List<ArgochemicalCompany> argochemicalCompanyList = response.stream()
							.map(a -> a.getEntity()).collect(Collectors.toList());

					int i = 0;
					for (ArgochemicalCompany m : argochemicalCompanyList) {
						++i;
						ArgochemicalCompany existRecored = em.find(ArgochemicalCompany.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.AGROCHEMICAL_COMPANY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Argochemical Company sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Argochemical Company API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Agrochemical
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncAgrochemical() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.AGROCHEMICAL.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Argochemical API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.AGROCHEMICAL.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<ArgochemicalModel> response = null;
		
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/agrochemical")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<ArgochemicalModel>>() {
				});

				pageRecordsCount = response.size();



				if (pageRecordsCount > 0) {

					List<Argochemical> argochemicalList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Argochemical m : argochemicalList) {
						++i;
						Argochemical existRecored = em.find(Argochemical.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.AGROCHEMICAL.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Argochemical  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Argochemical  API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Village
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVillage() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VILLAGE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Village Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VILLAGE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<VillageModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/village")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);
				
				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VillageModel>>() {
				});

				pageRecordsCount = response.size();



				if (pageRecordsCount > 0) {

					List<Village> villageList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Village m : villageList) {
						++i;
						Village existRecored = em.find(Village.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							} else {
								LOGGER.info("INFO :: duplocate "+m.getId());
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VILLAGE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Village  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Village  API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// phenophase
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPhenophase() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PHENOPHASE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: phenophase Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PHENOPHASE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<PhenophaseModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/phenophase")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PhenophaseModel>>() {
				});

				pageRecordsCount = response.size();
	

				if (pageRecordsCount > 0) {

					List<Phenophase> phenophaseList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (Phenophase m : phenophaseList) {
						++i;
						Phenophase existRecored = em.find(Phenophase.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PHENOPHASE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Phenophase  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Phenophase  API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// unit Of Measurement
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncUnitOfMeasurement() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.UNIT_OF_MEASUREMENT.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: unit Of Measurement API Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.UNIT_OF_MEASUREMENT.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<UnitOfMeasurementModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/unit-of-measurement")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<UnitOfMeasurementModel>>() {
				});

				pageRecordsCount = response.size();


				if (pageRecordsCount > 0) {

					List<UnitOfMeasurement> unitOfMeasurementList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (UnitOfMeasurement m : unitOfMeasurementList) {
						++i;
						UnitOfMeasurement existRecored = em.find(UnitOfMeasurement.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.UNIT_OF_MEASUREMENT.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Unit Of Measurement  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Unit Of Measurement  API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// panchayat
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPanchayat() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PANCHAYAT.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Panchayat API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PANCHAYAT.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<PanchayatModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/panchayat")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PanchayatModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<Panchayat> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (Panchayat m : list) {
						++i;
						Panchayat existRecored = em.find(Panchayat.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PANCHAYAT.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Panchayat sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Panchayat API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Stress
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStress() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Stress API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StressModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/stress")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<Stress> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (Stress m : list) {
						++i;
						Stress existRecored = em.find(Stress.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// AgrochemicalBrand
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncAgrochemicalBrand() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.AGROCHEMICAL_BRAND.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Agrochemical Brand Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.AGROCHEMICAL_BRAND.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<AgrochemicalBrandModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/agrochemical-brand")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<AgrochemicalBrandModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<AgrochemicalBrand> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (AgrochemicalBrand m : list) {
						++i;
						AgrochemicalBrand existRecored = em.find(AgrochemicalBrand.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.AGROCHEMICAL_BRAND.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Agrochemical Brand sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Agrochemical Brand API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Variety
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVariety() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VARIETY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Variety API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VARIETY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<VarietyModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/variety")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);
				
				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VarietyModel>>() {
				});

				pageRecordsCount = response.size();

				
				if (pageRecordsCount > 0) {
					List<Variety> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (Variety m : list) {
						++i;
						Variety existRecored = em.find(Variety.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VARIETY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Variety sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Variety API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// enrollment_place
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncEnrollmentPlace() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.ENROLLMENT_PLACE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Enrollment Place API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.ENROLLMENT_PLACE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<EnrollmentPlaceModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/enrollment-place")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<EnrollmentPlaceModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<EnrollmentPlace> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (EnrollmentPlace m : list) {
						++i;
						EnrollmentPlace existRecored = em.find(EnrollmentPlace.class, m.getID());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.ENROLLMENT_PLACE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Enrollment Place sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Enrollment Place API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// mobile_type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncMobileType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.MOBILE_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Mobile Type API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.MOBILE_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<MobileTypeModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/mobile-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<MobileTypeModel>>() {
				});

				pageRecordsCount = response.size();
				if (pageRecordsCount > 0) {
					List<MobileType> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (MobileType m : list) {
						++i;
						MobileType existRecored = em.find(MobileType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.MOBILE_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Mobile Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Mobile Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Phenophase Duration
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPhenophaseDuration() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PHENOPHASE_DURATION.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Phenophase Duration MasterData API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PHENOPHASE_DURATION.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<PhenophaseDurationMasterDataModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/phenophase-duration")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PhenophaseDurationMasterDataModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<PhenophaseDuration> phenophaseDurationList = response.stream()
							.map(a -> a.getEntity()).collect(Collectors.toList());

					int i = 0;
					for (PhenophaseDuration m : phenophaseDurationList) {
						++i;
						PhenophaseDuration existRecored = em.find(PhenophaseDuration.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PHENOPHASE_DURATION.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			fileUtility.createAndUploadZipFile("phenophaseDuration"); // for data zip

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Phenophase Duration sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Phenophase Duration API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// plant-part
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPlantPart() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PLANT_PART.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: PlantPart Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PLANT_PART.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<PlanPartModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/plant-part")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PlanPartModel>>() {
				});

				pageRecordsCount = response.size();
				if (pageRecordsCount > 0) {
					List<PlantPart> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (PlantPart m : list) {
						++i;
						PlantPart existRecored = em.find(PlantPart.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PLANT_PART.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Plant Part sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Plant Part API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// $$stress symptoms $$
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressSymptoms() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_SYMPTOMS.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Stress Symptoms API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_SYMPTOMS.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {

			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;

			List<StressSymptomsModel> response = null;


			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/stress-symptoms")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressSymptomsModel>>() {
				});
				
				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					List<StressSymptoms> stressSymptomsList = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());

					int i = 0;
					for (StressSymptoms m : stressSymptomsList) {
						++i;
						StressSymptoms existRecored = em.find(StressSymptoms.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {

							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_SYMPTOMS.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Symptoms sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Symptoms API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Vip-status
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVipStatus() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VIP_STATUS.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: VipStatus Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VIP_STATUS.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<VipStatusModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/vip-status")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VipStatusModel>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<VipStatus> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (VipStatus m : list) {
						++i;
						VipStatus existRecored = em.find(VipStatus.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VIP_STATUS.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Vip Status sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Vip Status API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// proxy-relation-type
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncProxyRelationType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PROXY_RELATION_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: ProxyRelationType Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PROXY_RELATION_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<ProxyRelationTypeModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/proxy-relation-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<ProxyRelationTypeModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<ProxyRelationType> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (ProxyRelationType m : list) {
						++i;
						ProxyRelationType existRecored = em.find(ProxyRelationType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PROXY_RELATION_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Proxy Relation Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Proxy Relation Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// seed-treatment-agent
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncSeedTreatmentAgent() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.SEED_TREATMENT_AGENT.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Seed Treatment Agent Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.SEED_TREATMENT_AGENT.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<SeedTreatmentAgentModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/seed-treatment-agent")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<SeedTreatmentAgentModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<SeedTreatmentAgent> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (SeedTreatmentAgent m : list) {
						++i;
						SeedTreatmentAgent existRecored = em.find(SeedTreatmentAgent.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.SEED_TREATMENT_AGENT.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Seed Treatment Agent Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Seed Treatment Agent API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// fertilizer
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncFertilizer() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.FERTILIZER.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Fertilizer API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.FERTILIZER.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<FertilizerModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/fertilizer")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<FertilizerModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<Fertilizer> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					
					int i = 0;
					for (Fertilizer m : list) {
						++i;
						Fertilizer existRecored = em.find(Fertilizer.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.FERTILIZER.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Fertilizer sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Fertilizer API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// $$ state-season-commodity $$

	/** after acz introduce- CDT-Ujwal */
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void syncStateSeasonCommodity() {
//
//		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
//		syncInfo.setTableName(MasterDataTable.STATE_SEASON_COMMODITY.name());
//		logs.add(syncInfo);
//
//		Date startTime = new Date();
//		LOGGER.info("INFO :: StateSeasonCommodity API Sync start: " + FORMAT.format(startTime));
//
//		String lastSyncTime = "0";
//		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STATE_SEASON_COMMODITY.name());
//		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
//			lastSyncTime = gstmSyn.getLastSync();
//		}
//
//		try {
//			List<StateSeasonCommodityModel> response = null;
//			int pageRecordsCount = 0;
//			int totalRecordsCount = 0;
//			int pageNo = 0;
//			int totalNewRecords = 0;
//			int totalUpdatedRecords = 0;
//			do {
//				++pageNo;
//				LOGGER.info("INFO :: Page Number: " + pageNo);
//
//				UriComponentsBuilder builder = UriComponentsBuilder
//						.fromHttpUrl(masterDataApi + "/state-season-commodity")
//						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
//						.queryParam("page", pageNo);
//
//				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
//						String.class);
//
//				response = mapper.readValue(apiResponse.getBody(),
//						new TypeReference<List<StateSeasonCommodityModel>>() {
//						});
//
//				pageRecordsCount = response.size();
//
//				if (pageRecordsCount > 0) {
//					List<StateSeasonCommodity> list = response.stream().map(a -> a.getEntity())
//							.collect(Collectors.toList());
//					int i = 0;
//					for (StateSeasonCommodity m : list) {
//
//						Optional<Integer> stateSeason = stateSeasonRepo.findByStateIdAndSeasonId(m.getStateId(),
//								m.getStateSeasonId());
//
//						if (stateSeason.isPresent() == true) {
//
//							m.setStateSeasonId(stateSeason.get().intValue());
//							++i;
//							StateSeasonCommodity existRecored = em.find(StateSeasonCommodity.class, m.getId());
//							if (existRecored != null) {
//								if (!existRecored.equals(m)) {
//									em.merge(m);
//									em.flush();
//									++totalUpdatedRecords;
//								}
//							} else {
//								em.persist(m);
//								em.flush();
//								++totalNewRecords;
//							}
//
//							if (i >= 500) {
//								i = 0;
//								em.clear();
//							}
//						}
//					}
//				}
//
//				totalRecordsCount += pageRecordsCount;
//			} while (pageRecordsCount > 0);
//
//			if (gstmSyn == null) {
//				gstmSyn = new GstmSync();
//				gstmSyn.setSchemaName(MasterDataTable.STATE_SEASON_COMMODITY.name());
//			}
//
//			if (totalRecordsCount > 0) {
//				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
//				gstmSyncRepository.save(gstmSyn);
//			}
//
//			Date endTime = new Date();
//
//			syncInfo.setTotalRecordsCount(totalRecordsCount);
//			syncInfo.setPageCount(pageNo - 1);
//			syncInfo.setTotalNewRecords(totalNewRecords);
//			syncInfo.setTotalNewRecords(totalNewRecords);
//			syncInfo.setSuccess(true);
//
//			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
//			LOGGER.info("INFO :: State Season Commodity sync Successful");
//			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
//			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
//			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
//			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
//		} catch (Exception e) {
//
//			syncInfo.setErrorMessage(e.getMessage());
//			LOGGER.error("ERROR :: State Season Commodity API error: ");
//			LOGGER.error(e);
//			throw new RuntimeException(e);
//		}
//	}
	 

	// stress-control-measure
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressControlMeasure() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_CONTROL_MEASURES.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: StressControlMeasure API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_CONTROL_MEASURES.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StressControlMeasureModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/stress-control-measure")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);
				
				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressControlMeasureModel>>() {
				});

				pageRecordsCount = response.size();

				
				if (pageRecordsCount > 0) {
					List<StressControlMeasure> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (StressControlMeasure m : list) {
						++i;
						StressControlMeasure existRecored = em.find(StressControlMeasure.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_CONTROL_MEASURES.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Control Measure sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Control Measure API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Stress Severity Control Measures
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressSeverityControlMeasures() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_SEVERITY_CONTROL_MEASURES.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: StressSeverityControlMeasures API Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_SEVERITY_CONTROL_MEASURES.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StressSeverityControlMeasuresModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/recommendation")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressSeverityControlMeasuresModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<StressSeverityControlMeasures> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (StressSeverityControlMeasures m : list) {
						++i;
						StressSeverityControlMeasures existRecored = em.find(StressSeverityControlMeasures.class,
								m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_SEVERITY_CONTROL_MEASURES.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Severity Control Measures sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Severity Control Measures API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// stress_severity
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressSeverity() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_SEVERITY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: StressSeverity Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_SEVERITY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StressSeverityModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/stress-severity")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressSeverityModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<StressSeverity> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (StressSeverity m : list) {
						++i;
						StressSeverity existRecored = em.find(StressSeverity.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_SEVERITY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Severity sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Severity API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// vip-designation
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVipDesignation() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VIP_DESIGNATION.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: VipDesignation Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VIP_DESIGNATION.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<VipDesignationModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/vip-designation")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VipDesignationModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<VipDesignation> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (VipDesignation m : list) {
						++i;
						VipDesignation existRecored = em.find(VipDesignation.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VIP_DESIGNATION.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Vip Designation sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Vip Designation API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// stress-control-recommendation
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStressControlRecommendation() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STRESS_RECOMMENDATION.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: StressControlRecommendation Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STRESS_RECOMMENDATION.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StressRecommendationModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/stress-control-recommendation")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StressRecommendationModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<StressRecommendation> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (StressRecommendation m : list) {
						++i;
						StressRecommendation existRecored = em.find(StressRecommendation.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STRESS_RECOMMENDATION.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Stress Control Measure sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Stress Control Measure API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Health Questionnaire
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncHealthQuestionnaire() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.HEALTH_QUESTIONNAIRE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: HealthQuestionnaire Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.HEALTH_QUESTIONNAIRE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<HealthQuestionnaireModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/health")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<HealthQuestionnaireModel>>() {
				});

				pageRecordsCount = response.size();
	
				if (pageRecordsCount > 0) {
					List<HealthQuestionnaire> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (HealthQuestionnaire m : list) {
						++i;
						HealthQuestionnaire existRecored = em.find(HealthQuestionnaire.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.HEALTH_QUESTIONNAIRE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Health Questionnaire sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Health Questionnaire API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// variety_quality
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVarietyQuality() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VARIETY_QUALITY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: VarietyQuality Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VARIETY_QUALITY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<VarietyQualityModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/variety-quality")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VarietyQualityModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<VarietyQuality> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (VarietyQuality m : list) {
						++i;
						VarietyQuality existRecored = em.find(VarietyQuality.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VARIETY_QUALITY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Variety Quality sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Variety Quality API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	
// Changes For ACZ Introduce - Rinkesh Start
	// regional-variety
	/*
	 * @Transactional(propagation = Propagation.REQUIRES_NEW) public void
	 * syncRegionalVariety() {
	 * 
	 * MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
	 * syncInfo.setTableName(MasterDataTable.REGIONAL_VARIETY.name());
	 * logs.add(syncInfo);
	 * 
	 * Date startTime = new Date();
	 * LOGGER.info("INFO :: RegionalVariety Sync started At: " +
	 * FORMAT.format(startTime));
	 * 
	 * String lastSyncTime = "0"; GstmSync gstmSyn =
	 * gstmSyncRepository.findBySchemaName(MasterDataTable.REGIONAL_VARIETY.name());
	 * if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) { lastSyncTime =
	 * gstmSyn.getLastSync(); }
	 * 
	 * try { List<RegionalVariety> response = null; int pageRecordsCount = 0; int
	 * totalRecordsCount = 0; int pageNo = 0; int totalNewRecords = 0; int
	 * totalUpdatedRecords = 0; do { ++pageNo; LOGGER.info("INFO :: Page Number: " +
	 * pageNo);
	 * 
	 * UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi
	 * + "/regional-variety") .queryParam("unixTimestamp",
	 * lastSyncTime).queryParam("apiKey", masterDataKey) .queryParam("page",
	 * pageNo);
	 * 
	 * ResponseEntity<String> apiResponse =
	 * restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
	 * String.class);
	 * 
	 * response = mapper.readValue(apiResponse.getBody(), new
	 * TypeReference<List<RegionalVariety>>() { });
	 * 
	 * pageRecordsCount = response.size();
	 * 
	 * if (pageRecordsCount > 0) {
	 * 
	 * int i = 0; for (RegionalVariety m : response) { ++i; RegionalVariety
	 * existRecored = em.find(RegionalVariety.class, m.getId()); if (existRecored !=
	 * null) { if (!existRecored.equals(m)) { em.merge(m); em.flush();
	 * ++totalUpdatedRecords; } } else { em.persist(m); em.flush();
	 * ++totalNewRecords; }
	 * 
	 * if (i >= 500) { i = 0; em.clear(); } } }
	 * 
	 * totalRecordsCount += pageRecordsCount; } while (pageRecordsCount > 0);
	 * 
	 * if (gstmSyn == null) { gstmSyn = new GstmSync();
	 * gstmSyn.setSchemaName(MasterDataTable.REGIONAL_VARIETY.name()); }
	 * 
	 * if (totalRecordsCount > 0) {
	 * gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
	 * gstmSyncRepository.save(gstmSyn); }
	 * 
	 * Date endTime = new Date();
	 * 
	 * syncInfo.setTotalRecordsCount(totalRecordsCount);
	 * syncInfo.setPageCount(pageNo-1);
	 * syncInfo.setTotalNewRecords(totalNewRecords);
	 * syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
	 * syncInfo.setSuccess(true);
	 * 
	 * 
	 * LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
	 * LOGGER.info("INFO :: Regional Variety sync Successful");
	 * LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
	 * LOGGER.info("INFO :: Total Page : " + (pageNo-1));
	 * LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
	 * LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords); }
	 * catch (Exception e) {
	 * 
	 * syncInfo.setErrorMessage(e.getMessage());
	 * LOGGER.error("ERROR :: Regional Variety API error: "); LOGGER.error(e); throw
	 * new RuntimeException(e); } }
	 */

//	TileZL11
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncTileZL11() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.TILE_ZL11.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Tile ZL 11 Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.TILE_ZL11.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<TileZL11Model> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/gstm-tilezl11")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<TileZL11Model>>() {
				});
				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<TileZL11> list = response.stream().map(a -> a.getEntity()).collect(Collectors.toList());
					int i = 0;
					for (TileZL11 m : list) {
						++i;
						TileZL11 existRecored = em.find(TileZL11.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;

			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.TILE_ZL11.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);

			Date endTime = new Date();
			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Mbep sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Mbep API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Regional Commodity */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncRegionalCommodity() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.REGIONAL_COMMODITY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Cropdata Calendar Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.REGIONAL_COMMODITY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<RegionalCommodity> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/regional-commodity")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<RegionalCommodity>>() {});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (RegionalCommodity m : response) {

						++i;
						RegionalCommodity existRecored = em.find(RegionalCommodity.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.REGIONAL_COMMODITY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);


			LOGGER.info("INFO :: Regional Commodity Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Regional Commodity sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Cropdata Calendar API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Changes By - Rinkesh - End
	// Mbep
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncMbep() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.MBEP.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Mbep Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.MBEP.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<MbepModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				 LOGGER.info("INFO :: Page Number: " + pageNo);
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/mbep")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<MbepModel>>() {
				});
				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {
					List<MbepEntity> list = response.stream().map(a -> a.getEntity())
							.collect(Collectors.toList());
					int i = 0;
					for (MbepEntity m : list) {
						++i;
						MbepEntity existRecored = em.find(MbepEntity.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				
				totalRecordsCount += pageRecordsCount;

			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.MBEP.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}
			


			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			
			Date endTime = new Date();
			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Mbep sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Mbep API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Calling Status
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncCallingStatus() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.CALLING_STATUS.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.CALLING_STATUS.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
		
			List<CallingStatus> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/calling-status")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<CallingStatus>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {

					int i = 0;
					for (CallingStatus m : response) {
						++i;
						CallingStatus existRecored = em.find(CallingStatus.class, m.getCallingStatusId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.CALLING_STATUS.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Calling Status sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Calling Status API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// GeneralBank
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncGeneralBank() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.GENERAL_BANK.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.GENERAL_BANK.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<Bank> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/general-bank")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<Bank>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (Bank m : response) {
						++i;
						Bank existRecored = em.find(Bank.class, m.getId());

						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.GENERAL_BANK.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			fileUtility.createAndUploadZipFile("bank"); // for data zip

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Bank sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Bank API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	
	// general-bank-branch
		@Transactional(propagation = Propagation.REQUIRES_NEW)
		public void syncGeneralBankBranch() {

			MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
			syncInfo.setTableName(MasterDataTable.GENERAL_BANK_BRANCH.name());
			logs.add(syncInfo);

			Date startTime = new Date();
			LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

			String lastSyncTime = "0";
			GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.GENERAL_BANK_BRANCH.name());
			if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
				lastSyncTime = gstmSyn.getLastSync();
			}

			try {
				List<BankBranch> response = null;
				int pageRecordsCount = 0;
				int totalRecordsCount = 0;
				int pageNo = 0;
				int totalNewRecords = 0;
				int totalUpdatedRecords = 0;
				do {
					 ++pageNo;
					LOGGER.info("INFO :: Page Number: " + pageNo);

					UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/general-bank-branch")
							.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
							.queryParam("page", pageNo);

					ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
							String.class);

					response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<BankBranch>>() {
					});

					pageRecordsCount = response.size();

					if (pageRecordsCount > 0) {

						int i = 0;
						for (BankBranch m : response) {
							++i;
							BankBranch existRecored = em.find(BankBranch.class, m.getId());
							if (existRecored != null) {
								if (!existRecored.equals(m)) {
									em.merge(m);
									em.flush();
									++totalUpdatedRecords;
								}
							} else {
								em.persist(m);
								em.flush();
								++totalNewRecords;
							}

							if (i >= 500) {
								i = 0;
								em.clear();
							}
						}
					}
					
					totalRecordsCount += pageRecordsCount;
				} while (pageRecordsCount > 0);

				if (gstmSyn == null) {
					gstmSyn = new GstmSync();
					gstmSyn.setSchemaName(MasterDataTable.GENERAL_BANK_BRANCH.name());
				}

				if (totalRecordsCount > 0) {
					gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
					gstmSyncRepository.save(gstmSyn);
				}

				Date endTime = new Date();

				syncInfo.setTotalRecordsCount(totalRecordsCount);
				syncInfo.setPageCount(pageNo-1);
				syncInfo.setTotalNewRecords(totalNewRecords);
				syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
				syncInfo.setSuccess(true);
				fileUtility.createAndUploadZipFile("branch"); // for data zip

				LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
				LOGGER.info("INFO :: Bank Branch sync Successful");
				LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
				LOGGER.info("INFO :: Total Page : " + (pageNo-1));
				LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
				LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
			} catch (Exception e) {

				syncInfo.setErrorMessage(e.getMessage());
				LOGGER.error("ERROR :: Bank Branch API error: ");
				LOGGER.error(e);
				throw new RuntimeException(e);
			}
		}


	// allied-activity
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncAlliedActivity() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.ALLIED_ACTIVITY.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.ALLIED_ACTIVITY.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<AlliedActivity> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/activity")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<AlliedActivity>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (AlliedActivity m : response) {
						++i;
						AlliedActivity existRecored = em.find(AlliedActivity.class, m.getId());

						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.ALLIED_ACTIVITY.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			fileUtility.createAndUploadZipFile("activity"); // for data zip

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Allied-activity  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Allied-activity API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	// Village To Village Distance
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVillageToVillageDistance() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VILLAGE_TO_VILLAGE_DISTANCE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Village To Village Distance Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VILLAGE_TO_VILLAGE_DISTANCE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			
			List<VillageToVillageDistance> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/village-to-village-distance")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VillageToVillageDistance>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (VillageToVillageDistance m : response) {
						++i;
						VillageToVillageDistance existRecored = em.find(VillageToVillageDistance.class, m.getId());

						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VILLAGE_TO_VILLAGE_DISTANCE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Village To Village Distance  sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Village To Village Distance API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	
	/** Regional Monthly Weather Base Travel Time */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncRegionalMonthlyWeatherBaseTravelTime() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.REGIONAL_MONTHLY_WEATHER_BASED_TRAVEL_TIME.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository
				.findBySchemaName(MasterDataTable.REGIONAL_MONTHLY_WEATHER_BASED_TRAVEL_TIME.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<MonthlyWeatherBasedTravelTime> response = new ArrayList<>();
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/regional-monthly-weather-based-travel-time")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<MonthlyWeatherBasedTravelTime>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (MonthlyWeatherBasedTravelTime m : response) {

						++i;
						MonthlyWeatherBasedTravelTime existRecored = em.find(MonthlyWeatherBasedTravelTime.class,
								m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.REGIONAL_MONTHLY_WEATHER_BASED_TRAVEL_TIME.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Monthly Weather Base Travel Time sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Monthly Weather Base Travel Time API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** general-terrain-type */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncGeneralTerrainType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.GENERAL_TERRAIN_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: General Terrain Type Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.GENERAL_TERRAIN_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<GeneralTerrainType> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/general-terrain-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<GeneralTerrainType>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (GeneralTerrainType m : response) {

						++i;
						GeneralTerrainType existRecored = em.find(GeneralTerrainType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.GENERAL_TERRAIN_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: General Terrain Type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: General Terrain Type API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Regional Connectivity Time */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncRegionalConnectivityTime() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.REGIONAL_CONNECTIVITY_TIME.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: RegionalConnectivityTime Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.REGIONAL_CONNECTIVITY_TIME.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<RegionalConnectivityTime> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/regional-connectivity-time")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<RegionalConnectivityTime>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (RegionalConnectivityTime m : response) {

						++i;
						RegionalConnectivityTime existRecored = em.find(RegionalConnectivityTime.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.REGIONAL_CONNECTIVITY_TIME.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Regional Connectivity Time sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {
			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Regional Connectivity Time API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Village To RL Distance */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncVillageToRLDistance() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.VILLAGE_TO_RL_DISTANCE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.VILLAGE_TO_RL_DISTANCE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<VillageToRlDistance> response = new ArrayList<>();
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/village-to-rl-distance")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<VillageToRlDistance>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (VillageToRlDistance m : response) {

						++i;
						VillageToRlDistance existRecored = em.find(VillageToRlDistance.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.VILLAGE_TO_RL_DISTANCE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Village To Rl Distance sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Village To Rl Distance API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Weather Base Travel Time */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncWeatherBasedTravelTime() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.WEATHER_BASED_TRAVEL_TIME.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Weather Based Travel Time Sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.WEATHER_BASED_TRAVEL_TIME.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<WeatherBasedTravelTime> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/weather-based-travel-time")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<WeatherBasedTravelTime>>() {
				});

				pageRecordsCount = response.size();
				
				if (pageRecordsCount > 0) {

					int i = 0;
					for (WeatherBasedTravelTime m : response) {

						++i;
						WeatherBasedTravelTime existRecored = em.find(WeatherBasedTravelTime.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.WEATHER_BASED_TRAVEL_TIME.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Weather Based Travel Time sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR ::Weather Based Travel Time API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Task Type Specification */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncTaskTypeSpecifications() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.TASK_TYPE_SPECIFICATIONS.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.TASK_TYPE_SPECIFICATIONS.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<TaskTypeSpecifications> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/task-type-specifications")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<TaskTypeSpecifications>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (TaskTypeSpecifications m : response) {

						++i;
						TaskTypeSpecifications existRecored = em.find(TaskTypeSpecifications.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.TASK_TYPE_SPECIFICATIONS.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Task Type Specifications sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Task Type Specifications API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	

	/** Cropdata Calendar */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncCropdataCalendar() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.CROPDATA_CALENDER.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Cropdata Calendar Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.CROPDATA_CALENDER.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<CropdataCalendar> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/cropdata-calendar")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<CropdataCalendar>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (CropdataCalendar m : response) {

						++i;
						CropdataCalendar existRecored = em.find(CropdataCalendar.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.CROPDATA_CALENDER.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Cropdata Calendar Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Cropdata Calendar sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Cropdata Calendar API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	
	/** HS-code synch */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncHsCode() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.HS_CODE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: HS CODE Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.HS_CODE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<HsCode> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				 ++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/hs-code")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<HsCode>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (HsCode m : response) {

						++i;
						HsCode existRecored = em.find(HsCode.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);

			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.HS_CODE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: HS CODE Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: HS CODE sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: HS CODE API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	

	/** Standard Quantity Chart */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncStandardQuantityChart() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STANDARD_QUANTITY_CHART.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: Standard Quantity Sync start: " + FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STANDARD_QUANTITY_CHART.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<StandardQuantityChart> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(masterDataApi + "/agri_standard_quantity_chart")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<StandardQuantityChart>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (StandardQuantityChart m : response) {

						++i;
						StandardQuantityChart existRecored = em.find(StandardQuantityChart.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}
				
				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STANDARD_QUANTITY_CHART.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo-1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);
			

			LOGGER.info("INFO :: Standard Quantity Chart Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: Standard Quantity Chart sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo-1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: Standard Quantity Chart API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	
	/** Started lh warehouse- CDT-ujwal- Start */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncLhWarehouse() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.LH_WAREHOUSE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: lh-warehouse Sync start: {}", FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.LH_WAREHOUSE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}
		try {
			List<LhWarehouseModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			List<Integer> regionIds = null;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/lh-warehouse")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<LhWarehouseModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (LhWarehouseModel m : response) {
//						regionIds = m.getRegionId().String
						++i;
						LhWarehouseModel existRecored = em.find(LhWarehouseModel.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.LH_WAREHOUSE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);

			fileUtility.createAndUploadZipFile("lhWarehouse"); // for lhWarehouse data zip
			LOGGER.info("INFO :: lh warehouse Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: lh warehouse sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: lh warehouse API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	/** Started lh warehouse- CDT-ujwal- End */

	/** Started Packaging Type- CDT-ujwal- Start */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncPackagingType() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.PACKAGING_TYPE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: packaging type Sync start: {}", FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.PACKAGING_TYPE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}
		try {
			List<PackagingType> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/packaging-type")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<PackagingType>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (PackagingType m : response) {

						++i;
						PackagingType existRecored = em.find(PackagingType.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.PACKAGING_TYPE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);

			fileUtility.createAndUploadZipFile("packaging"); // for packaging data zip
			LOGGER.info("INFO :: packaging type Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: packaging type sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: lh warehouse API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	/** Started Packaging Type- CDT-ujwal- End */
	
	/** Started quality parameter- CDT-ujwal- Start */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncQualityParameter() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.QUALITY_PARAMETERE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: quality parameter Sync start: {}", FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.QUALITY_PARAMETERE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}
		try {
			List<QualityParameter> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/agri-quality-parameter")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<QualityParameter>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (QualityParameter m : response) {

						++i;
						
//						QualityParameter existRecored = new QualityParameter();
						QualityParameter existRecored = em.find(QualityParameter.class, m.getId());
						
						if (existRecored != null) {
							
							if (!existRecored.equals(m)) {
								
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.QUALITY_PARAMETERE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);


			LOGGER.info("INFO :: quality parameter Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: quality parameter sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: quality parameter API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	/** Started quality parameter- CDT-ujwal- End */
	
	/** Started quality commodity parameter- CDT-ujwal- Start */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncQualityCommodityParameter() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.QUALITY_COMMODITY_PARAMETERE.name());
		logs.add(syncInfo);

		Date startTime = new Date();
		LOGGER.info("INFO :: quality parameter Sync start: {}", FORMAT.format(startTime));

		String lastSyncTime = "0";
		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.QUALITY_COMMODITY_PARAMETERE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}
		try {
			List<QualityCommodityParameter> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/agri-quality-commodity-parameter")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<QualityCommodityParameter>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {

					int i = 0;
					for (QualityCommodityParameter m : response) {

						++i;
						QualityParameter existRecored = em.find(QualityParameter.class, m.getId());
						if (existRecored != null) {
							if (!existRecored.equals(m)) {
								em.merge(m);
								em.flush();
								++totalUpdatedRecords;
							}
						} else {
							em.persist(m);
							em.flush();
							++totalNewRecords;
						}

						if (i >= 500) {
							i = 0;
							em.clear();
						}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.QUALITY_COMMODITY_PARAMETERE.name());
			}

			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.save(gstmSyn);
			}

			Date endTime = new Date();

			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);


			LOGGER.info("INFO :: quality commodity parameter Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: quality commodity parameter sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);
		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: quality commodity parameter API error: ");
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}
	
	/** Started quality commodity parameter- CDT-ujwal- End */
}
