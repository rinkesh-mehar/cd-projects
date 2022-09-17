package com.drkrishi.prm.service;

import com.drkrishi.prm.dao.PR_Village_AssigmentDao;
import com.drkrishi.prm.dao.SearchPrsDAOImpl;
import com.drkrishi.prm.entity.DRKrishiUserEntity;
import com.drkrishi.prm.model.ResponseMessage;
import com.drkrishi.prm.model.SearchPrmAssigntask;
import com.drkrishi.prm.model.Villagetask;
import com.drkrishi.prm.newentity.NEW_Village_AssigmentEntity;
import com.drkrishi.prm.newentity.NEW_VillagetaskEntity;
import com.drkrishi.prm.repository.villageTaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class SearchPrsServiceImpl implements SearchPrsService{

	@Autowired
	private PR_Village_AssigmentDao assigmentDao;

	@Autowired
	villageTaskRepository villageTaskRopo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	public SearchPrsDAOImpl searchPrsDaoImpl; 
	
	//Get list of assignment done PRS by PRM for 5 weeks
	@Override
	public List<Villagetask> getAssignmentListByPrmIdSearch(SearchPrmAssigntask search) {
		
		System.out.println(" Prs Service Service ");
		
		int stateId = search.getStateId();
		int regionId = search.getRegionId();
		String prsName = search.getName() == null ? "" : search.getName();
		
		System.out.println();
		System.out.println( " state: "+ stateId + " region: "+regionId + " Name: " + prsName);
		
		List<DRKrishiUserEntity> userEntity = searchPrsDaoImpl.getPrsListByName(stateId, regionId, prsName, search.getUserId());
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
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		
		List<NEW_VillagetaskEntity> villagetaskEntities = new ArrayList<NEW_VillagetaskEntity>();
		
				
		for (int i = 0; i < 4; i++) {
			
			int weekNo=getWeek(year);
			int weekvalue = (week + i) % weekNo > 0 ? (week + i) % weekNo : weekNo;
			int yearvalue = weekvalue < week - 1 ? year + 1 : year;
			int month = new Date().getMonth() ;
			int da = (new Date().getDate());
			
			/*
			 * if(i == 0 && weekvalue ==1 && month == 0 && da -(Calendar.DAY_OF_WEEK) < 0) {
			 * Calendar calendar = Calendar.getInstance();
			 * calendar.setFirstDayOfWeek(Calendar.MONDAY); calendar.setWeekDate(yearvalue,
			 * weekvalue-1 , 1); int d = calendar.getTime().getDate() +1; if(d <= 31) {
			 * yearvalue = yearvalue -1; } }
			 */
			for(DRKrishiUserEntity user :userEntity) {
				System.out.println(user);
				
				int prs_Id = user.getId();
				System.out.println(" PRS ID " + prs_Id);
				System.out.println();System.out.println();
				
				List<NEW_Village_AssigmentEntity> list = searchPrsDaoImpl.getPRS_AssigmentByPRSId(prs_Id, weekvalue, yearvalue );
				
				
				list.forEach( assignment -> {
					
					System.out.println();System.out.println();
					
					System.out.println(" PR_Village_AssigmentEntity:::: " + assignment );
					System.out.println(" Assignment ID " +  assignment.getAssigment_Id() );
					
//					System.out.println(" getDrkrishiUser1  " + assignment.getDrkrishiUser1());
//					System.out.println(" getDrkrishiUser2  " + assignment.getDrkrishiUser2());
					
//					PR_Village_AssigmentEntity prVillageAssigment = assignment; 
					
					List<NEW_VillagetaskEntity> taskEntity = searchPrsDaoImpl.getPRS_VillageTaskEntity(assignment.getAssigment_Id());
					
					taskEntity.forEach( entity -> {
						villagetaskEntities.add(entity);
					});
										
				});
			}
		}		
		
		List<Villagetask> villagetasks = new ArrayList<Villagetask>();
		
		for (NEW_VillagetaskEntity assigmentEntity : villagetaskEntities) {
			villagetasks.add(modelMapper.map(assigmentEntity, Villagetask.class));			
		}
		
		return villagetasks;		
	}

	@Override
	public ResponseMessage getScoutNamesListByStateidAndRegionid(int userId, int stateId, int regionId) {
		ResponseMessage responseModel = new ResponseMessage();
		try {

			List<DRKrishiUserEntity> drkrishiUserEntity = searchPrsDaoImpl.getScoutNamesList(userId,stateId, regionId);
			List<String> prsNameList = new ArrayList<String>();

			drkrishiUserEntity.forEach(entity -> {
				prsNameList.add(
						entity.getFirstName() + " " + ((entity.getMiddleName() == null) ? "" : entity.getMiddleName().concat(" "))
								+ "" + entity.getLastName());
			});
			responseModel.setStatusCode("success");
			responseModel.setMessage("");
			responseModel.setData(prsNameList);

		} catch (Exception e) {

			responseModel.setStatusCode("error");
			responseModel.setMessage("Prs not found");
			e.printStackTrace();
		}
		return responseModel;

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
	
}
