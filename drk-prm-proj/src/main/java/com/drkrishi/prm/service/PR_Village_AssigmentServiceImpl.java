package com.drkrishi.prm.service;

import com.drkrishi.prm.dao.PRMDaoImpl;
import com.drkrishi.prm.dao.PR_Village_AssigmentDao;
import com.drkrishi.prm.dao.VillagesAssigmenRepositiry;
import com.drkrishi.prm.entity.*;
import com.drkrishi.prm.model.*;
import com.drkrishi.prm.repository.DrKUserRepository;
import com.drkrishi.prm.repository.PrmTilesRepository;
import com.drkrishi.prm.repository.VillageRepository;
import com.drkrishi.prm.repository.villageTaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class PR_Village_AssigmentServiceImpl implements PR_Village_AssigmentService {

	@Autowired
	private PR_Village_AssigmentDao assigmentDao;

	@Autowired
	villageTaskRepository villageTaskRopo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	public PRMDaoImpl prmDaoImpl; 
	
	//Assign village task to PRS
	@Autowired
	private VillagesAssigmenRepositiry villagesAssignmentRepo;
	
	@Autowired
	VillageRepository villageRepo;
	
	@Autowired
	PrmTilesRepository prmTilesRepository;
	@Autowired
	DrKUserRepository drKUserRepository;

	@Override
	public ResponseModel save_Village_AssigmentService(PRModel assigment) {
		return assigmentDao.save_Village_Assigment(assigment);
		
	}

	// get village details based on prm id
	@Override
	public List<PR_Village_Assigment> getPR_Village_AssigmentByPRMId(int prmId) {
		// TODO Auto-generated method stub
		return assigmentDao.getPR_Village_AssigmentByPRMId(prmId);
	}

	//Get all assignment list
	@Override
	public List<PR_Village_Assigment> getPR_Village_AllAssigmentByPRMId() {
		return assigmentDao.getPR_Village_AllAssigmentByPRMId();
	}

	//Get list of assignment done PRS by PRM for 5 weeks
	@Override
	public List<Villagetask> getAssignmentListByPrmId(int prmId) {
		return assigmentDao.getAssignmentListByPrmId(prmId);
	}
	
	// get task details based on search field i.e, state id, region id or prs name 
	@Override
	public List<Villagetask> getAssignmentListByPrmIdSearch(SearchPrmAssigntask search) {
		
		int stateId = search.getStateId();
		int regionId = search.getRegionId();
		String prsName = search.getName() == null ? "" : search.getName();
		
		
		System.out.println();
		System.out.println( " state: "+ stateId + " region: "+regionId + " Name: " + prsName);
		
		List<DRKrishiUserEntity> userEntity = prmDaoImpl.getPrsListByName(stateId, regionId, prsName);
		System.out.println();System.out.println();
		
		if(userEntity.size() > 0 ) {
			System.out.println(" Users is Found ......");
			userEntity.forEach( user -> {
				System.out.println(user);
			});
		}			
		else {
			System.out.println(" Users is Not Found ......");			
			return new ArrayList<Villagetask>();
		}
			
		System.out.println();System.out.println();
	
		
		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
		Calendar cal = Calendar.getInstance();  // cal.set(2019, 11, 27);		
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		
		List<VillagetaskEntity> villagetaskEntities = new ArrayList<VillagetaskEntity>();
		
				
		for (int i = 0; i < 4; i++) {
			
			int weekvalue = (week + i) % 52 > 0 ? (week + i) % 52 : 52;
			int yearvalue = weekvalue < week - 1 ? year + 1 : year;
			
			System.out.println();System.out.println();
			System.out.println(" Search: week value " + weekvalue + ":  Year Value " + yearvalue);
			
			userEntity.forEach( user -> {
				System.out.println(user);
				
				int prs_Id = user.getId();
				System.out.println(" PRS ID " + prs_Id);
				System.out.println();System.out.println();
				
				List<PR_Village_AssigmentEntity> list = prmDaoImpl.getPRS_AssigmentByPRSId(prs_Id, weekvalue, yearvalue );
				System.out.println( " PRS ASSIGNMENMT LIST " + list.size() );
				
				list.forEach( assignment -> {
					
					System.out.println();System.out.println();
					
					System.out.println(" PR_Village_AssigmentEntity:::: " + assignment );
					System.out.println(" Assignment ID " +  assignment.getAssigment_Id() );
					
//					System.out.println(" getDrkrishiUser1  " + assignment.getDrkrishiUser1());
					System.out.println(" getDrkrishiUser2  " + assignment.getDrkrishiUser2());
					
//					PR_Village_AssigmentEntity prVillageAssigment = assignment; 
					
					List<VillagetaskEntity> taskEntity = prmDaoImpl.getPRS_VillageTaskEntity(assignment.getAssigment_Id());
					
					taskEntity.forEach( entity -> {
						villagetaskEntities.add(entity);
					});
										
				});
			});
		}		
		
		List<Villagetask> villagetasks = new ArrayList<Villagetask>();
		
		for (VillagetaskEntity assigmentEntity : villagetaskEntities) {
			villagetasks.add(modelMapper.map(assigmentEntity, Villagetask.class));			
		}
		
		return villagetasks;		
	}

	@Override
	public List<Integer> getAssignedVillageByWeek(int week, int year) {
		return villageTaskRopo.getAssignedVillageByWeek(week , year);
	}
	
	
	public PrsEditResponseModel getAssignmentDetailsForEdit(int assgnmentId) {

		PrsEditResponseModel response = new PrsEditResponseModel();
		List<VillageModel> villnameList = new ArrayList<VillageModel>();
		List<String> prmTilesList = new ArrayList<>();
		List<Integer> villcodeList = new ArrayList<Integer>();
		try {
			int prsId = 0;
			Optional<PR_Village_AssigmentEntity> assignment = villagesAssignmentRepo.findById(assgnmentId);
			if (assignment.isPresent()) {
				int prmId=assignment.get().getDrkrishiUser1().getId();
				prsId = assignment.get().getDrkrishiUser2().getId();
				response.setAssigmentId(assgnmentId);
				response.setDrkrishiUser1(prmId);
				response.setPrscout(assignment.get().getDrkrishiUser2().getId());
				response.setMonth(assignment.get().getMonth());
				response.setWeekNumber(assignment.get().getWeekNumber());
				response.setYear(assignment.get().getYear());
			}
			List<VillagetaskEntity> villTask = villageTaskRopo.findAllByPrVillageAssigment(assgnmentId);
			for (VillagetaskEntity villagetaskEntity : villTask) {

				villcodeList.add(villagetaskEntity.getVillageEntity().getCode());
			}
			for (Integer villCode : villcodeList) {
				VillageModel villageModel = new VillageModel();
				List<VillageEntity> villEntity = villageRepo.findByVillageCode(villCode);
				for (VillageEntity villageEntity : villEntity) {
					villageModel.setVillageCode(villageEntity.getCode());
					villageModel.setVillageName(villageEntity.getName());
					villnameList.add(villageModel);

				}

			}
			response.setVillage(villnameList);
			List<PrmTilesEntity> prmTilesEntity = prmTilesRepository.findAllByprsAssignmentId(assgnmentId);
			for (PrmTilesEntity prmTiles : prmTilesEntity) {

				prmTilesList.add(prmTiles.getTilesId());
			}
			response.setDatasid(prmTilesList);
			List<DRKrishiUserEntity> users = drKUserRepository.findByuserId(prsId);
			response.setStateId(users.get(0).getStateId());
			response.setRegionID(users.get(0).getRegionId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	@Override
	public ResponseModel deleteAssignmentTask(int assgnmentId) {
		return assigmentDao.deletePrsAssignmentTask(assgnmentId);
	}
	
	/*@Override
	public List<Villagetask> getAssignmentListByPrmIdSearchRefineFields(SearchPrmAssigntask search) {
		
		int stateId = search.getStateId();
		int regionId = search.getRegionId();
		String prsName = search.getName() == null ? "" : search.getName();
		
		
		System.out.println();
		System.out.println( " state: "+ stateId + " region: "+regionId + " Name: " + prsName);
		
		List<DRKrishiUserEntity> userEntity = prmDaoImpl.getPrsListByName(stateId, regionId, prsName);
		System.out.println();System.out.println();
		
		if(userEntity.size() > 0 ) {
			System.out.println(" Users is Found ......");
			userEntity.forEach( user -> {
				System.out.println(user);
			});
		}			
		else {
			System.out.println(" Users is Not Found ......");			
			return new ArrayList<Villagetask>();
		}
			
		System.out.println();System.out.println();
	
		
		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
		Calendar cal = Calendar.getInstance();  // cal.set(2019, 11, 27);		
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		
		List<VillagetaskEntity> villagetaskEntities = new ArrayList<VillagetaskEntity>();
		
				
		for (int i = 0; i < 4; i++) {
			
			int weekvalue = (week + i) % 52 > 0 ? (week + i) % 52 : 52;
			int yearvalue = weekvalue < week - 1 ? year + 1 : year;
			
			System.out.println();System.out.println();
			System.out.println(" Search: week value " + weekvalue + ":  Year Value " + yearvalue);
			
			userEntity.forEach( user -> {
				System.out.println(user);
				
				int prs_Id = user.getId();
				System.out.println(" PRS ID " + prs_Id);
				System.out.println();System.out.println();
				
				List<P_Village_AssigmentEntity> list = prmDaoImpl.getPRS_AssigmentByPRSId(prs_Id, weekvalue, yearvalue );
				System.out.println( " PRS ASSIGNMENMT LIST " + list.size() );
				
				list.forEach( assignment -> {
					
					System.out.println();System.out.println();
					
					System.out.println(" PR_Village_AssigmentEntity:::: " + assignment );
					System.out.println(" Assignment ID " +  assignment.getAssigment_Id() );
					
					System.out.println(" getDrkrishiUser1  " + assignment.getDrkrishiUser1());
					System.out.println(" getDrkrishiUser2  " + assignment.getDrkrishiUser2());
					
//					PR_Village_AssigmentEntity prVillageAssigment = assignment; 
					
					List<VillagetaskEntity> taskEntity = prmDaoImpl.getPRS_VillageTaskEntity(assignment.getAssigment_Id());
					
					taskEntity.forEach( entity -> {
						villagetaskEntities.add(entity);
					});
										
				});
			});
		}		
		
		List<Villagetask> villagetasks = new ArrayList<Villagetask>();
		
		for (VillagetaskEntity assigmentEntity : villagetaskEntities) {
			villagetasks.add(modelMapper.map(assigmentEntity, Villagetask.class));			
		}
		
		return villagetasks;		
	}  */
	
	
	
}
