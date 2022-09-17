package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.DRKrishiUserEntity;
import com.drkrishi.prm.entity.PR_Village_AssigmentEntity;
import com.drkrishi.prm.entity.VillagetaskEntity;
import com.drkrishi.prm.model.*;

import java.util.List;

public interface PRMDao {

	public List<DrKrishiRole> findUserByReportingToId(int id);

	public List<State> getStates();

	public List<Village> findVillagesByRegion(int regionCode);

	public List<Village> findVillagesBytalika(int talukaCode);

	public List<DRKrishiUser> findScoutByReportingId(int userId, int regionId);

	public List<Village_Info> findVillagesInfoByVillage(int villageCode);

	public List<Village> findVillagesbyDistrict(int districtCode);

	public List<Region> findRegionByState(int stateCode);

	public List<Village> findVillagesByTileId(int tileId);

	public List<DRKrishiUser> findScoutByPRmId(int userId);

	public List<DRKrishiUserEntity> getPrsListByName(int stateId,int regionId, String prsname);
	
	public List<PR_Village_AssigmentEntity> getPRS_AssigmentByPRSId(int prsId, int weekvalue, int yearvalue);
	
	public List<VillagetaskEntity> getPRS_VillageTaskEntity(int assignmentId);	
	
	
}
