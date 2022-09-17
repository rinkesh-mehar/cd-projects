package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.*;
import com.drkrishi.prm.model.*;
import com.drkrishi.prm.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Repository
@Component
public class PRMDaoImpl implements PRMDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private TehsilRepository talukarepo;

	@Autowired
	private VillageRepository villagerepo;

	@Autowired
	private UserRolesRepository userRoleRepo;

	@Autowired
	private DrKUserRepository userRepo;

	@Autowired
	private RegionRepository regionrepo;

	@Autowired
	ModelMapper modelMapper;

	//get list of all states
	@Override
	public List<State> getStates() {

		List<StateEntity> entities = (List<StateEntity>) stateRepo.findAll();
		List<State> states = new ArrayList<State>();
		for (StateEntity entity : entities) {
			states.add(modelMapper.map(entity, State.class));
		}
		return states;
	}

	@Override
	public List<Village> findVillagesByRegion(int regionCode) {
		return null;
	}

	//get villages based on villages
	@Override
	public List<Village> findVillagesBytalika(int talukaCode) {
		/*
		 * List<VillageEntity> entities = ( usersList<VillageEntity>)
		 * villagerepo.findBytalukaCode(talukaCode); List<Village> village = new
		 * ArrayList<Village>(); for (VillageEntity entity : entities) {
		 * village.add(modelMapper.map(entity, Village.class)); }
		 */
		return null;
	}

	@Override
	public List<DrKrishiRole> findUserByReportingToId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	//get village info based on village
	@Override
	public List<Village_Info> findVillagesInfoByVillage(int villageCode) {
		/*
		 * List<Village_InfoEntity> entities = (List<Village_InfoEntity>)
		 * villageInforepo.findByvillageCode(villageCode); List<Village_Info>
		 * villageInfo = new ArrayList<Village_Info>(); for (Village_InfoEntity entity :
		 * entities) { villageInfo.add(modelMapper.map(entity, Village_Info.class)); }
		 */
		return null;
	}

	//Get villages by district
	@Override
	public List<Village> findVillagesbyDistrict(int districtCode) {
		/*
		 * List<VillageEntity> entities = (List<VillageEntity>)
		 * villagerepo.findBydistrict(districtCode); List<Village> village = new
		 * ArrayList<Village>(); for (VillageEntity entity : entities) {
		 * village.add(modelMapper.map(entity, Village.class)); }
		 */
		return null;
	}

	//Get region based on state
	@Override
	public List<Region> findRegionByState(int stateCode) {
		List<RegionEntity> entities = (List<RegionEntity>) regionrepo.findByState(stateCode);
		List<Region> region = new ArrayList<Region>();
		for (RegionEntity entity : entities) {
			region.add(modelMapper.map(entity, Region.class));
		}
		return region;
	}

	//Get villages based on tile id
	@Override
	public List<Village> findVillagesByTileId(int tileId) {
		List<VillageEntity> entities = (List<VillageEntity>) villagerepo.findAll();
		List<Village> village = new ArrayList<Village>();
		for (VillageEntity entity : entities) {
			village.add(modelMapper.map(entity, Village.class));
		}
		return village;
	}

	//Get PR Scouts based on PRM and region
	@Override
	public List<DRKrishiUser> findScoutByReportingId(int userId, int regionId) {

		String sql = "user";
		Query query = em.createNamedQuery(sql);
		query.setParameter("userId", userId);
		query.setParameter("regionId", regionId);
		List<DRKrishiUserEntity> entities = query.getResultList();

		List<DRKrishiUser> users = new ArrayList<DRKrishiUser>();
		for (DRKrishiUserEntity entity : entities) {
			users.add(modelMapper.map(entity, DRKrishiUser.class));
		}
		return users;
	}


	//Get PR Scouts based on PRM
	@Override
	public List<DRKrishiUser> findScoutByPRmId(int userId) {

		List<DRKrishiUserEntity> entities = userRepo.findByReportingId(userId);

		List<DRKrishiUser> users = new ArrayList<DRKrishiUser>();
		for (DRKrishiUserEntity entity : entities) {
			users.add(modelMapper.map(entity, DRKrishiUser.class));
		}
		return users;
	}

	// get prs user list based on state id, region id, or prs name
	@Override
	public List<DRKrishiUserEntity> getPrsListByName(int stateId, int regionId, String prsname) {
		
		List<DRKrishiUserEntity> list = new ArrayList<DRKrishiUserEntity>();
		
		try {
			
			String hql = "from DRKrishiUserEntity where regionId = :regionId and stateId = :stateId and ( firstName LIKE :firstNamesearch"
					+ " or middleName LIKE :middleNamesearch or lastName LIKE :lastNamesearch )";
			Query query = em.createQuery(hql).
					setParameter("regionId", regionId).
					setParameter("stateId", stateId).
					setParameter("firstNamesearch", prsname+"%").
					setParameter("middleNamesearch", prsname+"%").
					setParameter("lastNamesearch", prsname+"%");

			if (query.getResultList().size() != 0) {
				return query.getResultList();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// get prs assigned task details based on prs id and week and year 
	@Override
	public List<PR_Village_AssigmentEntity> getPRS_AssigmentByPRSId(int prsId, int weekvalue, int yearvalue) {

		List<PR_Village_AssigmentEntity> list = new ArrayList<PR_Village_AssigmentEntity>();
		
		try {  
			String hql = "from PR_Village_AssigmentEntity where drkrishiUser2.id = :prsId and weekNumber=: weekNumber and year = :year ";			
			Query query = em.createQuery(hql).setParameter("prsId", prsId).setParameter("weekNumber", weekvalue).setParameter("year", yearvalue);

			if (query.getResultList().size() != 0) {
				return query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// get task based on prs id
	@Override
	public List<VillagetaskEntity> getPRS_VillageTaskEntity(int assignmentId) {

		List<VillagetaskEntity> list = new ArrayList<VillagetaskEntity>();
		
		try {  
			String hql = "from VillagetaskEntity where prVillageAssigment.assigment_Id = :assigment_Id  ";			
			Query query = em.createQuery(hql).setParameter("assigment_Id", assignmentId);

			if (query.getResultList().size() != 0) {
				return query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	

}
