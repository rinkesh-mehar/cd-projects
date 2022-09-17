package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.DRKrishiUserEntity;
import com.drkrishi.prm.newentity.NEW_Village_AssigmentEntity;
import com.drkrishi.prm.newentity.NEW_VillagetaskEntity;

import java.util.List;

public interface SearchPrsDAO {

	public List<DRKrishiUserEntity> getPrsListByName(int stateId, int regionId, String prsname, Integer userId);
	
	public List<NEW_Village_AssigmentEntity> getPRS_AssigmentByPRSId(int prsId, int weekvalue, int yearvalue);
	
	public List<NEW_VillagetaskEntity> getPRS_VillageTaskEntity(int assignmentId);

	public List<DRKrishiUserEntity> getScoutNamesList(int userId,int stateId, int regionId);
	
	
}
