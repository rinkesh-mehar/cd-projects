package in.cropdata.toolsuite.drk.service.gt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.gt.GT_ZL14_Repository;
import in.cropdata.toolsuite.drk.dao.gt.GT_ZL19_Repository;
import in.cropdata.toolsuite.drk.dao.gt.GT_ZL20_Repository;
import in.cropdata.toolsuite.drk.dto.gt.GroundTruthZLInfDto;
import in.cropdata.toolsuite.drk.exceptions.DataInsertionException;
import in.cropdata.toolsuite.drk.exceptions.DuplicateDataException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.NoRecordFoundException;
import in.cropdata.toolsuite.drk.model.cases.CaseDetails;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL14;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL19;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL20;
import in.cropdata.toolsuite.drk.model.gt.GroundTruthZL14;
import in.cropdata.toolsuite.drk.model.gt.GroundTruthZL19;
import in.cropdata.toolsuite.drk.model.gt.GroundTruth;
import in.cropdata.toolsuite.drk.model.gt.Spots;
import in.cropdata.toolsuite.drk.model.gt.Stress;

@Service
public class GroundTruthService {

	@Autowired
	private GT_ZL20_Repository gt_ZL20_Repository;

	@Autowired
	private GT_ZL19_Repository gt_ZL19_Repository;

	@Autowired
	private GT_ZL14_Repository gt_ZL14_Repository;

	public Map<String, Object> addGroundTruthData(List<GroundTruth> groundTruthList) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		List<GT_ZL20> spotList = new ArrayList<GT_ZL20>();
		List<GT_ZL19> farmList = new ArrayList<GT_ZL19>();
		List<GT_ZL14> villageList = new ArrayList<GT_ZL14>();

		if (groundTruthList != null && groundTruthList.size() > 0) {

			try {

				for (GroundTruth gt : groundTruthList) {

					for (Spots spot : gt.getSpots()) {

						for (Stress stress : spot.getStress()) {

							if (spot.getSpotId() == null || spot.getSpotId().toString().equalsIgnoreCase("null")) {

								List<BigInteger> caseDetailList = gt_ZL19_Repository.caseDetailsExist(gt.getCaseId());

								if (caseDetailList != null && caseDetailList.size() > 0) {

									GT_ZL19 farmEntityObject = new GT_ZL19();
									farmEntityObject.setCaseId(gt.getCaseId());
									farmEntityObject.setRegionId(gt.getRegionId());
									farmEntityObject.setSampleId(spot.getSampleId());
//									farmEntityObject.setSpotId(spot.getSpotId());
									farmEntityObject.setStressId(stress.getStressId());
									farmEntityObject.setSeverityId(stress.getSeverity());

									farmList.add(farmEntityObject);

								} else {

									GT_ZL14 villageEntityObject = new GT_ZL14();
									villageEntityObject.setCaseId(gt.getCaseId());
									villageEntityObject.setRegionId(gt.getRegionId());
									villageEntityObject.setSampleId(spot.getSampleId());
//									farmEntityObject.setSpotId(spot.getSpotId());
									villageEntityObject.setStressId(stress.getStressId());
									villageEntityObject.setSeverityId(stress.getSeverity());

									villageList.add(villageEntityObject);

								}

							} else {

								GT_ZL20 spotEntityObject = new GT_ZL20();
								spotEntityObject.setCaseId(gt.getCaseId());
								spotEntityObject.setRegionId(gt.getRegionId());
								spotEntityObject.setSampleId(spot.getSampleId());
								spotEntityObject.setSpotId(spot.getSpotId());
								spotEntityObject.setStressId(stress.getStressId());
								spotEntityObject.setSeverityId(stress.getSeverity());

								spotList.add(spotEntityObject);

							}
						}
					}
				}

				if (spotList != null && spotList.size() > 0) {
					gt_ZL20_Repository.saveAll(spotList);
				}
				if (farmList != null && farmList.size() > 0) {
					gt_ZL19_Repository.saveAll(farmList);
				}
				if (villageList != null && villageList.size() > 0) {
					gt_ZL14_Repository.saveAll(villageList);
				}

				resMap.put("success", true);
				resMap.put("message", "Ground truth data has been processed.");
				return resMap;
			} catch (Exception e) {
				throw new DataInsertionException("Database Insertion Exception: " + e.getMessage());
			}

		} else {
			resMap.put("message", " Input data is empty..");
			resMap.put("success", false);
			return resMap;
		}
	}

	private List<GT_ZL19> validateFarmData(List<GroundTruthZL19> list) {

		List<GT_ZL19> farmListValidated = new ArrayList<GT_ZL19>();

		if (list != null && list.size() > 0 && !list.isEmpty()) {

			list.stream().forEach(gl19 -> {

				if (gl19.getCaseId() <= 0 || !(Integer.valueOf(gl19.getCaseId()) instanceof Integer)) {
					throw new InvalidDataException("Invalid Data. CaseId is invalid : " + gl19.getCaseId());
				}

				if (gl19.getRegionId() <= 0 || !(Integer.valueOf(gl19.getRegionId()) instanceof Integer)) {
					throw new InvalidDataException("Invalid Data. RegionId is invalid : " + gl19.getRegionId());
				}

				if (gl19.getVillageCode() <= 0 || !(Integer.valueOf(gl19.getVillageCode()) instanceof Integer)) {
					throw new InvalidDataException("Invalid Data. VillageCode is invalid : " + gl19.getVillageCode());
				}

				List<Stress> stressList = gl19.getStress();

				stressList.stream().forEach(stress -> {

					if (stress.getStressId() <= 0 || !(Integer.valueOf(stress.getStressId()) instanceof Integer)) {
						throw new InvalidDataException("Invalid Data. StressId is invalid : " + stress.getStressId());
					}
					if (stress.getSeverity() < 0 || !(Integer.valueOf(stress.getSeverity()) instanceof Integer)) {
						throw new InvalidDataException("Invalid Data. Severity is invalid : " + stress.getSeverity());
					}

					GT_ZL19 gtZL19 = new GT_ZL19();

					gtZL19.setRegionId(gl19.getRegionId());
					gtZL19.setCaseId(gl19.getCaseId());
					gtZL19.setVillageCode(gl19.getVillageCode());

					gtZL19.setStressId(stress.getStressId());
					gtZL19.setSeverityId(stress.getSeverity());

					farmListValidated.add(gtZL19);

				});
			});

		} else

		{
			throw new InvalidDataException("Invalid Data. Data can not be null or empty.");
		}

		return farmListValidated;
	}

//	public List<GT_ZL20> getPanchayatwiseSpotData(Integer panchayatCode) {
	public List<GroundTruth> getPanchayatwiseSpotData(Integer panchayatCode) {

		try {

//			List<GT_ZL20> list = gt_ZL20_Repository.getPanchayatwiseSpotData(panchayatCode);

			List<GroundTruthZLInfDto> list = gt_ZL20_Repository.getPanchayatwiseCompleteSpotData(panchayatCode);

			if (list != null && list.size() > 0) {

				List<GroundTruth> groundTruthZL20List = new ArrayList<GroundTruth>();

				for (GroundTruthZLInfDto dto : list) {

					GroundTruth gtDto = new GroundTruth();
					gtDto.setRegionId(dto.getRegionID());
//					gtDto.setVillageCode(dto.getVillageCode());
					gtDto.setCaseId(dto.getCaseID());

					List<Spots> spotList = new ArrayList<Spots>();

					if (dto.getTileGroup() != null) {

						String spotArr[] = dto.getTileGroup().split(",");

						for (int k = 0; k < spotArr.length; k++) {

							Spots spot = new Spots();

							spot.setSpotId(new BigInteger(spotArr[k]));

							String[] stressStr = dto.getStressIDGroup().split(",");
							String[] severityStr = dto.getSeverityIDGroup().split(",");
							List<Stress> stressList = new ArrayList<Stress>();

							String str = stressStr[k];
							String stressSer = severityStr[k];
							String[] stArr = str.split("#");
							String[] svArr = stressSer.split("#");

							for (int j = 0; j < stArr.length; j++) {

								Stress strss = new Stress();

								strss.setStressId(Integer.parseInt(stArr[j]));

								strss.setSeverity(Integer.parseInt(svArr[j]));

								stressList.add(strss);

							}
							spot.setStress(stressList);

							spotList.add(spot);
						}
					} else {
						throw new NoRecordFoundException("Record Not Found for Spot ");
					}
					gtDto.setSpots(spotList);

					groundTruthZL20List.add(gtDto);

				}

				return groundTruthZL20List;

			} else {
				throw new NoRecordFoundException("Record Not Found for PanchaytCode " + panchayatCode);
			}

		} catch (Exception e) {
			throw e;
		}
	}

//	public List<GT_ZL19> getPanchayatwiseFarmData(Integer panchayatCode) {
	public List<GroundTruthZL19> getPanchayatwiseFarmData(Integer panchayatCode) {

		try {

			List<GroundTruthZLInfDto> list = gt_ZL19_Repository.getPanchayatwiseCompleteFarmData(panchayatCode);

			if (list != null && list.size() > 0) {

				List<GroundTruthZL19> groundTruthZL19List = new ArrayList<GroundTruthZL19>();

				for (GroundTruthZLInfDto infDto : list) {

					GroundTruthZL19 groundTruthZL19 = new GroundTruthZL19();

					groundTruthZL19.setRegionId(infDto.getRegionID());
					groundTruthZL19.setVillageCode(infDto.getVillageCode());
					groundTruthZL19.setCaseId(infDto.getCaseID());
					groundTruthZL19.setFieldTileId(new BigInteger(infDto.getTileGroup()));

					String[] stressArr = infDto.getStressIDGroup().split(",");
					String[] severityArr = infDto.getSeverityIDGroup().split(",");

					List<Stress> stressList = new ArrayList<Stress>();

					for (int i = 0; i < stressArr.length; i++) {

						Stress stress = new Stress();
						stress.setStressId(Integer.parseInt(stressArr[i]));
						stress.setSeverity(Integer.parseInt(severityArr[i]));

						stressList.add(stress);
					}

					groundTruthZL19.setStress(stressList);

					groundTruthZL19List.add(groundTruthZL19);

				}

				return groundTruthZL19List;

			} else {
				throw new NoRecordFoundException("Record Not Found for PanchaytCode " + panchayatCode);
			}

		} catch (Exception e) {
			throw e;
		}
	}

//	public List<GT_ZL14> getPanchayatwiseVillageData(Integer panchayatCode) {
	public List<GroundTruthZL14> getPanchayatwiseVillageData(Integer panchayatCode) {

		try {

			List<GroundTruthZLInfDto> list = gt_ZL14_Repository.getPanchayatwiseCompleteVillageData(panchayatCode);

			if (list != null && list.size() > 0) {

				List<GroundTruthZL14> groundTruthZL14List = new ArrayList<GroundTruthZL14>();

				for (GroundTruthZLInfDto infDto : list) {

					GroundTruthZL14 groundTruthZL14 = new GroundTruthZL14();

					groundTruthZL14.setRegionId(infDto.getRegionID());
					groundTruthZL14.setVillageCode(infDto.getVillageCode());
					groundTruthZL14.setCaseId(infDto.getCaseID());
					groundTruthZL14.setVillageTileId(new BigInteger(infDto.getTileGroup()));

					String[] stressArr = infDto.getStressIDGroup().split(",");
					String[] severityArr = infDto.getSeverityIDGroup().split(",");

					List<Stress> stressList = new ArrayList<Stress>();

					for (int i = 0; i < stressArr.length; i++) {

						Stress stress = new Stress();
						stress.setStressId(Integer.parseInt(stressArr[i]));
						stress.setSeverity(Integer.parseInt(severityArr[i]));

						stressList.add(stress);
					}

					groundTruthZL14.setStress(stressList);

					groundTruthZL14List.add(groundTruthZL14);

				}

				return groundTruthZL14List;

			} else {
				throw new NoRecordFoundException("Record Not Found for PanchaytCode " + panchayatCode);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public List<GT_ZL20> getCasewiseSpotData(Integer caseID) {

		try {

			List<GT_ZL20> list = gt_ZL20_Repository.getCasewiseSpotData(caseID);

			if (list != null && list.size() > 0) {

				return list;

			} else {
				throw new NoRecordFoundException("Record Not Found for caseID " + caseID);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public List<GT_ZL19> getCasewiseFarmData(Integer caseID) {

		try {

			List<GT_ZL19> list = gt_ZL19_Repository.getCasewiseFarmData(caseID);

			if (list != null && list.size() > 0) {

				return list;

			} else {
				throw new NoRecordFoundException("Record Not Found for caseID " + caseID);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public List<GT_ZL14> getCasewiseVillageData(Integer caseID) {

		try {

			List<GT_ZL14> list = gt_ZL14_Repository.getCasewiseVillageData(caseID);

			if (list != null && list.size() > 0) {

				return list;

			} else {
				throw new NoRecordFoundException("Record Not Found for caseID " + caseID);
			}

		} catch (Exception e) {
			throw e;
		}
	}

}
