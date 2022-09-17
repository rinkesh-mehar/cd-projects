package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.AuditTrial;
import com.drkrishi.prm.entity.*;
import com.drkrishi.prm.model.*;
import com.drkrishi.prm.repository.*;
import com.drkrishi.prm.service.SmsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Component
public class PR_Village_AssigmentDaoImpl implements PR_Village_AssigmentDao {

	private static final Logger LOGGER = LogManager.getLogger(PR_Village_AssigmentDaoImpl.class);

	@Autowired
	private VillagesAssigmenRepositiry villagesAssignmentRepo;

	@Autowired
	VillageRepository villageRepo;

	@Autowired
	villageTaskRepository villageTaskRopo;

	@Autowired
	AuditTrialRepository auditTrailRepo;

	@Autowired
	private DrKUserRepository userRepo;

	@Autowired
	SmsServiceImpl smsServiceImpl;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PrmTilesRepository prmTilesRepository;

	int prmid;

	StringBuffer sb = new StringBuffer();

	//Assign village task to PRS
	@Transactional
	@Override
	public ResponseModel save_Village_Assigment(PRModel prModel) {

		ResponseModel responseModel = new ResponseModel();
		PR_Village_Assigment assigment = new PR_Village_Assigment();
		boolean flag = true;

		try {
			System.out.println(" prModel.getDrkrishiUser1() ********** " + prModel.getDrkrishiUser1());

			List<DRKrishiUserEntity> userentitiesByPrmId = userRepo.findByuserId(prModel.getDrkrishiUser1());
			System.out.println(userentitiesByPrmId);
			List<DRKrishiUserEntity> userentitiesByPrsId = userRepo.findByuserId(prModel.getPrscout());
			if (userentitiesByPrmId.get(0).getStatus() == 1 && prModel.getAssigmentId() == null) {

				if (null != prModel.getVillage() && prModel.getVillage().size() > 0) {

					for (JsonNode ite : prModel.getVillage()) {

						List<VillageEntity> villageEntity = villageRepo
								.findByVillageCode(Integer.valueOf(ite.get("villageCode").toString()));

						if (villageEntity != null && villageEntity.size() > 0) {

							System.out.println(" Starting getVillage ");
							flag = getVillage(prModel, flag, villageEntity);
							System.out.println(" Ending getVillage ");
							if (!flag) {
								responseModel.setStatusCode(406);
								responseModel.setMsg("Village already assigned for this week ");
								break;
							}
						} else {
							flag = false;
							responseModel.setStatusCode(406);
							responseModel.setMsg(
									"Villages does not exists in drkrishi database :- " + ite.get("villageName"));
							// Villages does not exists in drkrishi database ite.get("villageName")
						}

					}

					if (flag && prModel.getAssigmentId() == null) {
						if (null != prModel) {

							PR_Village_AssigmentEntity assigmentEntity = villagesAssignmentRepo.findByPRSIdForWeek(
									prModel.getPrscout(), prModel.getWeekNumber(), prModel.getYear());
							if (assigmentEntity == null) {
								assigment.setMonth(prModel.getMonth());
								assigment.setWeekNumber(prModel.getWeekNumber());
								assigment.setYear(prModel.getYear());
								assigment.setDrkrishiUser1(
										modelMapper.map(userentitiesByPrmId.get(0), DRKrishiUser.class));
								assigment.setDrkrishiUser2(
										modelMapper.map(userentitiesByPrsId.get(0), DRKrishiUser.class));
								assigment.setCreated_Date(new Date());
								assigment.setStatus(1);
								assigmentEntity = modelMapper.map(assigment, PR_Village_AssigmentEntity.class);

								assigmentEntity = villagesAssignmentRepo.save(assigmentEntity);

								sb.delete(0, sb.length());
								sb.append("month=" + prModel.getMonth() + " , ");
								sb.append("week=" + prModel.getWeekNumber() + " , ");
								sb.append("year= " + prModel.getYear() + " , ");

								prmid = userentitiesByPrmId.get(0).getId();

								sb.append("Prm=(" + prmid + ")" + userentitiesByPrmId.get(0).getFirstName() + " , ");

								sb.append("Prs=(" + prModel.getPrscout() + ")"
										+ userentitiesByPrsId.get(0).getFirstName());

								// auditTrial(sb.toString(), "prs_assignment", userentities.get(0).getId());
								auditTrial(sb.toString(), "prs_assignment", prmid);
							}

							for (JsonNode ite : prModel.getVillage()) {

								System.out.println("taks assigned");
								List<VillageEntity> villageEntity = villageRepo
										.findByVillageCode(Integer.valueOf(ite.get("villageCode").toString()));

								VillagetaskEntity villagetaskEntity = new VillagetaskEntity();
								villagetaskEntity.setPrVillageAssigment(assigmentEntity);
								villagetaskEntity.setIsCompleted(0);
								villagetaskEntity.setStatus(1);
								for (VillageEntity entity : villageEntity) {
									villagetaskEntity.setVillageEntity(entity);
								}
								villageTaskRopo.save(villagetaskEntity);

								sb.append("village_id = " + villagetaskEntity.getVillageEntity().getId() + " , ");
								sb.append("prs_assignment_id = "
										+ villagetaskEntity.getPrVillageAssigment().getAssigment_Id() + " , ");
								sb.append("satus= incompleted");
								auditTrial(sb.toString(), "prs_task", prmid);
							}

							List<String> tilesId = prModel.getDatasid();

							PR_Village_AssigmentEntity prsAssignmentId = villagesAssignmentRepo
									.findByassigment_Id(prModel.getAssigmentId());
							for (String tiles : tilesId) {
								PrmTilesEntity prmTilesEntity = new PrmTilesEntity();
								prmTilesEntity.setTilesId(tiles);
								prmTilesEntity.setStatus(1);
								prmTilesEntity.setPrsAssignmentId(assigmentEntity);

								prmTilesRepository.save(prmTilesEntity);
							}

							// return responseModel;

						} else {

						}
					} else {
						// return responseModel;
					}

				} else {
					// error no village selected
					flag = false;
					responseModel.setStatusCode(406);
					responseModel.setMsg("Error no village selected");
					// return responseModel;
				}
			}

			// --------------------------------edit code------------------
			else if (flag && prModel.getAssigmentId() != null && userentitiesByPrmId.get(0).getStatus() == 1) {
				Calendar cal = Calendar.getInstance();
				cal.setFirstDayOfWeek(Calendar.MONDAY);
				int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
				List<Integer> villListCode = new ArrayList<Integer>();
				List<Integer> newAddedVillageList = new ArrayList<Integer>();
				List<Integer> oldAddedVillageList = new ArrayList<Integer>();
				PR_Village_AssigmentEntity assignment = villagesAssignmentRepo
						.findByassigment_Id(prModel.getAssigmentId());
				int weekNumber = assignment.getWeekNumber();
				if (currentWeek == prModel.getWeekNumber() && weekNumber == prModel.getWeekNumber()) {

					for (JsonNode ite : prModel.getVillage()) {
						List<VillageEntity> villageEntity = villageRepo
								.findByVillageCode(Integer.valueOf(ite.get("villageCode").toString()));
						villListCode.add(villageEntity.get(0).getCode());
					}

					for (Integer villListCodeData : villListCode) {
						VillagetaskEntity villagetaskEntity = villageTaskRopo
								.findByprVillageAssigmentAndvillageEntity(prModel.getAssigmentId(), villListCodeData);
						if (villagetaskEntity == null) {
							newAddedVillageList.add(villListCodeData);
						} else {
							oldAddedVillageList.add(villListCodeData);
						}
					}

					List<VillagetaskEntity> villagetaskEntityRepo = villageTaskRopo
							.findByprVillageAssigment(prModel.getAssigmentId());
					if (villagetaskEntityRepo.size() != oldAddedVillageList.size()) {
						responseModel.setStatusCode(406);
						responseModel.setMsg("Cannot make changes for ongoing task.");
						flag = false;
					}

					// ----------------
					if (flag) {
						for (Integer villgeCode : newAddedVillageList) {
							List<VillageEntity> villageEntity = villageRepo.findByVillageCode(villgeCode);
							if (villageEntity != null && villageEntity.size() > 0) {
								System.out.println(" Starting getVillage ");
								flag = getVillage(prModel, flag, villageEntity);
								System.out.println(" Ending getVillage ");
								if (!flag) {
									responseModel.setStatusCode(406);
									responseModel.setMsg("Village already assigned for this week ");
									break;
								}
							} else {
								flag = false;
								responseModel.setStatusCode(406);
								responseModel.setMsg("Villages does not exists in drkrishi database :- "
										+ villageEntity.get(0).getName());
							}
						}
					}

					if (oldAddedVillageList.size() == villListCode.size() && newAddedVillageList.size() == 0 && flag) {
						System.out.println("villages not added");
					}

					if (oldAddedVillageList.size() != villListCode.size() && newAddedVillageList.size() > 0 && flag) {
						for (Integer villCode : newAddedVillageList) {
							PR_Village_AssigmentEntity prsAssignmentId = villagesAssignmentRepo
									.findByassigment_Id(prModel.getAssigmentId());
							List<VillageEntity> villEntity = villageRepo.findByVillageCode(villCode);
							VillagetaskEntity villagetaskEntity = new VillagetaskEntity();
							villagetaskEntity.setPrVillageAssigment(prsAssignmentId);
							villagetaskEntity.setStatus(1);
							villagetaskEntity.setIsCompleted(0);
							villagetaskEntity.setVillageEntity(villEntity.get(0));
							villageTaskRopo.save(villagetaskEntity);
						}

						// ----add tiles also here for adding in current week
						List<String> newTilesAddedList = new ArrayList<String>();
						List<String> oldAddedTilesList = new ArrayList<String>();
						List<String> tiles = prModel.getDatasid();

						for (String tilesId : tiles) {
							PrmTilesEntity prmTilesEntity = prmTilesRepository
									.findBytilesIdAndPrsAssignmentId(prModel.getAssigmentId(), tilesId);
							if (prmTilesEntity == null) {
								newTilesAddedList.add(tilesId);
							} else {
								oldAddedTilesList.add(tilesId);
							}
						}

						if (oldAddedTilesList.size() == tiles.size() && newTilesAddedList.size() == 0 && flag) {
							System.out.println("villages not added");
						}

						if (oldAddedTilesList.size() != tiles.size() && newTilesAddedList.size() > 0 && flag) {
							PR_Village_AssigmentEntity prsAssignmentId = villagesAssignmentRepo
									.findByassigment_Id(prModel.getAssigmentId());
							for (String tilesList : newTilesAddedList) {
								PrmTilesEntity prmTilesEntity = new PrmTilesEntity();
								prmTilesEntity.setTilesId(tilesList);
								prmTilesEntity.setPrsAssignmentId(prsAssignmentId);
								prmTilesEntity.setStatus(1);
								prmTilesRepository.save(prmTilesEntity);

							}
						}

						// ---------------------

					}

				}
				if (currentWeek != prModel.getWeekNumber() || weekNumber != prModel.getWeekNumber()) {

					
					  PR_Village_AssigmentEntity assigmentEntityData = villagesAssignmentRepo
					  .findByPRSIdForWeek(prModel.getPrscout(), prModel.getWeekNumber(), prModel.getYear());
					  
					/*
					 * List<String> prsFullNmae = getPrsFullName(prModel.getPrscout()); if
					 * (assigmentEntityData != null && flag) { responseModel.setStatusCode(406);
					 * responseModel.setMsg(prsFullNmae.get(0)+" "+"already assigned for the week");
					 * flag=false; }
					 */
					 
					if(assigmentEntityData==null) {
						assigment.setAssigment_Id(prModel.getAssigmentId());
						assigment.setMonth(prModel.getMonth());
						assigment.setWeekNumber(prModel.getWeekNumber());
						assigment.setYear(prModel.getYear());
						assigment.setDrkrishiUser1(modelMapper.map(userentitiesByPrmId.get(0), DRKrishiUser.class));
						assigment.setDrkrishiUser2(modelMapper.map(userentitiesByPrsId.get(0), DRKrishiUser.class));
						assigment.setCreated_Date(new Date());
						assigment.setStatus(1);
						PR_Village_AssigmentEntity assigmentEntity = modelMapper.map(assigment,
								PR_Village_AssigmentEntity.class);
						assigmentEntity = villagesAssignmentRepo.save(assigmentEntity);
					}
//----------------------
					if (flag) {
						for (JsonNode ite : prModel.getVillage()) {
							List<VillageEntity> villageEntity = villageRepo
									.findByVillageCode(Integer.valueOf(ite.get("villageCode").toString()));
							villListCode.add(villageEntity.get(0).getCode());
						}

						for (Integer villListCodeData : villListCode) {
							VillagetaskEntity villagetaskEntity = villageTaskRopo
									.findByprVillageAssigmentAndvillageEntity(prModel.getAssigmentId(),
											villListCodeData);
							if (villagetaskEntity == null) {
								newAddedVillageList.add(villListCodeData);
							} else {
								oldAddedVillageList.add(villListCodeData);
							}
						}
//-------------------------------------------if vill removed
						List<VillagetaskEntity> villagetaskEntityRepo = villageTaskRopo
								.findByprVillageAssigment(prModel.getAssigmentId());
						List<Integer> villList = new ArrayList<Integer>();
						List<Integer> removedVillage = new ArrayList<Integer>();
						for (VillagetaskEntity entity : villagetaskEntityRepo) {
							villList.add(entity.getVillageEntity().getCode());
						}
						for (Integer value : villList) {
							if (!villListCode.contains(value)) {
								removedVillage.add(value);

							}

						}

						if (removedVillage.size() > 0) {
							Integer assignId = prModel.getAssigmentId();
							for (int newVillCode : removedVillage) {
								updateToInActiveVill(newVillCode, assignId);
							}
						}
					}
					// ---------------------------------

					if (flag) {
						for (Integer villgeCode : newAddedVillageList) {

							List<VillageEntity> villageEntity = villageRepo.findByVillageCode(villgeCode);

							if (villageEntity != null && villageEntity.size() > 0) {

								System.out.println(" Starting getVillage ");
								flag = getVillage(prModel, flag, villageEntity);
								System.out.println(" Ending getVillage ");
								if (!flag) {
									responseModel.setStatusCode(406);
									responseModel.setMsg("Village already assigned for this week ");
									break;
								}
							} else {
								flag = false;
								responseModel.setStatusCode(406);
								responseModel.setMsg("Villages does not exists in drkrishi database :- "
										+ villageEntity.get(0).getName());
								// Villages does not exists in drkrishi database ite.get("villageName")
							}
						}
					}

					// ----------------

					if (oldAddedVillageList.size() == villListCode.size() && newAddedVillageList.size() == 0 && flag) {

						System.out.println("villages not added");

					}

					if (oldAddedVillageList.size() != villListCode.size() && newAddedVillageList.size() > 0 && flag) {
						for (Integer villCode : newAddedVillageList) {
							PR_Village_AssigmentEntity prsAssignmentId = villagesAssignmentRepo
									.findByassigment_Id(prModel.getAssigmentId());
							List<VillageEntity> villEntity = villageRepo.findByVillageCode(villCode);
							VillagetaskEntity villagetaskEntity = new VillagetaskEntity();
							villagetaskEntity.setPrVillageAssigment(prsAssignmentId);
							villagetaskEntity.setIsCompleted(0);
							villagetaskEntity.setStatus(1);
							villagetaskEntity.setVillageEntity(villEntity.get(0));
							villageTaskRopo.save(villagetaskEntity);
						}
					}
					if (flag) {
						// ----add tiles also here for adding in current week
						List<String> newTilesAddedList = new ArrayList<>();
						List<String> oldAddedTilesList = new ArrayList<>();
						// int[] tiles = prModel.getDatasid();
						List<String> tiles = prModel.getDatasid();
						for (String tilesId : tiles) {
							PrmTilesEntity prmTilesEntity = prmTilesRepository
									.findBytilesIdAndPrsAssignmentId(prModel.getAssigmentId(), tilesId);
							if (prmTilesEntity == null) {
								newTilesAddedList.add(tilesId);
							} else {
								oldAddedTilesList.add(tilesId);
							}
						}

						// --------------------if tiles removed
						List<PrmTilesEntity> prmTilesEntiy = prmTilesRepository
								.findAllByprsAssignmentId(prModel.getAssigmentId());
						List<String> tilesData = new ArrayList<>();
						List<String> removedTilesList = new ArrayList<>();
						for (PrmTilesEntity tile : prmTilesEntiy) {
							tilesData.add(tile.getTilesId());
						}
						for (String value : tilesData) {
							if (!tiles.contains(value)) {
								removedTilesList.add(value);
							}

						}

						if (removedTilesList.size() > 0) {
							Integer assignId = prModel.getAssigmentId();
							for (String newTiles : removedTilesList) {
								updateToInActive(assignId, newTiles);
							}
						}
						// -----------------------------

						if (oldAddedTilesList.size() == tiles.size() && newTilesAddedList.size() == 0 && flag) {
							System.out.println("tiles not added");
						}

						if (oldAddedTilesList.size() != tiles.size() && newTilesAddedList.size() > 0 && flag) {
							PR_Village_AssigmentEntity prsAssignmentId = villagesAssignmentRepo
									.findByassigment_Id(prModel.getAssigmentId());
							for (String tilesList : newTilesAddedList) {

								PrmTilesEntity prmTilesEntity = new PrmTilesEntity();
								prmTilesEntity.setTilesId(tilesList);
								prmTilesEntity.setPrsAssignmentId(prsAssignmentId);
								prmTilesEntity.setStatus(1);
								prmTilesRepository.save(prmTilesEntity);

							}
						}

					}
				}

			} else {
				flag = false;
				responseModel.setStatusCode(406);
				responseModel.setMsg("User is inactive.");
			}
			if (flag) {

				String mobileNumber = userentitiesByPrsId.get(0).getMobileNumber();
				System.out.println(" Mobile Number " + mobileNumber);
				smsServiceImpl.prmTaskAssignmentNotification(String.valueOf(mobileNumber));

				// -------
				List<String> prsFullNmae = getPrsFullName(prModel.getPrscout());
				System.out.println(prsFullNmae.get(0));

				// ---

				responseModel.setStatusCode(200);
				responseModel.setPrsName(prsFullNmae.get(0));
				responseModel.setMsg("Success");
			}
			LOGGER.info(" response message : " + responseModel.getMsg().toString());
			return responseModel;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured while assigning villages to PRM " + e.toString());
			responseModel.setStatusCode(406);
			responseModel.setMsg("Some error occured durring assignment, please contact system admin");
			return responseModel;

		}

	}

	
	private List<String> getPrsFullName(Integer prscout) {
		List<DRKrishiUserEntity> userEntity = userRepo.findByuserId(prscout);
		List<String> prsNameList = new ArrayList<String>();

		userEntity.forEach(entity -> {
			prsNameList.add(

					entity.getFirstName() + " "
							+ ((entity.getMiddleName() == null) ? "" : entity.getMiddleName().concat(" ")) + ""
							+ entity.getLastName());
		});
		return prsNameList;

	}

	@Transactional
	private void updateToInActiveVill(int newVillCode, Integer assignId) {
		try {
			Query hql = em.createQuery(
					"UPDATE  VillagetaskEntity v SET v.status=0 WHERE v.villageEntity.id = :newVillCode and v.prVillageAssigment.assigment_Id = :assignId");
			hql.setParameter("newVillCode", newVillCode).setParameter("assignId", assignId).executeUpdate();

		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	@Transactional
	private void updateToInActive(Integer assignId, String newTiles) {
		try {
			Query hql = em.createQuery(
					"update  PrmTilesEntity v set v.status = 0 where v.prsAssignmentId.assigment_Id = :assignId and v.tilesId = : newTiles");
			int result = hql.setParameter("assignId", assignId).setParameter("newTiles", newTiles).executeUpdate();

		} catch (Exception e) {
			e.fillInStackTrace();
		}

	}

	// get village details
	private boolean getVillage(PRModel prModel, boolean flag, List<VillageEntity> villageEntity) {

		try {
			for (VillageEntity entity : villageEntity) {

				System.out.println(" entity.getId() .................... " + entity.getId());
				List<VillagetaskEntity> villagetaskEntity = villageTaskRopo
						.findVillageTaskBYvillageIdId(entity.getId());
				System.out.println(" villagetaskEntity .................... " + villagetaskEntity.size());

				for (VillagetaskEntity taskEntity : villagetaskEntity) {
					if (taskEntity.getPrVillageAssigment().getWeekNumber() == prModel.getWeekNumber()
							&& taskEntity.getPrVillageAssigment().getYear() == prModel.getYear()
							&& taskEntity.getIsCompleted() == 0) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(" Existing.............getVillage....... ");
		return flag;
	}

	// get villages based on prm id
	@Override
	public List<PR_Village_Assigment> getPR_Village_AssigmentByPRMId(int prmId) {

		List<PR_Village_AssigmentEntity> villageAssigmentList = (List<PR_Village_AssigmentEntity>) villagesAssignmentRepo
				.findByPrmID(prmId);
		List<PR_Village_Assigment> assigments = new ArrayList<PR_Village_Assigment>();

		for (PR_Village_AssigmentEntity assigmentEntity : villageAssigmentList) {

			assigments.add(modelMapper.map(assigmentEntity, PR_Village_Assigment.class));
		}
		return assigments;
	}

	//Get all assignment list
	@Override
	public List<PR_Village_Assigment> getPR_Village_AllAssigmentByPRMId() {
		List<PR_Village_AssigmentEntity> villageAssigmentList = (List<PR_Village_AssigmentEntity>) villagesAssignmentRepo
				.findAll();
		List<PR_Village_Assigment> assigments = new ArrayList<PR_Village_Assigment>();

		for (PR_Village_AssigmentEntity assigmentEntity : villageAssigmentList) {

			assigments.add(modelMapper.map(assigmentEntity, PR_Village_Assigment.class));
		}
		return assigments;
	}

	//Get list of assignment done PRS by PRM for 5 weeks
	@Override
	public List<Villagetask> getAssignmentListByPrmId(int prmId) {

		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
		Calendar cal = Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// cal.set(2019, 11, 27);
		//cal.setFirstDayOfWeek(Calendar.MONDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		List<VillagetaskEntity> villagetaskEntities = new ArrayList<VillagetaskEntity>();

		int valYear = Integer.valueOf(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
		for (int i = 0; i < 4; i++) {
			
			int weekvalue = (week + i) % getWeek(valYear) > 0 ? (week + i) % getWeek(valYear) : 53;

			int yearvalue = weekvalue < week - 1 ? year + 1 : year;

			System.out.println(" week value " + weekvalue + ":  Year Value " + yearvalue);

			List<VillagetaskEntity> list = villageTaskRopo.findVillageTaskBYPrmId(prmId, weekvalue, yearvalue);
			villagetaskEntities.addAll(list);
		}

		List<Villagetask> villagetasks = new ArrayList<Villagetask>();

		for (VillagetaskEntity assigmentEntity : villagetaskEntities) {

			villagetasks.add(modelMapper.map(assigmentEntity, Villagetask.class));

		}
		return villagetasks;
	}
	
	// get week based on year
	public static int getWeek(int year)
	{
		 Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, year);
		    cal.set(Calendar.MONTH, Calendar.DECEMBER);
		    cal.set(Calendar.DAY_OF_MONTH, 31);

		    int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
		    int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
		    int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
		    return numberOfWeeks;
	}
	
	
	
	
	
	// save details for audit trial
	public void auditTrial(String values, String table, int userId) {

		AuditTrial auditTrial = new AuditTrial();
		auditTrial.setAction("save");
		auditTrial.setAuditDateTime(new Timestamp(System.currentTimeMillis()));
		auditTrial.setOldValues("empty");
		auditTrial.setNewvalues(values);
		auditTrial.setAction("save");
		auditTrial.setTransactionTable(table);
		auditTrial.setUserId1(userId);

		auditTrailRepo.save(auditTrial);

	}

	@Override
	public ResponseModel deletePrsAssignmentTask(int assgnmentId) {
		ResponseModel responseModel = new ResponseModel();
		int updatedAssignment = villagesAssignmentRepo.update(assgnmentId);
		int updatedTask = villageTaskRopo.updateTOInActive(assgnmentId);
		int updatedTiles = prmTilesRepository.updateInActiveTiles(assgnmentId);
		if (updatedAssignment != 0 && updatedTask != 0 && updatedTiles != 0) {
			responseModel.setStatusCode(200);
			responseModel.setMsg("Success.");
		} else {
			responseModel.setStatusCode(200);
			responseModel.setMsg("Error.");
		}
		return responseModel;
	}

}
