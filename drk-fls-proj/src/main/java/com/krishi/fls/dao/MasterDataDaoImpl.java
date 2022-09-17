package com.krishi.fls.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.fls.entity.HealthQuestionnaire;
import com.krishi.fls.entity.MbepEntity;
import com.krishi.fls.entity.Phenophase;
import com.krishi.fls.entity.PhenophaseDuration;
import com.krishi.fls.entity.PlantPart;
import com.krishi.fls.entity.StateSeasonCommodity;
import com.krishi.fls.entity.StaticData;
import com.krishi.fls.entity.Stress;
import com.krishi.fls.entity.StressControlMeasure;
import com.krishi.fls.entity.StressSeverity;
import com.krishi.fls.entity.StressSeverityControlMeasures;
import com.krishi.fls.entity.StressSymptoms;
import com.krishi.fls.entity.StressType;
import com.krishi.fls.entity.TileZL11;
import com.krishi.fls.entity.Variety;
import com.krishi.fls.entity.VarietyQuality;
import com.krishi.fls.model.MasterData;
import com.krishi.fls.repository.HealthQuestionarieRepository;
import com.krishi.fls.repository.MbepRepository;
import com.krishi.fls.repository.PhenophaseDurationRepository;
import com.krishi.fls.repository.PhenophaseRepository;
import com.krishi.fls.repository.PlantPartRepository;
import com.krishi.fls.repository.StateSeasonCommodityRepository;
import com.krishi.fls.repository.StaticDataRepository;
import com.krishi.fls.repository.StressControlMeasureRepository;
import com.krishi.fls.repository.StressRepository;
import com.krishi.fls.repository.StressSeverityControlMeasuresRepository;
import com.krishi.fls.repository.StressSeverityRepository;
import com.krishi.fls.repository.StressSymptomsRepository;
import com.krishi.fls.repository.StressTypeRepository;
import com.krishi.fls.repository.TileZL11Repository;
import com.krishi.fls.repository.VarietyQualityRepository;
import com.krishi.fls.repository.VarietyRepository;

@Service
public class MasterDataDaoImpl implements MasterDataDao {
	
	@Autowired
	private PhenophaseRepository phenophaseRepository;
	
	@Autowired
	private PlantPartRepository plantPartRepository;
	
	@Autowired
	private StressRepository stressRepository;
	
	@Autowired
	private StressSymptomsRepository stressSymptomsRepository;
	
	@Autowired
	private StressTypeRepository stressTypeRepository;
	
	@Autowired
	private StressSeverityRepository stressSeverityRepository;
	
	@Autowired
	private HealthQuestionarieRepository healthQuestionarieRepository;
	
	@Autowired
	private PhenophaseDurationRepository phenophaseDurationRepository;
	
	@Autowired
	private StressControlMeasureRepository stressControlMeasureRepository;
	
	@Autowired
	private StressSeverityControlMeasuresRepository stressSeverityControlMeasuresRepository;
	
	@Autowired
	private StaticDataRepository StaticDataRepository;
	
	@Autowired
	private VarietyRepository varietyRepository;

	@Autowired
	private VarietyQualityRepository varietyQualityRepository;

	@Autowired
	private MbepRepository mbepRepository;
	
	@Autowired
	private StateSeasonCommodityRepository stateSeasonCommodityRepository; 
	
	@Autowired
	private TileZL11Repository tileZl11Repository;
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional(readOnly = true)
	@Override
	public MasterData getMasterData(Integer userId, Integer stateId, Integer regionId) {
		MasterData masterData = new MasterData();
		List<Phenophase> phenophase = phenophaseRepository.findAll();
		List<PlantPart> plantPart = plantPartRepository.findAll();
//		List<Stress> stress = stressRepository.findAll();
//		List<StressSymptoms> stressSymptoms = stressSymptomsRepository.findAll();
//		List<HealthQuestionnaire> healthQuestionarie=healthQuestionarieRepository.findAll();
//		List<PhenophaseDuration> phenophaseDuration = phenophaseDurationRepository.findAll();
//		List<StressSeverityControlMeasures> stressSeverityControlMeasure=stressSeverityControlMeasuresRepositoryRepository.findAll();
		
		List<StressType> stressType = stressTypeRepository.findAll();
		List<StressSeverity> stressSeverity=stressSeverityRepository.findAll();
		List<StressControlMeasure> stressControlMeasure=stressControlMeasureRepository.findAll();
		List<StaticData> staticData=StaticDataRepository.findAll();
		
		Set<Integer> commodityIds = new HashSet<Integer>();
		Set<Integer> varietyIds = new HashSet<Integer>();
		Set<Integer> stressIds = new HashSet<Integer>();
		
		/** after introduce acz- CDT-Ujwal */
//		List<StateSeasonCommodity> stateSeasonCommodities = stateSeasonCommodityRepository.findCommodities(userId);
//		if(stateSeasonCommodities.size() > 0) {
//			 commodityIds = stateSeasonCommodities.stream().map(obj -> obj.getCommodityId()).collect(Collectors.toSet());
			commodityIds = phenophaseDurationRepository.getCommodityList(userId);

//			Set<Integer> aczList = phenophaseDurationRepository.getAczIdList(stateId);
			Set<Integer> aczList = tileZl11Repository.getAczIdByRegion(regionId);
			Set<Integer> phenophaseVarietyIds =phenophaseDurationRepository.getVarietyIdsByRegion(regionId);
//		}
		
		if (aczList.size() > 0) {
			
//			List<Stress> stressList = stressRepository.getStressByCommodityIdIn(commodityIds);
			List<Stress> stressList = stressRepository.getStressByAczIdIn(aczList);
			if (stressList.size() > 0) {
				masterData.setStressList(stressList);
				stressIds = stressList.stream().map(obj -> obj.getStressId()).collect(Collectors.toSet());
			} else {
				masterData.setStressList(new ArrayList<Stress>());
			}

//			List<HealthQuestionnaire> healthQuestionarieList = healthQuestionarieRepository
//					.getHealthQuestionnaireByCommodityIdIn(commodityIds);
			List<HealthQuestionnaire> healthQuestionarieList = healthQuestionarieRepository
					.getHealthQuestionnaireByAczIdInAndVarietyIdIn(aczList,phenophaseVarietyIds);
			if (healthQuestionarieList.size() > 0) {
				masterData.setHealthQuestionarie(healthQuestionarieList);
			} else {
				masterData.setHealthQuestionarie(new ArrayList<HealthQuestionnaire>());
			}
			
			List<Variety> varietyList = varietyRepository.getVarietyByCommodityIdIn(commodityIds);	
			if(varietyList.size() > 0) {
				varietyIds = varietyList.stream().map(obj -> obj.getId()).collect(Collectors.toSet());
			}
			
		} else {
			
			masterData.setStressList(new ArrayList<Stress>());
			masterData.setHealthQuestionarie(new ArrayList<HealthQuestionnaire>());
		}
		
		if (stressIds.size() > 0) {
			List<StressSeverityControlMeasures> stressSeverityControlMeasure = stressSeverityControlMeasuresRepository
					.getStressSeverityControlMeasuresByStressIdIn(stressIds);
			if (stressSeverityControlMeasure.size() > 0) {
				masterData.setStressSeverityControlMeasure(stressSeverityControlMeasure);
			} else {
				masterData.setStressSeverityControlMeasure(new ArrayList<StressSeverityControlMeasures>());
			}
		} else {
			masterData.setStressSeverityControlMeasure(new ArrayList<StressSeverityControlMeasures>());
		}
		
		if(stressIds.size() > 0) {
			List<StressSymptoms> stressSymptomsList = stressSymptomsRepository.getStressSymptomsByStressIdIn(stressIds);			
			if(stressSymptomsList.size() > 0) {
				masterData.setStressSymptomsList(stressSymptomsList);
			} else {
				masterData.setStressSymptomsList(new ArrayList<StressSymptoms>());
			}
		} else {
			masterData.setStressSymptomsList(new ArrayList<StressSymptoms>());
		}
		
//		List<PhenophaseDuration> phenophaseDurationList = phenophaseDurationRepository.findPhenophaseDurationByStateId(userId);
//		if(phenophaseDurationList.size() > 0) {
//			masterData.setPhenophaseDurationList(phenophaseDurationList);
//		} else {
//			masterData.setPhenophaseDurationList(new ArrayList<PhenophaseDuration>());
//		}
		
		if(varietyIds.size() > 0) {
			List<VarietyQuality> varietyQualitiesList = varietyQualityRepository.getVarietyQualityByVarietyIdIn(varietyIds);
			if(varietyQualitiesList.size() > 0) {
				masterData.setVarietyQualities(varietyQualitiesList);
			} else {
				masterData.setVarietyQualities(new ArrayList<VarietyQuality>());
			}
		} else {
			masterData.setVarietyQualities(new ArrayList<VarietyQuality>());
		}
		List<MbepEntity> mbep = mbepRepository.findMbepEntity(userId);
		if(mbep.size() > 0) {
			masterData.setMbep(mbep);
		} else {
			masterData.setMbep(new ArrayList<MbepEntity>());
		}
		/** sync gstm-zl11 list - CDT-Ujwal-Start */
		List<TileZL11> tileZl11List = tileZl11Repository.findTileZl11ByRegion(userId);
		if(tileZl11List.size() > 0) {
			masterData.setTileZL11(tileZl11List);
		} else {
			masterData.setTileZL11(new ArrayList<TileZL11>());
		} 
		/** sync gstm-zl11 list - CDT-Ujwal-End */
//		List<MbepEntity> mbep = getMbepByVarietyId(getVarietyIdByUserId(userId));
 		

//		masterData.setStressSymptomsList(stressSymptoms);
//		masterData.setPhenophaseDurationList(phenophaseDuration);
//		masterData.setStressSeverityControlMeasure(stressSeverityControlMeasure);
		
 		masterData.setPhenophaseList(phenophase);
		masterData.setPlantPartList(plantPart);
		masterData.setStressControlMeasure(stressControlMeasure);
		masterData.setStressTypeList(stressType);
		masterData.setStressSeverity(stressSeverity);
		masterData.setStaticData(staticData);
		
		return masterData;
	}

}
